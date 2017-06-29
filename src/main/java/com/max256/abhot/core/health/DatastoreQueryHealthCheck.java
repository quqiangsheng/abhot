package com.max256.abhot.core.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.max256.abhot.core.datastore.Datastore;
import com.max256.abhot.core.exception.DatastoreException;

import static com.google.common.base.Preconditions.checkNotNull;

public class DatastoreQueryHealthCheck extends HealthCheck implements HealthStatus
{
	static final String NAME = "Datastore-Query";
	private final Datastore datastore;

	@Inject
	public DatastoreQueryHealthCheck(Datastore datastore)
	{
		this.datastore = checkNotNull(datastore);
	}

	@Override
	public String getName()
	{
		return NAME;
	}

	@Override
	protected Result check() throws Exception
	{
		try
		{
			datastore.getMetricNames();
			return Result.healthy();
		}
		catch (DatastoreException e)
		{
			return Result.unhealthy(e);
		}
	}
}
