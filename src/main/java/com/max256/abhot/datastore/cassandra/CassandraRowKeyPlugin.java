package com.max256.abhot.datastore.cassandra;

import java.util.Iterator;

import com.max256.abhot.core.datastore.DatastoreMetricQuery;
import com.max256.abhot.core.datastore.QueryPlugin;

/**
 Created by bhawkins on 11/23/14.
 */
public interface CassandraRowKeyPlugin extends QueryPlugin
{
	/**
	 Must return the row keys for a query grouped by time

	 @param query
	 @return
	 */
	public Iterator<DataPointsRowKey> getKeysForQueryIterator(DatastoreMetricQuery query);
}
