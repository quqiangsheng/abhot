//
// Datastore.java
//
// Copyright 2013, NextPage Inc. All rights reserved.
//

package com.max256.abhot.core.datastore;

import com.google.common.collect.ImmutableSortedMap;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.exception.DatastoreException;

public interface Datastore
{
	public void close() throws InterruptedException, DatastoreException;

	public void putDataPoint(String metricName, ImmutableSortedMap<String, String> tags, DataPoint dataPoint, int ttl) throws DatastoreException;

	public Iterable<String> getMetricNames() throws DatastoreException;

	public Iterable<String> getTagNames() throws DatastoreException;

	public Iterable<String> getTagValues() throws DatastoreException;

	public void queryDatabase(DatastoreMetricQuery query, QueryCallback queryCallback) throws DatastoreException;

	public void deleteDataPoints(DatastoreMetricQuery deleteQuery) throws DatastoreException;

	public TagSet queryMetricTags(DatastoreMetricQuery query) throws DatastoreException;
}
