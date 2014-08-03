package com.connorhaigh.jalopy.exceptions;

import com.connorhaigh.jalopy.http.StatusCode;

public class HttpException extends Exception
{
	/**
	 * Create a new HTTP exception with a matching status code.
	 * @param message the detailed message of the exception
	 * @param statusCode the relevant status code
	 */
	public HttpException(String message, StatusCode statusCode)
	{
		super(message);
		
		this.statusCode = statusCode;
	}
	
	/**
	 * Returns the status code for this exception.
	 * @return the status code
	 */
	public StatusCode getStatusCode()
	{
		return this.statusCode;
	}
	
	public static final long serialVersionUID = 1;
	
	private StatusCode statusCode;
}
