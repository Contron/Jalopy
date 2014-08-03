package com.connorhaigh.jalopy.exceptions;

public class ServerException extends Exception
{
	/**
	 * Create a new server exception.
	 * @param message the message
	 */
	public ServerException(String message)
	{
		super(message);
	}
	
	public static final long serialVersionUID = 1;
}
