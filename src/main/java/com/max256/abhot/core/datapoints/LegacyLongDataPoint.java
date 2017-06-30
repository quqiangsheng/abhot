package com.max256.abhot.core.datapoints;

import org.json.JSONException;
import org.json.JSONWriter;
import com.max256.abhot.core.DataPoint;

import java.io.DataOutput;
import java.io.IOException;

@Deprecated
public class LegacyLongDataPoint extends LegacyDataPoint
{
	private long m_value;

	public LegacyLongDataPoint(long timestamp, long l)
	{
		super(timestamp);
		m_value = l;
	}


	/*@Override
	public ByteBuffer toByteBuffer()
	{
		return LegacyDataPointFactory.writeToByteBuffer(this);
	}*/

	@Override
	public void writeValueToBuffer(DataOutput buffer) throws IOException
	{
		LegacyDataPointFactory.writeToByteBuffer(buffer, this);
	}

	@Override
	public void writeValueToJson(JSONWriter writer) throws JSONException
	{
		writer.value(m_value);
	}

	@Override
	public String getApiDataType()
	{
		return DataPoint.API_LONG;
	}

	@Override
	public boolean isLong()
	{
		return true;
	}

	@Override
	public boolean isDouble()
	{
		return true;
	}

	@Override
	public long getLongValue()
	{
		return m_value;
	}

	@Override
	public double getDoubleValue()
	{
		return (double)m_value;
	}
}
