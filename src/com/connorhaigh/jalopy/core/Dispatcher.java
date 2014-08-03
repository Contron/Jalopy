package com.connorhaigh.jalopy.core;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.exceptions.ServerException;
import com.connorhaigh.jalopy.http.Content;
import com.connorhaigh.jalopy.http.Http;
import com.connorhaigh.jalopy.http.Request;
import com.connorhaigh.jalopy.http.RequestHeader;
import com.connorhaigh.jalopy.http.ResponseHeader;
import com.connorhaigh.jalopy.http.StatusCode;
import com.connorhaigh.jalopy.http.responses.GenericResponseHeader;
import com.connorhaigh.jalopy.http.responses.RedirectResponseHeader;

public class Dispatcher 
{
	/**
	 * Create a new request dispatcher.
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
		this.resource = null;
		this.content = null;
		
		this.responseHeader = null;
	}
	
	/**
	 * Dispatch and deal with this request.
	 * @param HttpException if the request could not be completed successfully
	 * @throws HttpException if a HTTP exception occurs
	 * @throws ServerException if a server exception occurs
	 * @throws IOException if an I/O exception occurs
	 */
	public void dispatch() throws HttpException, ServerException, IOException
	{
		//handle
		this.handleDomain();
		this.handleResource();
		this.handleContentType();
		this.handleRequestType();
		
		//create header
		if (this.responseHeader == null)
			this.responseHeader = new GenericResponseHeader(this.server, StatusCode.OKAY, this.content);
		
		//request and handler
		Request request = new Request(this.requestHeader, this.domain, this.content, this.resource);
		Handler handler = this.mapping.createHandler(this.server, request, this.dataOutputStream);
		
		//write header
		this.dataOutputStream.write(this.responseHeader.assemble().getBytes());
		
		//handle
		if (!this.resource.isDirectory())
			handler.handle();
	}
	
	/**
	 * Handle finding the correct request handler for the type requested.
	 * @throws HttpException if the request type is not supported
	 */
	private void handleRequestType() throws HttpException
	{
		//get mapping
		this.mapping = this.server.findMappingFor(this.requestHeader.getMethod());
		if (this.mapping == null)
			throw new HttpException("No matching request mapping", StatusCode.NOT_IMPLEMENTED);
	}
	
	/**
	 * Handle finding the matching domain for the request.
	 * @throws HttpException if the domain does not exist
	 */
	private void handleDomain() throws HttpException
	{
		//get domain
		this.domain = this.server.findDomainFor(this.requestHeader.getHost());
		if (this.domain == null)
			throw new HttpException("No matching host found", StatusCode.NOT_FOUND);
	}
	
	/**
	 * Handle finding the proper resource for the request.
	 * @throws HttpException if the resource is invalid
	 */
	private void handleResource() throws HttpException
	{
		//get path and file
		String path = this.requestHeader.getPath();
		this.resource = new File(this.domain.getDirectory(), path);
		
		if (this.resource.isDirectory())
		{
			//index slash check
			if (!path.endsWith(Http.SLASH))
			{
				//get location
				String location = (this.domain.resolvePathToRelative(this.resource) + Http.SLASH);
				
				//redirect
				this.responseHeader = new RedirectResponseHeader(this.server, StatusCode.FOUND, location);
				this.resource = new File(this.domain.getDirectory(), path);
				
				return;
			}
			
			//get index files
			for (String index : this.server.getConfiguration().getIndexFiles())
			{
				//check index file
				File indexFile = new File(this.resource, index);
				if (indexFile.exists())
				{
					//use index file
					this.resource = indexFile;
					
					return;
				}
			}
		}
		
		//check permissions
		if (this.resource == null || !this.resource.exists() || this.resource.isDirectory())
			throw new HttpException("File not found", StatusCode.NOT_FOUND);
		if (!this.domain.isPathInDirectory(this.resource) || !this.resource.canRead())
			throw new HttpException("Access denied", StatusCode.ACCESS_DENIED);
	}
	
	/**
	 * Handle finding the correct content type for the resource.
	 * @throws HttpException if the content type does not exist
	 */
	private void handleContentType() throws HttpException
	{
		//get MIME and content type
		MimeType mimeType = this.server.findMimeTypeFor(this.resource);
		this.content = new Content(mimeType, this.resource.length());
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
	private File resource;
	private Content content;
	
	private ResponseHeader responseHeader;
}
