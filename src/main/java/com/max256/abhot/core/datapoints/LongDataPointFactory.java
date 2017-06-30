package com.max256.abhot.core.datapoints;

import com.max256.abhot.core.DataPoint;


public interface LongDataPointFactory extends DataPointFactory
{
	public DataPoint createDataPoint(long timestamp, long value);
}
