package com.connorhaigh.jalopy.core.handlers;

import java.io.DataOutputStream;
import java.io.IOException;

import com.connorhaigh.jalopy.core.Handler;
import com.connorhaigh.jalopy.core.Server;
import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.http.Request;

public class HeadHandler extends Handler
{
	/**
	 * Creates a new HEAD request handler.
	 * @param server the owning server
	 * @param requestHeader the received request
	 * @param dataOutputStream the data output stream of the request
	 */
	public HeadHandler(Server server, Request request, DataOutputStream dataOutputStream)
	{
		super(server, request, dataOutputStream);
	}
	
	/**
	 * Handles a HEAD request.
	 * @throws HttpException if a HTTP exception occurs
	 * @throws IOException if an IO exception occurs
	 */
	@Override
	public void handle() throws HttpException, IOException
	{
		
	}
}
