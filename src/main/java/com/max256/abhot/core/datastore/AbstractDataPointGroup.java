
package com.max256.abhot.core.datastore;

import com.google.common.collect.*;
import com.max256.abhot.core.groupby.GroupByResult;
import com.max256.abhot.util.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractDataPointGroup implements DataPointGroup
{
	private String name;
	private TreeMultimap<String, String> tags = TreeMultimap.create();
	private List<GroupByResult> groupByResult = new ArrayList<>();

	public AbstractDataPointGroup(String name)
	{
		this.name = name;
	}

	public AbstractDataPointGroup(String name, SetMultimap<String, String> tags)
	{
		this.name = Preconditions.checkNotNullOrEmpty(name);
		this.tags = TreeMultimap.create(tags);
	}

	public void addTag(String name, String value)
	{
		tags.put(name, value);
	}

	public void addTags(SetMultimap<String, String> tags)
	{
		this.tags.putAll(tags);
	}

	public void addTags(Map<String, String> tags)
	{
		this.tags.putAll(Multimaps.forMap(tags));
	}

	public void addTags(DataPointGroup dpGroup)
	{
		for (String key : dpGroup.getTagNames())
		{
			for (String value : dpGroup.getTagValues(key))
			{
				this.tags.put(key, value);
			}
		}
	}

	public void addGroupByResult(GroupByResult groupByResult)
	{
		this.groupByResult.add(checkNotNull(groupByResult));
	}

	public List<GroupByResult> getGroupByResult()
	{
		return groupByResult;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public Set<String> getTagNames()
	{
		return (tags.keySet());
	}

	@Override
	public Set<String> getTagValues(String tag)
	{
		return (tags.get(tag));
	}

	public SetMultimap<String, String> getTags()
	{
		return (ImmutableSetMultimap.copyOf(tags));
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	public abstract void close();
}