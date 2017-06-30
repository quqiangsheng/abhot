package com.max256.abhot.core.datapoints;

import com.max256.abhot.core.DataPoint;


public interface DoubleDataPointFactory extends DataPointFactory
{
	public DataPoint createDataPoint(long timestamp, double value);
}
