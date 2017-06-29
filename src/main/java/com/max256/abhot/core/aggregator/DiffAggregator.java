package com.max256.abhot.core.aggregator;

import com.google.inject.Inject;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;
import com.max256.abhot.core.datastore.DataPointGroup;

/**
 Created by bhawkins on 12/16/14.
 */
@AggregatorName(name = "diff", description = "Computes the difference between successive data points.")
public class DiffAggregator implements Aggregator
{
	private DoubleDataPointFactory m_dataPointFactory;

	@Inject
	public DiffAggregator(DoubleDataPointFactory dataPointFactory)
	{
		m_dataPointFactory = dataPointFactory;
	}

	@Override
	public DataPointGroup aggregate(DataPointGroup dataPointGroup)
	{
		return new DiffDataPointGroup(dataPointGroup);
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

	private class DiffDataPointGroup extends AggregatedDataPointGroupWrapper
	{

		public DiffDataPointGroup(DataPointGroup innerDataPointGroup)
		{
			super(innerDataPointGroup);
		}

		@Override
		public boolean hasNext()
		{
			return currentDataPoint != null && hasNextInternal();
		}

		@Override
		public DataPoint next()
		{
			final double lastValue = currentDataPoint.getDoubleValue();

			//This defaults the rate to 0 if no more data points exists
			double newValue = lastValue;

			if (hasNextInternal())
			{
				currentDataPoint = nextInternal();

				newValue = currentDataPoint.getDoubleValue();
			}

			double diff = newValue - lastValue;

			return (m_dataPointFactory.createDataPoint(currentDataPoint.getTimestamp(), diff));
		}
	}
}
