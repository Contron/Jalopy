package com.connorhaigh.jalopy.http.responses;

import com.connorhaigh.jalopy.core.Server;
import com.connorhaigh.jalopy.http.Http;
import com.connorhaigh.jalopy.http.ResponseHeader;
import com.connorhaigh.jalopy.http.StatusCode;

public class RedirectResponseHeader extends ResponseHeader
{
	/**
	 * Creates a new redirect response header.
	 * @param server the owning server
	 * @param statusCode the status code
	 * @param location the location to redirect to
	 */
	public RedirectResponseHeader(Server server, StatusCode statusCode, String location)
	{
		super(server, statusCode);
		
		this.location = location;
	}
	
	/**
	 * Assembles this header into a HTTP-compliant header.
	 * @return the assembled header	
	 */
	@Override
	public String assemble()
	{
		//result
		StringBuilder result = new StringBuilder();
		
		//core
		result.append(this.coreHeader());
		result.append("Location: " + this.location + Http.CARRIAGE_RETURN);
		
		//finalise
		result.append(Http.CARRIAGE_RETURN);
		
		return result.toString();
	}
	
	/**
	 * Returns if this header should be preceded with the head of the response.
	 * @return if this header should be preceded with the head
	 */
	@Override
	public boolean outHead()
	{
		return true;
	}
	
	/**
	 * Returns if this header should be followed by the body of the response.
	 * @return if this header should be followed by the body
	 */
	@Override
	public boolean outBody()
	{
		return false;
	}
	
	private String location;
}
