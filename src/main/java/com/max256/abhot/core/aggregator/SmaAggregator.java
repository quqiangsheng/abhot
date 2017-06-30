package com.max256.abhot.core.aggregator;


import com.google.inject.Inject;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.aggregator.annotation.AggregatorProperty;
import com.max256.abhot.core.datapoints.DoubleDataPointFactory;
import com.max256.abhot.core.datastore.DataPointGroup;
import com.max256.abhot.core.groupby.GroupByResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@AggregatorName(
        name = "sma",
        description = "Simple moving average.",
        properties = {
                @AggregatorProperty(name = "size", type = "integer")
        }
)
public class SmaAggregator implements Aggregator
{
	private DoubleDataPointFactory m_dataPointFactory;

	//@NonZero
	private int m_size;

	@Inject
	public SmaAggregator(DoubleDataPointFactory dataPointFactory)
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
	public DataPointGroup aggregate(DataPointGroup dataPointGroup)
	{
		checkState(m_size != 0);
		return new SmaDataPointGroup(dataPointGroup);
	}

	public void setSize(int size)
	{
		m_size = size;
	}

	private class SmaDataPointGroup implements DataPointGroup
	{
		private DataPointGroup m_innerDataPointGroup;
		ArrayList<DataPoint> subSet = new ArrayList<DataPoint>();

		public SmaDataPointGroup(DataPointGroup innerDataPointGroup)
		{
			m_innerDataPointGroup = innerDataPointGroup;

			for(int i=0;i<m_size-1;i++){
				if (innerDataPointGroup.hasNext()){
					subSet.add(innerDataPointGroup.next());
				}
			}
		}

		@Override
		public boolean hasNext()
		{
			return (m_innerDataPointGroup.hasNext());
		}

		@Override
		public DataPoint next()
		{
			DataPoint dp = m_innerDataPointGroup.next();

			subSet.add(dp);
			if(subSet.size()>m_size){
				subSet.remove(0);
			}
			
			double sum = 0;
			for(int i=0;i<subSet.size();i++){
				DataPoint dpt = subSet.get(i);
				sum += dpt.getDoubleValue();
			}
			
			dp = m_dataPointFactory.createDataPoint(dp.getTimestamp(), sum / subSet.size());

			//System.out.println(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(dp.getTimestamp())+" "+sum+" "+subSet.size());
			return (dp);
		}

		@Override
		public void remove()
		{
			m_innerDataPointGroup.remove();
		}

		@Override
		public String getName()
		{
			return (m_innerDataPointGroup.getName());
		}

		@Override
		public List<GroupByResult> getGroupByResult()
		{
			return (m_innerDataPointGroup.getGroupByResult());
		}


		@Override
		public void close()
		{
			m_innerDataPointGroup.close();
		}

		@Override
		public Set<String> getTagNames()
		{
			return (m_innerDataPointGroup.getTagNames());
		}

		@Override
		public Set<String> getTagValues(String tag)
		{
			return (m_innerDataPointGroup.getTagValues(tag));
		}
	}
}
