package com.max256.abhot.rollup;

import com.max256.abhot.core.exception.KairosDBException;

public class RollUpException extends KairosDBException
{
	public RollUpException(String message)
	{
		super(message);
	}

	public RollUpException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public RollUpException(Throwable cause)
	{
		super(cause);
	}
}
