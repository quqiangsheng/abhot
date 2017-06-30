package com.max256.abhot.core.datastore;

import com.google.common.collect.TreeMultimap;

import java.util.Set;


/**
 * TagSetImpl
 * @author fbf
 *
 */
public class TagSetImpl implements TagSet
{
	private TreeMultimap<String, String> m_tags = TreeMultimap.create();

	public void addTag(String name, String value)
	{
		m_tags.put(name, value);
	}

	@Override
	public Set<String> getTagNames()
	{
		return (m_tags.keySet());
	}

	@Override
	public Set<String> getTagValues(String tag)
	{
		return (m_tags.get(tag));
	}
}
