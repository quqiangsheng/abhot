package com.max256.abhot.core.exception;

/**
 * Wraps exceptions from the data stores.
 */
public class DatastoreException extends KairosDBException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatastoreException(String message)
	{
		super(message);
	}

	public DatastoreException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DatastoreException(Throwable cause)
	{
		super(cause);
	}
}