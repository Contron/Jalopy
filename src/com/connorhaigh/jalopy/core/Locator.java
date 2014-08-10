package com.connorhaigh.jalopy.core;

import java.io.File;

import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.http.Content;
import com.connorhaigh.jalopy.http.Http;
import com.connorhaigh.jalopy.http.ResponseHeader;
import com.connorhaigh.jalopy.http.StatusCode;
import com.connorhaigh.jalopy.http.responses.GenericResponseHeader;
import com.connorhaigh.jalopy.http.responses.RedirectResponseHeader;

public class Locator 
{
	/**
	 * Create a new locator.
	 * @param server the owning server
	 * @param domain the domain holding the resource
	 * @param path the relative path of the resource
	 */
	public Locator(Server server, Domain domain, String path)
	{
		this.server = server;
		this.domain = domain;
		this.path = path;
		
		this.responseHeader = null;
	}
	
	/**
	 * Locate the suitable resource from the given path.
	 * @throws HttpException if the resource is invalid
	 */
	public void locate() throws HttpException
	{
		//get resource
		this.resource = new File(this.domain.getDirectory(), this.path);
		
		if (this.resource.isDirectory())
		{
			if (!this.path.endsWith(Http.SLASH))
			{
				//redirect
				String location = (this.domain.resolvePathToRelative(this.resource) + Http.SLASH);
				this.responseHeader = new RedirectResponseHeader(this.server, StatusCode.FOUND, location);
				
				return;
			}
			
			for (String index : this.server.getConfiguration().getIndexFiles())
			{
				//check index file
				File indexFile = new File(this.resource, index);
				if (indexFile.exists())
				{
					//use index file
					this.resource = indexFile;
					
					break;
				}
			}
		}
		
		//check permissions
		if (this.resource == null || !this.resource.exists() || this.resource.isDirectory())
			throw new HttpException("File not found", StatusCode.NOT_FOUND);
		if (!this.domain.isPathInDirectory(this.resource) || !this.resource.canRead() || this.resource.isHidden())
			throw new HttpException("Access denied", StatusCode.ACCESS_DENIED);
		
		//set MIME and content type
		MimeType mimeType = this.server.findMimeTypeFor(this.resource);
		Content content = new Content(mimeType, this.resource.length());
		
		//set header
		this.responseHeader = new GenericResponseHeader(this.server, StatusCode.OKAY, content);
	}
	
	/**
	 * Returns the located resource for this locator.
	 * @return the located resource
	 */
	public File getResource()
	{
		return this.resource;
	}
	
	/**
	 * Returns the response header required to access the resource for this locator.
	 * @return the response header
	 */
	public ResponseHeader getResponseHeader()
	{
		return this.responseHeader;
	}
	
	private Server server;
	private Domain domain;
	private File resource;
	private String path;
	
	private ResponseHeader responseHeader;
}
