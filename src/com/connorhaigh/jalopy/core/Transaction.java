package com.connorhaigh.jalopy.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.html.PageGenerator;
import com.connorhaigh.jalopy.http.RequestHeader;

public class Transaction implements Runnable
{
	/**
	 * Create a new transaction.
	 * @param server the owning server
	 * @param socket the client's socket
	 */
	public Transaction(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
	}
	
	/**
	 * Run and handle the transaction between the server and the client.
	 */
	@Override
	public void run()
	{
		try
		{
			//create wrapper streams
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
			
			//header and dispatcher
			RequestHeader requestHeader = null;
			Dispatcher dispatcher = null;
			
			try
			{
				//read headers
				requestHeader = new RequestHeader();
				requestHeader.gather(bufferedReader);
				
				//dispatch
				dispatcher = new Dispatcher(this.server, requestHeader, dataOutputStream);
				dispatcher.dispatch();
				
				//log
				if (this.server.getConfiguration().getLogRequests())
					this.server.getRequestLogger().logSuccess(this.socket, requestHeader, dispatcher);
			}
			catch (HttpException httpException)
			{
				try
				{
					//http error
					dataOutputStream.write(PageGenerator.generatePageCompletelyFor(this.server, httpException).assemble().getBytes());
				}
				catch (IOException ioException)
				{
					
				}
				
				//log
				if (this.server.getConfiguration().getLogErrors())
					this.server.getErrorLogger().logFailure(this.socket, requestHeader, httpException);
			}
			catch (Exception exception)
			{
				try
				{
					//generic error
					dataOutputStream.write(PageGenerator.generatePageCompletelyFor(this.server, "Unexpected Error", exception.getMessage()).assemble().getBytes());
				}
				catch (IOException ioException)
				{
					
				}
					
				//log
				if (this.server.getConfiguration().getLogErrors())
					this.server.getExceptionLogger().logException(this.socket, requestHeader, exception);
			}
			
			//close
			bufferedReader.close();
			dataOutputStream.close();
		}
		catch (Exception exception)
		{
			
		}
	}
	
	/**
	 * Handle the transaction between the server and the client.
	 * @throws IOException if the request could not be completed
	 */
	public void handle() throws IOException
	{
		//create thread
		Thread thread = new Thread(this);
		thread.setName("Transaction Thread");
		thread.start();
	}
	
	private Server server;
	private Socket socket;
}
