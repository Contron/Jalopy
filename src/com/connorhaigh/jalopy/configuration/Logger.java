package com.connorhaigh.jalopy.configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

import com.connorhaigh.jalopy.core.Dispatcher;
import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.http.RequestHeader;
import com.connorhaigh.jalopy.resources.DateTimeFormatters;

public class Logger 
{
	/**
	 * Create a new logger.
	 * @param root the root directory
	 * @param directory the sub directory
	 * @param file the log file
	 */
	public Logger(File root, String directory, String file)
	{
		this.root = root;
		this.directory = new File(this.root, directory);
		this.file = new File(this.directory, file + ".log");
		
		this.bufferedWriter = null;
	}
	
	/**
	 * Set up this logger.
	 * @throws IOException if the log file could not be created and hooked
	 */
	public void setUp() throws IOException
	{
		if (!this.file.exists())
		{
			//create directory
			this.root.mkdir();
			this.directory.mkdir();
		}
		
		this.bufferedWriter = new BufferedWriter(new FileWriter(this.file, true));
	}
	
	/**
	 * Shut down this logger.
	 * @throws IOException if the log file could not be closed
	 */
	public void shutDown() throws IOException
	{
		this.bufferedWriter.close();
	}
	
	/**
	 * Log a detailed request.
	 * @param address the source address
	 * @param method the method
	 * @param path the path to the resource
	 * @param specification the specification of the request
	 * @param responseCode the response code
	 * @param userAgent the user agent
	 */
	private void logDetailed(String address, String method, String path, String specification, String responseCode, String userAgent)
	{
		this.log(String.format
		(
			"%s %s %s %s > %s (%s)",
			address,
			method,
			path,
			specification,
			responseCode,
			userAgent
		));
	}
	
	/**
	 * Log a successful request.
	 * @param socket the client's socket
	 * @param requestHeader the request headers
	 * @param dispatcher the dispatcher
	 */
	public void logSuccess(Socket socket, RequestHeader requestHeader, Dispatcher dispatcher)
	{
		this.logDetailed
		(
			socket.getInetAddress().getHostAddress(),
			requestHeader.getMethod(),
			requestHeader.getPath(),
			requestHeader.getSpecification(),
			dispatcher.getResponseHeader().getStatusCode().getRealCode(),
			requestHeader.getUserAgent()
		);
	}
	
	/**
	 * Log a failed request.
	 * @param socket the client's socket
	 * @param requestHeader the request headers
	 * @param httpException the exception
	 */
	public void logFailure(Socket socket, RequestHeader requestHeader, HttpException httpException)
	{
		this.logDetailed
		(
			socket.getInetAddress().getHostAddress(),
			requestHeader.getMethod(),
			requestHeader.getPath(),
			requestHeader.getSpecification(),
			httpException.getStatusCode().getRealCode(),
			requestHeader.getUserAgent()
		);
	}
	
	/**
	 * Log an exception
	 * @param socket the client's socket
	 * @param requestHeader the request headers
	 * @param exception the exception
	 */
	public void logException(Socket socket, RequestHeader requestHeader, Exception exception)
	{
		this.logDetailed
		(
			socket.getInetAddress().getHostAddress(),
			requestHeader.getMethod(),
			requestHeader.getPath(),
			requestHeader.getSpecification(),
			exception.getClass().getSimpleName(),
			exception.getMessage()
		);
	}
	
	/**
	 * Log a message to file.
	 * @param message the message to write
	 */
	public void log(String message)
	{
		//skip if no writer
		if (this.bufferedWriter == null)
			return;
		
		try
		{
			//write message
			String logMessage = String.format("[%s] %s", DateTimeFormatters.LOGGER.format(LocalDateTime.now()), message);
			this.bufferedWriter.write(logMessage + System.lineSeparator());
			this.bufferedWriter.flush();
		}
		catch (IOException ioException)
		{
			
		}
	}
	
	private File root;
	private File directory;
	private File file;
	
	private BufferedWriter bufferedWriter;
}
