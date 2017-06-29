package com.max256.abhot.core.health;

import static com.codahale.metrics.health.HealthCheck.Result;

public interface HealthStatus
{
	String getName();

	public Result execute();
}
