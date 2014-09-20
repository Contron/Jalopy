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
	 * Creates a new locator.
	 * @param server the owning server
	 * @param domain the domain holding the resource
	 * @param path the relative path of the resource
	 */
	public Locator(Server server, Domain domain, String path)
	{
		this.server = server;
		this.domain = domain;
		this.path = path;
		
		this.resource = null;
		this.responseHeader = null;
	}
	
	/**
	 * Locates the suitable resource from the given path.
	 * @throws HttpException if the resource is invalid
	 */
	public void locate() throws HttpException
	{
		//create resource
		this.resource = new File(this.domain.getDirectory(), this.path);
		
		if (this.resource.isDirectory())
		{
			//trailing slash
			if (this.checkTrailingSlash())
				return;
			
			//find index file
			this.findIndexFile();
		}
		
		//verify and prepare
		this.verifyResource();
		this.prepareResource();
	}
	
	/**
	 * Checks if the URL requires a trailing slash, and redirects if necessary.
	 */
	private boolean checkTrailingSlash()
	{
		//ignore slash
		if (this.path.endsWith(Http.SLASH))
			return false;
		
		//redirect
		String location = (this.domain.resolvePathToRelative(this.resource) + Http.SLASH);
		this.responseHeader = new RedirectResponseHeader(this.server, StatusCode.FOUND, location);

		return true;
	}
	
	/**
	 * Locates a matching index file for the directory.
	 */
	private void findIndexFile()
	{
		//loop files
		for (String index : this.server.getConfiguration().getIndexFiles())
		{
			//create index file
			File indexFile = new File(this.resource, index);
			
			if (indexFile.exists())
			{
				//use index file
				this.resource = indexFile;
				
				break;
			}
		}
	}
	
	/**
	 * Verifies that the matching resource can actually be accessed.
	 * @throws HttpException if the resource cannot be accessed
	 */
	private void verifyResource() throws HttpException
	{
		//check exists
		if (this.resource == null || !this.resource.exists() || this.resource.isDirectory())
			throw new HttpException("File not found", StatusCode.NOT_FOUND);
		
		//check permissions
		if (!this.domain.isPathInDirectory(this.resource) || !this.resource.canRead() || this.resource.isHidden())
			throw new HttpException("Access denied", StatusCode.ACCESS_DENIED);
	}
	
	/**
	 * Prepares the matching resource by generating a correct content definition.
	 */
	private void prepareResource()
	{
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
	private String path;
	
	private File resource;
	private ResponseHeader responseHeader;
}
