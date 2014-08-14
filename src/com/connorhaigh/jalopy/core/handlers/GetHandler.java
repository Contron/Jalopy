package com.connorhaigh.jalopy.core.handlers;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import com.connorhaigh.jalopy.core.Handler;
import com.connorhaigh.jalopy.core.Server;
import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.http.Request;

public class GetHandler extends Handler
{
	/**
	 * Create a new GET request handler.
	 * @param server the owning server
	 * @param requestHeader the received request
	 * @param dataOutputStream the data output stream of the request
	 */
	public GetHandler(Server server, Request request, DataOutputStream dataOutputStream)
	{
		super(server, request, dataOutputStream);
	}
	
	/**
	 * Handle a GET request.
	 * @throws HttpException if a HTTP exception occurs
	 * @throws IOException if an IO exception occurs
	 */
	@Override
	public void handle() throws HttpException, IOException
	{
		try (FileInputStream fileInputStream = new FileInputStream(this.getRequest().getResource()))
		{
			//read
			int bytes = 0;
			byte[] buffer = new byte[4096];
			while ((bytes = fileInputStream.read(buffer)) != -1)
				this.getDataOutputStream().write(buffer, 0, bytes);
		}
	}
}
