package com.connorhaigh.jalopy.core;

import java.io.DataOutputStream;
import java.io.IOException;

import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.http.Request;

public abstract class Handler 
{
	/**
	 * Creates a new request handler.
	 * @param server the owning server
	 * @param requestHeader the received request
	 * @param dataOutputStream the data output stream of the request
	 */
	public Handler(Server server, Request request, DataOutputStream dataOutputStream)
	{
		this.server = server;
		
		this.request = request;
		this.dataOutputStream = dataOutputStream;
	}
	
	/**
	 * Handles a request.
	 * @throws HttpException if a HTTP exception occurs
	 * @throws IOException if an IO exception occurs
	 */
	public abstract void handle() throws HttpException, IOException;
	
	/**
	 * Returns the server for this handler.
	 * @return the server
	 */
	public Server getServer()
	{
		return this.server;
	}
	
	/**
	 * Returns the received request for this handler.
	 * @return the received request
	 */
	public Request getRequest()
	{
		return this.request;
	}
	
	/**
	 * Returns the data output stream to the client for this request.
	 * @return the data output stream
	 */
	public DataOutputStream getDataOutputStream()
	{
		return this.dataOutputStream;
	}
	
	private Server server;
	
	private Request request;
	private DataOutputStream dataOutputStream;
}
