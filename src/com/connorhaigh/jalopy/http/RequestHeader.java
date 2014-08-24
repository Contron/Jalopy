package com.connorhaigh.jalopy.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import com.connorhaigh.jalopy.exceptions.HttpException;

public class RequestHeader 
{
	/**
	 * Creates a new empty request header.
	 */
	public RequestHeader()
	{
		this.method = null;
		this.path = null;
		this.specification = null;
		
		this.host = null;
		this.userAgent = null;
		this.authorisation = null;
	}
	
	/**
	 * Gather this request header's contents from an input stream.
	 * @param bufferedReader the input stream to read
	 * @throws IOException if the stream could not be read
	 * throws HttpException if a HTTP related exception occurs
	 */
	public void gather(BufferedReader bufferedReader) throws IOException, HttpException
	{
		//token map
		HashMap<String, String> headers = new HashMap<String, String>();
		
		//get status line
		String statusLine = bufferedReader.readLine();
		if (statusLine == null)
			throw new HttpException("Missing status line", StatusCode.BAD_REQUEST);
		
		//split status line
		String[] statusLineParts = statusLine.split(" ");
		if (statusLineParts.length < 3)
			throw new HttpException("Invalid status line parameters", StatusCode.BAD_REQUEST);

		//get request, path and specification
		this.method = statusLineParts[0];
		this.path = statusLineParts[1];
		this.specification = statusLineParts[2];

		//read lines
		String line = null;
		while ((line = bufferedReader.readLine()) != null)
		{
			//skip if empty
			if (line.isEmpty())
				break;

			//split
			String[] parts = line.split(": ");
			
			if (parts.length < 2)
				throw new HttpException("Incomplete header parameters", StatusCode.BAD_REQUEST);
			
			//put key and value
			String key = parts[0];
			String value = parts[1];
			headers.put(key, value);
		}
		
		//update
		this.host = headers.getOrDefault("Host", "localhost");
		this.userAgent = headers.getOrDefault("User-Agent", "Unspecified");
		this.authorisation = headers.getOrDefault("Authorization", "None");
	}
	
	/**
	 * Sanitises the received data from the client.
	 */
	public void sanitise()
	{
		//clean method and host
		this.method = this.method.toUpperCase();
		this.host = this.host.toLowerCase();
	}
	
	/**
	 * Returns the request method in this request header.
	 * @return the request method
	 */
	public String getMethod()
	{
		return this.method;
	}
	
	/**
	 * Returns the requested path in this request header.
	 * @return the requested path
	 */
	public String getPath()
	{
		return this.path;
	}
	
	/**
	 * Returns the source specification in this request header.
	 * @return the source specification
	 */
	public String getSpecification()
	{
		return this.specification;
	}
	
	/**
	 * Returns the target host in this request header.
	 * @return the target host
	 */
	public String getHost()
	{
		return this.host;
	}
	
	/**
	 * Returns the user agent in this request header.
	 * @return the user agent
	 */
	public String getUserAgent()
	{
		return this.userAgent;
	}
	
	/**
	 * Returns the authorisation field in this request header.
	 * @return the authorisation field
	 */
	public String getAuthorisation()
	{
		return this.authorisation;
	}
	
	private String method;
	private String path;
	private String specification;
	
	private String host;
	private String userAgent;
	private String authorisation;
}
