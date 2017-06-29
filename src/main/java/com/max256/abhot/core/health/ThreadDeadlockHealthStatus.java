package com.max256.abhot.core.health;

import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;

public class ThreadDeadlockHealthStatus extends ThreadDeadlockHealthCheck implements HealthStatus
{
	@Override
	public String getName()
	{
		return "JVM-Thread-Deadlock";
	}
}
