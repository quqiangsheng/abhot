package com.max256.abhot.core.datapoints;

import org.json.JSONException;
import org.json.JSONWriter;
import com.max256.abhot.core.aggregator.DataGapsMarkingAggregator;

import java.io.DataOutput;
import java.io.IOException;

/**
 * kairosdb 1.1.3 NullDataPoint hava exist bug ,this class have not finished
 * @author fbf
 *
 */
@Deprecated
public class NullDataPoint extends DataPointHelper
{
	public static final String API_TYPE = "null";

	public NullDataPoint(long timestamp)
	{
		super(timestamp);
	}


	@Override
	public String getDataStoreDataType()
	{
		return NullDataPointFactory.DATASTORE_TYPE;
	}

	@Override
	public void writeValueToBuffer(DataOutput buffer) throws IOException
	{
		// write nothing - only used for query results
	}

	@Override
	public void writeValueToJson(JSONWriter writer) throws JSONException
	{
		writer.value(null);
	}

	@Override
	public boolean isLong()
	{
		return false;
	}

	@Override
	public long getLongValue()
	{
		throw new IllegalArgumentException("No aggregator can be chained after " + DataGapsMarkingAggregator.class.getName());
	}

	@Override
	public boolean isDouble()
	{
		return false;
	}

	@Override
	public double getDoubleValue()
	{
		throw new IllegalArgumentException("No aggregator can be chained after " + DataGapsMarkingAggregator.class.getName());
	}

	@Override
	public String getApiDataType()
	{
		return API_TYPE;
	}
}
