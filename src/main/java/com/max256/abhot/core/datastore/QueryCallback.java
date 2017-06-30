package com.max256.abhot.core.datastore;

import java.io.IOException;
import java.util.Map;

import com.max256.abhot.core.DataPoint;


public interface QueryCallback
{
	public void addDataPoint(DataPoint datapoint) throws IOException;
	
	public void startDataPointSet(String dataType, Map<String, String> tags) throws IOException;
	public void endDataPoints() throws IOException;
}
