package com.connorhaigh.jalopy.http;

import com.connorhaigh.jalopy.core.Server;
import com.connorhaigh.jalopy.core.interfaces.Assemblable;

public abstract class ResponseHeader implements Assemblable
{
	/**
	 * Creates a new header.
	 * @param server the owning server
	 * @param statusCode the status code of the response
	 */
	public ResponseHeader(Server server, StatusCode statusCode)
	{
		this.server = server;
		this.statusCode = statusCode;
	}
	
	/**
	 * Assembles this header into a HTTP-compliant header.
	 * @return the assembled header
	 */
	@Override
	public abstract String assemble();
	
	/**
	 * Returns if this header should be preceded with the head of the response.
	 * @return if this header should be preceded with the head
	 */
	public abstract boolean outHead();
	
	/**
	 * Returns if this header should be followed by the body of the response.
	 * @return if this header should be followed by the body
	 */
	public abstract boolean outBody();
	
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
		result.append("Date: " + this.server.getClock().format(this.server.getClock().getNow()) + Http.CARRIAGE_RETURN);
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
