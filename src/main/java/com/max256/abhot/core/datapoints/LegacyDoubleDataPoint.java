package com.max256.abhot.core.datapoints;

import org.json.JSONException;
import org.json.JSONWriter;
import com.max256.abhot.core.DataPoint;

import java.io.DataOutput;
import java.io.IOException;

@Deprecated
public class LegacyDoubleDataPoint extends LegacyDataPoint
{
	private double m_value;

	public LegacyDoubleDataPoint(long timestamp, double value)
	{
		super(timestamp);
		m_value = value;
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
		return DataPoint.API_DOUBLE;
	}

	@Override
	public boolean isDouble()
	{
		return true;
	}

	@Override
	public double getDoubleValue()
	{
		return m_value;
	}
}
