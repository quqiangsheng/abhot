package com.max256.abhot.core.aggregator;

import com.google.common.collect.ImmutableSortedMap;
import com.google.inject.Inject;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.aggregator.annotation.AggregatorName;
import com.max256.abhot.core.aggregator.annotation.AggregatorProperty;
import com.max256.abhot.core.datastore.DataPointGroup;
import com.max256.abhot.core.datastore.Datastore;
import com.max256.abhot.core.exception.DatastoreException;
import com.max256.abhot.core.groupby.GroupBy;
import com.max256.abhot.core.groupby.GroupByResult;
import com.max256.abhot.core.groupby.TagGroupBy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 Created by bhawkins on 8/28/15.
 */
@AggregatorName(
        name = "save_as",
        description = "Saves the results to a new metric.",
        properties = {
                @AggregatorProperty(name = "metric_name", type = "string")
        }
)
public class SaveAsAggregator implements Aggregator, GroupByAware
{
	private Datastore m_datastore;
	private String m_metricName;
	private Map<String, String> m_tags;
	private int m_ttl = 0;
	private Set<String> m_tagsToKeep = new HashSet<>();
	private boolean m_addSavedFrom = true;

	@Inject
	public SaveAsAggregator(Datastore datastore)
	{
		m_datastore = datastore;
		m_tags = new HashMap<String, String>();
	}

	public SaveAsAggregator(Datastore datastore, String metricName)
	{
		this(datastore);
		m_metricName = metricName;
		m_tags = new HashMap<String, String>();
	}

	public void setAddSavedFrom(boolean addSavedFrom)
	{
		m_addSavedFrom = addSavedFrom;
	}

	public void setMetricName(String metricName)
	{
		m_metricName = metricName;
	}

	public void setTags(Map<String, String> tags)
	{
		m_tags = tags;
	}

	public void setTtl(int ttl)
	{
		m_ttl = ttl;
	}

	public String getMetricName()
	{
		return m_metricName;
	}

	public Map<String, String> getTags()
	{
		return m_tags;
	}

	@Override
	public DataPointGroup aggregate(DataPointGroup dataPointGroup)
	{
		return new SaveAsDataPointAggregator(dataPointGroup);
	}

	@Override
	public boolean canAggregate(String groupType)
	{
		return true;
	}

	@Override
	public String getAggregatedGroupType(String groupType)
	{
		return groupType;
	}

	@Override
	public void setGroupBys(List<GroupBy> groupBys)
	{
		for (GroupBy groupBy : groupBys)
		{
			if (groupBy instanceof TagGroupBy)
			{
				TagGroupBy tagGroupBy = (TagGroupBy) groupBy;

				m_tagsToKeep.addAll(tagGroupBy.getTagNames());
			}
		}
	}

	private class SaveAsDataPointAggregator implements DataPointGroup
	{
		private DataPointGroup m_innerDataPointGroup;
		private ImmutableSortedMap<String, String> m_groupTags;

		public SaveAsDataPointAggregator(DataPointGroup innerDataPointGroup)
		{
			m_innerDataPointGroup = innerDataPointGroup;
			ImmutableSortedMap.Builder<String, String> mapBuilder = ImmutableSortedMap.<String, String>naturalOrder();
			mapBuilder.putAll(m_tags);
			if (m_addSavedFrom)
				mapBuilder.put("saved_from", innerDataPointGroup.getName());

			for (String innerTag : innerDataPointGroup.getTagNames())
			{
				Set<String> tagValues = innerDataPointGroup.getTagValues(innerTag);
				if (m_tagsToKeep.contains(innerTag) && (tagValues.size() == 1))
					mapBuilder.put(innerTag, tagValues.iterator().next());
			}

			m_groupTags = mapBuilder.build();
		}

		@Override
		public boolean hasNext()
		{
			return m_innerDataPointGroup.hasNext();
		}

		@Override
		public DataPoint next()
		{
			DataPoint next = m_innerDataPointGroup.next();

			try
			{
				m_datastore.putDataPoint(m_metricName, m_groupTags, next, m_ttl);
			}
			catch (DatastoreException e)
			{
				throw new RuntimeException("Failure to save data to "+m_metricName, e);
			}

			return next;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public String getName()
		{
			return m_innerDataPointGroup.getName();
		}

		@Override
		public List<GroupByResult> getGroupByResult()
		{
			return m_innerDataPointGroup.getGroupByResult();
		}

		@Override
		public void close()
		{
			m_innerDataPointGroup.close();
		}

		@Override
		public Set<String> getTagNames()
		{
			return m_innerDataPointGroup.getTagNames();
		}

		@Override
		public Set<String> getTagValues(String tag)
		{
			return m_innerDataPointGroup.getTagValues(tag);
		}
	}
}
