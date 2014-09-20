package com.connorhaigh.jalopy.http.responses;

import com.connorhaigh.jalopy.core.Server;
import com.connorhaigh.jalopy.http.Content;
import com.connorhaigh.jalopy.http.Http;
import com.connorhaigh.jalopy.http.ResponseHeader;
import com.connorhaigh.jalopy.http.StatusCode;

public class GenericResponseHeader extends ResponseHeader
{
	/**
	 * Creates a new generic header.
	 * @param server the owning server
	 * @param statusCode the status code
	 * @param content the content definition
	 */
	public GenericResponseHeader(Server server, StatusCode statusCode, Content content)
	{
		super(server, statusCode);
		
		this.content = content;
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
		result.append(this.coreValues());
		result.append("Content-Type: " + this.content.getMimeType().getRealType() + Http.CARRIAGE_RETURN);
		result.append("Content-Length: " + this.content.getLength() + Http.CARRIAGE_RETURN);
		result.append("Last-Modified: " + this.getServer().getClock().format(this.content.getLastModified()) + Http.CARRIAGE_RETURN);
		
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
		return true;
	}
	
	private Content content;
}
