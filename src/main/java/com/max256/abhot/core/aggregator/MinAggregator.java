package com.max256.abhot.core.aggregator;

import com.google.inject.Inject;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.aggregator.annotation.AggregatorProperty;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;

import java.util.Collections;
import java.util.Iterator;

/**
 * Converts all longs to double. This will cause a loss of precision for very large long values.
 */
@AggregatorName(
        name = "min",
        description = "Returns the minimum value data point for the time range.",
        properties = {
                @AggregatorProperty(name = "sampling", type = "duration"),
                @AggregatorProperty(name="align_start_time", type="boolean")
        }
)
public class MinAggregator extends RangeAggregator
{
	private DoubleDataPointFactory m_dataPointFactory;


	@Inject
	public MinAggregator(DoubleDataPointFactory dataPointFactory)
	{
		m_dataPointFactory = dataPointFactory;
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

	@Override
	protected RangeSubAggregator getSubAggregator()
	{
		return (new MinDataPointAggregator());
	}

	private class MinDataPointAggregator implements RangeSubAggregator
	{

		@Override
		public Iterable<DataPoint> getNextDataPoints(long returnTime, Iterator<DataPoint> dataPointRange)
		{
			double min = Double.MAX_VALUE;
			while (dataPointRange.hasNext())
			{
				DataPoint dp = dataPointRange.next();

				min = Math.min(min, dp.getDoubleValue());
			}

			return Collections.singletonList(m_dataPointFactory.createDataPoint(returnTime, min));
		}
	}
}