package com.max256.abhot.core.datapoints;


import java.io.DataOutput;
import java.io.IOException;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONWriter;


public class DoubleDataPoint extends DataPointHelper
{
	private double m_value;

	public DoubleDataPoint(long timestamp, double value)
	{
		super(timestamp);
		m_value = value;
	}

	@Override
	public double getDoubleValue()
	{
		return (m_value);
	}

	/*@Override
	public ByteBuffer toByteBuffer()
	{
		return DoubleDataPointFactoryImpl.writeToByteBuffer(this);
	}*/

	@Override
	public void writeValueToBuffer(DataOutput buffer) throws IOException
	{
		DoubleDataPointFactoryImpl.writeToByteBuffer(buffer, this);
	}

	@Override
	public void writeValueToJson(JSONWriter writer) throws JSONException
	{	
		//in kairosdb 1.1.3 is a bug : m_value != m_value??? 
		if (Double.isInfinite(m_value))
			throw new IllegalStateException("not a number or Infinity:" + m_value + " data point=" + this);

		writer.value(m_value);
	}

	@Override
	public String getApiDataType()
	{
		return API_DOUBLE;
	}

	@Override
	public String getDataStoreDataType()
	{
		return DoubleDataPointFactoryImpl.DST_DOUBLE;
	}

	@Override
	public boolean isLong()
	{
		return false;
	}

	@Override
	public long getLongValue()
	{
		return (long)m_value;
	}

	@Override
	public boolean isDouble()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DoubleDataPoint that = (DoubleDataPoint) o;

		if (Double.compare(that.m_value, m_value) != 0) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		long temp = Double.doubleToLongBits(m_value);
		return (int) (temp ^ (temp >>> 32));
	}

	@Override
	public String toString() {
		return "DoubleDataPoint ["
				+ "m_timestamp=" + new DateTime(m_timestamp) +
				"m_value=" + m_value + "]";
	}
	
}
