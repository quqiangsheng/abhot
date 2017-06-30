package com.max256.abhot.core.datastore;

import static com.max256.abhot.util.Preconditions.checkNotNullOrEmpty;

/**
 * Time Unit enum
 * @author fbf
 *
 */
public enum TimeUnit
{
	MILLISECONDS,
	SECONDS,
	MINUTES,
	HOURS,
	DAYS,
	WEEKS,
	MONTHS,
	YEARS;

	/**
	 * parse String Literals to TimeUnit enum
	 * if parse fail will throw IllegalArgumentException
	 * @param value
	 * @return
	 */
	public static TimeUnit from(String value)
	{
		checkNotNullOrEmpty(value);
		for (TimeUnit unit : values())
		{
			if (unit.toString().equalsIgnoreCase(value))
			{
				return unit;
			}
		}

		throw new IllegalArgumentException("No enum constant for " + value);
	}

	/**
	 * Whether TimeUnit contains a given value 
	 * @param value
	 * @return if include true,else false 
	 */
	public static boolean contains(String value)
	{
		for (TimeUnit unit : values())
		{
			if (unit.name().equalsIgnoreCase(value))
			{
				return true;
			}
		}

		return false;
	}

	public static String toValueNames()
	{
		StringBuilder builder = new StringBuilder();
		boolean firstTime = true;
		for (TimeUnit timeUnit : values())
		{
			if (!firstTime)
			{
				builder.append(',');
			}
			builder.append(timeUnit.name());
			firstTime = false;
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return TimeUnit.toValueNames();
	}
	
}

