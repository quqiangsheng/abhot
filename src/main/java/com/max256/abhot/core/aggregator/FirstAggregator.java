package com.max256.abhot.core.aggregator;

import com.google.inject.Inject;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.aggregator.annotation.AggregatorProperty;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;

import java.util.Collections;
import java.util.Iterator;

/**
 Converts all longs to double. This will cause a loss of precision for very large long values.
 */
@AggregatorName(
        name = "first",
        description = "Returns the first value data point for the time range.",
        properties = {
                @AggregatorProperty(name = "sampling", type = "duration"),
                @AggregatorProperty(name="align_start_time", type="boolean")
        }
)
public class FirstAggregator extends RangeAggregator
{
	private DoubleDataPointFactory m_dataPointFactory;

	@Inject
	public FirstAggregator(DoubleDataPointFactory dataPointFactory)
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
		return (new FirstDataPointAggregator());
	}

	private class FirstDataPointAggregator implements RangeSubAggregator
	{
		@Override
		public Iterable<DataPoint> getNextDataPoints(long returnTime, Iterator<DataPoint> dataPointRange)
		{
			Iterable<DataPoint> ret;
			if (dataPointRange.hasNext())
				ret = Collections.singletonList(m_dataPointFactory.createDataPoint(returnTime, dataPointRange.next().getDoubleValue()));
			else
				ret = Collections.emptyList();

			//Chew up the rest of the data points in range
			while (dataPointRange.hasNext())
			{
				dataPointRange.next();
			}

			return ret;
		}
	}
}