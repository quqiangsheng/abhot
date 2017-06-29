package com.max256.abhot.core.health;

import java.util.List;

public interface HealthCheckService
{
	List<HealthStatus> getChecks();
}
