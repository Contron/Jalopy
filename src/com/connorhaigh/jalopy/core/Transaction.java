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
	 * Creates a new transaction.
	 * @param server the owning server
	 * @param socket the client's socket
	 */
	public Transaction(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
	}
	
	/**
	 * Runs and handles the transaction between the server and the client.
	 */
	@Override
	public void run()
	{
		//streams
		BufferedReader bufferedReader = null;
		DataOutputStream dataOutputStream = null;
		
		try
		{
			//create wrapper streams
			bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
			
			//header and dispatcher
			RequestHeader requestHeader = null;
			Dispatcher dispatcher = null;
			
			try
			{
				//read headers
				requestHeader = new RequestHeader();
				requestHeader.gather(bufferedReader);
				requestHeader.sanitise();
				
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
				exception.printStackTrace();
				
				try
				{
					//generic error
					dataOutputStream.write(PageGenerator.generatePageCompletelyFor(this.server, "Server Error", exception.getMessage()).assemble().getBytes());
				}
				catch (IOException ioException)
				{
					
				}
				
				//log
				if (this.server.getConfiguration().getLogErrors())
					this.server.getExceptionLogger().logException(this.socket, requestHeader, exception);
			}
		}
		catch (Exception exception)
		{
			
		}
		finally
		{
			try
			{
				//close
				bufferedReader.close();
				dataOutputStream.close();
			}
			catch (IOException ioException)
			{
				
			}
		}
	}
	
	/**
	 * Handles the transaction between the server and the client.
	 */
	public void handle()
	{
		//create thread
		Thread thread = new Thread(this);
		thread.setName("Transaction Thread");
		thread.start();
	}
	
	private Server server;
	private Socket socket;
}
