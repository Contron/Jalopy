package com.connorhaigh.jalopy.http;

import java.io.File;

import com.connorhaigh.jalopy.core.Domain;

public class Request 
{
	/**
	 * Creates a new request.
	 * @param requestHeader the request headers received
	 * @param domain the domain for the request
	 * @param resource the specific resource of the request
	 */
	public Request(RequestHeader requestHeader, Domain domain, Content content, File resource)
	{
		this.requestHeader = requestHeader;
		this.domain = domain;
		this.content = content;
		this.resource = resource;
	}
	
	/**
	 * Returns the request header for this request.
	 * @return the request header
	 */
	public RequestHeader getRequestHeader()
	{
		return this.requestHeader;
	}
	
	/**
	 * Returns the domain for this request.
	 * @return the domain
	 */
	public Domain getDomain()
	{
		return this.domain;
	}
	
	/**
	 * Returns the content definition for this request.
	 * @return the content definition
	 */
	public Content getContent()
	{
		return this.content;
	}
	
	/**
	 * Returns the requested resource for this request.
	 * @return the requested resource
	 */
	public File getResource()
	{
		return this.resource;
	}
	
	private RequestHeader requestHeader;
	private Domain domain;
	private Content content;
	private File resource;
}
