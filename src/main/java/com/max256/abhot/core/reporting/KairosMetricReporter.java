//
// KairosMetricReporter.java
//
// Copyright 2013, NextPage Inc. All rights reserved.
//

package com.max256.abhot.core.reporting;

import com.max256.abhot.core.DataPointSet;

import java.util.List;

public interface KairosMetricReporter
{
	public List<DataPointSet> getMetrics(long now);
}
