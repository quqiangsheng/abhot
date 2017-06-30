package com.max256.abhot.core.aggregator;

import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.aggregator.annotation.AggregatorProperty;
import com.max256.abhot.core.datastore.DataPointGroup;


@AggregatorName(
        name = "filter",
        description = "Filters datapoints according to filter operation with a null data point.",
        properties = {
                @AggregatorProperty(name = "filter_op", type = "enum", values = {"lte", "lt", "gte", "gt", "equal"}),
                @AggregatorProperty(name="threshold", type="double")
        }
)
public class FilterAggregator implements Aggregator
{
	public enum FilterOperation
	{
		LTE, LT, GTE, GT, EQUAL
	}

	;

	public FilterAggregator()
	{
		m_threshold = 0.0;
	}

	public FilterAggregator(FilterOperation filterop, double threshold)
	{
		m_filterop = filterop;
		m_threshold = threshold;
	}

	private FilterOperation m_filterop;
	private double m_threshold;

	/**
	 Sets filter operation to apply to data points. Values can be LTE, LE, GTE, GT, or EQUAL.

	 @param filterop
	 */
	public void setFilterOp(FilterOperation filterop)
	{
		m_filterop = filterop;
	}

	public void setThreshold(double threshold)
	{
		m_threshold = threshold;
	}

	public DataPointGroup aggregate(DataPointGroup dataPointGroup)
	{
		return new FilterDataPointAggregator(dataPointGroup);
	}

	public boolean canAggregate(String groupType)
	{
		return true;
	}

	public String getAggregatedGroupType(String groupType)
	{
		return groupType;
	}

	private class FilterDataPointAggregator extends AggregatedDataPointGroupWrapper
	{
		public FilterDataPointAggregator(DataPointGroup innerDataPointGroup)
		{
			super(innerDataPointGroup);
		}

		public boolean hasNext()
		{
			boolean foundValidDp = false;
			while (!foundValidDp && currentDataPoint != null)
			{
				double x0 = currentDataPoint.getDoubleValue();
				if (m_filterop == FilterOperation.LTE && x0 <= m_threshold)
					moveCurrentDataPoint();
				else if (m_filterop == FilterOperation.LT && x0 < m_threshold)
					moveCurrentDataPoint();
				else if (m_filterop == FilterOperation.GTE && x0 >= m_threshold)
					moveCurrentDataPoint();
				else if (m_filterop == FilterOperation.GT && x0 > m_threshold)
					moveCurrentDataPoint();
				else if (m_filterop == FilterOperation.EQUAL && x0 == m_threshold)
					moveCurrentDataPoint();
				else
					foundValidDp = true;
			}

			return foundValidDp;
		}

		public DataPoint next()
		{
			DataPoint ret = currentDataPoint;
			moveCurrentDataPoint();
			return ret;
		}

		private void moveCurrentDataPoint()
		{
			if (hasNextInternal())
				currentDataPoint = nextInternal();
			else
				currentDataPoint = null;
		}
	}
}
