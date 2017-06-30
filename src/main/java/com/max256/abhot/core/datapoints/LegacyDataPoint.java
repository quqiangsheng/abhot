package com.max256.abhot.core.datapoints;

@Deprecated
public abstract class LegacyDataPoint extends DataPointHelper
{
	public LegacyDataPoint(long timestamp)
	{
		super(timestamp);
	}


	@Override
	public String getDataStoreDataType()
	{
		return LegacyDataPointFactory.DATASTORE_TYPE;
	}

	@Override
	public boolean isLong()
	{
		return false;
	}

	@Override
	public long getLongValue()
	{
		return 0;
	}

	@Override
	public boolean isDouble()
	{
		return false;
	}

	@Override
	public double getDoubleValue()
	{
		return 0;
	}
}
