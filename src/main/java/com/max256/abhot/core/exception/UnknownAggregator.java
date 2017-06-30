package com.max256.abhot.core.exception;

public class UnknownAggregator extends DatastoreException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownAggregator(String message)
	{
		super(message);
	}

	public UnknownAggregator(String message, Throwable cause)
	{
		super(message, cause);
	}
}