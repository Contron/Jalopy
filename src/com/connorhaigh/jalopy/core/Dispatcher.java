package com.connorhaigh.jalopy.core;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.exceptions.ServerException;
import com.connorhaigh.jalopy.http.Content;
import com.connorhaigh.jalopy.http.Request;
import com.connorhaigh.jalopy.http.RequestHeader;
import com.connorhaigh.jalopy.http.ResponseHeader;
import com.connorhaigh.jalopy.http.StatusCode;

public class Dispatcher 
{
	/**
	 * Creates a new request dispatcher.
	 * @param server the owning server
	 * @param requestHeader the request headers of the request
	 * @param dataOutputStream the data output stream of the request
	 */
	public Dispatcher(Server server, RequestHeader requestHeader, DataOutputStream dataOutputStream)
	{
		this.server = server;
		
		this.requestHeader = requestHeader;
		this.dataOutputStream = dataOutputStream;
		
		this.mapping = null;
		this.domain = null;
		this.content = null;
		
		this.resource = null;
		this.responseHeader = null;
	}
	
	/**
	 * Dispatches and deals with this request.
	 * @param HttpException if the request could not be completed successfully
	 * @throws HttpException if a HTTP exception occurs
	 * @throws ServerException if a server exception occurs
	 * @throws IOException if an IO exception occurs
	 */
	public void dispatch() throws HttpException, ServerException, IOException
	{
		//handle
		this.handleDomain();
		this.handleRequestType();
		this.locateResource();
		
		//request and handler
		Request request = new Request(this.requestHeader, this.domain, this.content, this.resource);
		Handler handler = this.mapping.createHandler(this.server, request, this.dataOutputStream);
		
		//head
		if (this.responseHeader.outHead())
			this.dataOutputStream.write(this.responseHeader.assemble().getBytes());
		
		//body
		if (this.responseHeader.outBody())
			handler.handle();
	}
	
	/**
	 * Handles finding the matching domain for the request.
	 * @throws HttpException if the domain does not exist
	 */
	private void handleDomain() throws HttpException
	{
		//get domain
		this.domain = this.server.findDomainFor(this.requestHeader.getHost());
		
		if (this.domain == null)
			throw new HttpException("No matching domain for host found", StatusCode.NOT_FOUND);
	}
	
	/**
	 * Handles finding the correct request handler for the type requested.
	 * @throws HttpException if the request type is not supported
	 */
	private void handleRequestType() throws HttpException
	{
		//get mapping
		this.mapping = this.server.findMappingFor(this.requestHeader.getMethod());
		
		if (this.mapping == null)
			throw new HttpException("No matching handler for method found", StatusCode.NOT_IMPLEMENTED);
	}
	
	/**
	 * Locates the requested resource for this request.
	 * @throws HttpException if the resource is invalid
	 */
	private void locateResource() throws HttpException
	{
		//create locator
		Locator locator = new Locator(this.server, this.domain, this.requestHeader.getPath());
		locator.locate();
		
		//update
		this.resource = locator.getResource();
		this.responseHeader = locator.getResponseHeader();
	}
	
	/**
	 * Returns the response header for this dispatcher.
	 * @return the response header
	 */
	public ResponseHeader getResponseHeader()
	{
		return this.responseHeader;
	}
	
	private Server server;
	
	private RequestHeader requestHeader;
	private DataOutputStream dataOutputStream;
	
	private Mapping mapping;
	private Domain domain;
	private Content content;
	
	private File resource;
	private ResponseHeader responseHeader;
}
