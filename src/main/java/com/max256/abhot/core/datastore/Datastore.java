//
// Datastore.java
//
// Copyright 2013, NextPage Inc. All rights reserved.
//

package com.max256.abhot.core.datastore;

import com.google.common.collect.ImmutableSortedMap;
import com.max256.abhot.core.DataPoint;
import com.max256.abhot.core.exception.DatastoreException;

/**
 * data backing store core interface
 * @author fbf
 *
 */
public interface Datastore
{
	/**
	 * connection resource close
	 * @throws InterruptedException
	 * @throws DatastoreException
	 */
	public void close() throws InterruptedException, DatastoreException;

	/**
	 * put a DataPoint
	 * @param metricName
	 * @param tags
	 * @param dataPoint
	 * @param ttl time to live
	 * @throws DatastoreException
	 */
	public void putDataPoint(String metricName, ImmutableSortedMap<String, String> tags, DataPoint dataPoint, int ttl) throws DatastoreException;

	/**
	 * get all metric name
	 * @return
	 * @throws DatastoreException
	 */
	public Iterable<String> getMetricNames() throws DatastoreException;

	/**
	 * get all tags key name
	 * @return
	 * @throws DatastoreException
	 */
	public Iterable<String> getTagNames() throws DatastoreException;
	/**
	 * get all tags value 
	 * @return
	 * @throws DatastoreException
	 */
	public Iterable<String> getTagValues() throws DatastoreException;

	/**
	 * query database
	 * @param query
	 * @param queryCallback
	 * @throws DatastoreException
	 */
	public void queryDatabase(DatastoreMetricQuery query, QueryCallback queryCallback) throws DatastoreException;

	/**
	 * delete DataPoint Whether it is necessary to prove it?
	 * @param deleteQuery
	 * @throws DatastoreException
	 */
	public void deleteDataPoints(DatastoreMetricQuery deleteQuery) throws DatastoreException;

	/**query metric tags
	 * @param query
	 * @return
	 * @throws DatastoreException
	 */
	public TagSet queryMetricTags(DatastoreMetricQuery query) throws DatastoreException;
}
