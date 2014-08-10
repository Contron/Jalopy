package com.connorhaigh.jalopy.http;

import java.time.LocalDateTime;

import com.connorhaigh.jalopy.core.Server;
import com.connorhaigh.jalopy.core.interfaces.Assemblable;
import com.connorhaigh.jalopy.resources.DateTimeFormatters;

public abstract class ResponseHeader implements Assemblable
{
	/**
	 * Create a new header.
	 * @param server the owning server
	 * @param statusCode the status code of the response
	 */
	public ResponseHeader(Server server, StatusCode statusCode)
	{
		this.server = server;
		this.statusCode = statusCode;
	}
	
	/**
	 * Assemble this header into a HTTP-compliant header.
	 * @return the assembled header
	 */
	@Override
	public abstract String assemble();
	
	/**
	 * Returns if this header should be followed by the body of the response.
	 * @return if this header should be followed by the body
	 */
	public abstract boolean body();
	
	/**
	 * Returns the HTTP specification for any header.
	 * @return the HTTP specification
	 */
	public String coreHeader()
	{
		return ("HTTP/1.0 " + this.getStatusCode().getRealCode() + Http.CARRIAGE_RETURN);
	}
	
	/**
	 * Returns the core values for any header.
	 * @return the core values
	 */
	public String coreValues()
	{
		//result
		StringBuilder result = new StringBuilder();
		
		//header
		result.append("Date: " + DateTimeFormatters.HTTP_TIME.format(LocalDateTime.now()) + Http.CARRIAGE_RETURN);
		result.append("Server: " + this.getServer().getDetailedName() + Http.CARRIAGE_RETURN);
		result.append("Connection: close" + Http.CARRIAGE_RETURN);
		
		return result.toString();
	}
	
	/**
	 * Returns the server for this response header.
	 * @return the server
	 */
	public Server getServer()
	{
		return this.server;
	}
	
	/**
	 * Returns the status code for this response header.
	 * @return the status code
	 */
	public StatusCode getStatusCode()
	{
		return this.statusCode;
	}
	
	private Server server;
	private StatusCode statusCode;
}
