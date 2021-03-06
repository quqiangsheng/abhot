package com.max256.abhot.core.datastore;

import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.groupby.GroupByResult;

import java.util.Collections;
import java.util.List;
import java.util.Set;


public class EmptyDataPointGroup implements DataPointGroup
{
	private String m_name;
	private TagSet m_tags;

	public EmptyDataPointGroup(String name, TagSet tags)
	{
		m_name = name;
		m_tags = tags;
	}

	@Override
	public String getName()
	{
		return (m_name);
	}

	@Override
	public List<GroupByResult> getGroupByResult()
	{
		return (Collections.emptyList());
	}

	@Override
	public void close()
	{
	}

	@Override
	public boolean hasNext()
	{
		return false;
	}

	@Override
	public DataPoint next()
	{
		return null;
	}

	@Override
	public void remove()
	{
	}

	@Override
	public Set<String> getTagNames()
	{
		return (m_tags.getTagNames());
	}

	@Override
	public Set<String> getTagValues(String tag)
	{
		return (m_tags.getTagValues(tag));
	}
}
