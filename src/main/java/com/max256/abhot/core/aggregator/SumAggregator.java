package com.max256.abhot.core.aggregator;

import com.google.inject.Inject;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.aggregator.annotation.AggregatorProperty;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;

/**
 * Converts all longs to double. This will cause a loss of precision for very large long values.
 */
@AggregatorName(
        name = "sum",
        description = "Adds data points together.",
        properties = {
                @AggregatorProperty(name = "sampling", type = "duration"),
                @AggregatorProperty(name="align_start_time", type="boolean")
        }
)
public class SumAggregator extends RangeAggregator
{
	public static final Logger logger = LoggerFactory.getLogger(SumAggregator.class);

	private DoubleDataPointFactory m_dataPointFactory;

	@Inject
	public SumAggregator(DoubleDataPointFactory dataPointFactory)
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
		return (new SumDataPointAggregator());
	}

	private class SumDataPointAggregator implements RangeSubAggregator
	{

		@Override
		public Iterable<DataPoint> getNextDataPoints(long returnTime, Iterator<DataPoint> dataPointRange)
		{
			double sum = 0;
			int counter = 0;
			while (dataPointRange.hasNext())
			{
				sum += dataPointRange.next().getDoubleValue();
				counter ++;
			}

			if (logger.isDebugEnabled())
			{
				logger.debug("Aggregating "+counter+" values");
			}

			return Collections.singletonList(m_dataPointFactory.createDataPoint(returnTime, sum));
		}
	}
}
