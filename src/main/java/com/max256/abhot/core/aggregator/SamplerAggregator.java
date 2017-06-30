package com.max256.abhot.core.aggregator;

import org.joda.time.DateTimeZone;

import com.google.inject.Inject;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.aggregator.annotation.AggregatorProperty;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;
import com.max256.abhot.core.datastore.DataPointGroup;
import com.max256.abhot.core.datastore.Sampling;
import com.max256.abhot.core.datastore.TimeUnit;
import com.max256.abhot.util.Util;

@AggregatorName(
        name = "sampler",
        description = "Computes the sampling rate of change for the data points.",
        properties = {
                @AggregatorProperty(name = "unit", type = "timeUnit")
        }
)
public class SamplerAggregator implements Aggregator, TimezoneAware
{
	private Sampling m_sampling;
	private DoubleDataPointFactory m_dataPointFactory;
	private DateTimeZone m_timeZone;

	@Inject
	public SamplerAggregator(DoubleDataPointFactory dataPointFactory)
	{
		m_dataPointFactory = dataPointFactory;
		m_sampling = new Sampling(1, TimeUnit.MILLISECONDS);
	}

	public DataPointGroup aggregate(DataPointGroup dataPointGroup)
	{
		return (new SamplerDataPointAggregator(dataPointGroup));
	}

	@Override
	public boolean canAggregate(String groupType)
	{
		return DataPoint.GROUP_NUMBER.equals(groupType);
	}

	@Override
	public String getAggregatedGroupType(String groupType)
	{
		return m_dataPointFactory.getGroupType();
	}

	public void setUnit(TimeUnit timeUnit)
	{
		m_sampling = new Sampling(1, timeUnit);
	}

	@Override
	public void setTimeZone(DateTimeZone timeZone)
	{
		m_timeZone = timeZone;
	}


	private class SamplerDataPointAggregator extends AggregatedDataPointGroupWrapper
	{
		public SamplerDataPointAggregator(DataPointGroup innerDataPointGroup)
		{
			super(innerDataPointGroup);
		}

		@Override
		public boolean hasNext()
		{
			//Ensure we have two data points to mess with
			return currentDataPoint != null && hasNextInternal();
		}

		@Override
		public DataPoint next()
		{
			@SuppressWarnings("unused")
			final double x0 = currentDataPoint.getDoubleValue();
			final long y0 = currentDataPoint.getTimestamp();

			//This defaults the rate to 0 if no more data points exists
			double x1 = 0;
			long y1 = y0 + 1;

			if (hasNextInternal())
			{
				currentDataPoint = nextInternal();

				x1 = currentDataPoint.getDoubleValue();
				y1 = currentDataPoint.getTimestamp();

				if (y1 == y0)
				{
					throw new IllegalStateException(
							"The sampler aggregator cannot compute rate for data points with the same time stamp.  " +
									"You must precede sampler with another aggregator.");
				}
			}
			double rate = x1 / (y1 - y0) * Util.getSamplingDuration(y0, m_sampling, m_timeZone);

			return (m_dataPointFactory.createDataPoint(y1, rate));
		}
	}
}
