package com.connorhaigh.jalopy.configuration;

import java.util.ArrayList;
import java.util.Arrays;

public class Configuration 
{
	/**
	 * Create a new default configuration.
	 */
	public Configuration()
	{
		this.serverName = "Jalopy Web Server";
		this.hostPort = 80;
		this.logRequests = true;
		this.logErrors = true;
		this.indexFiles = new ArrayList<String>(Arrays.<String>asList("index.html", "index.htm", "index.txt"));
	}
	
	/**
	 * Sets the server name to use for this configuration.
	 * @param serverName the server name
	 */
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}
	
	/**
	 * Returns the server name to use for this configuration.
	 * @return the server name
	 */
	public String getServerName()
	{
		return this.serverName;
	}
	
	/**
	 * Sets the host port to use for this configuration.
	 * @param hostPort the host port
	 */
	public void setHostPort(int hostPort)
	{
		this.hostPort = hostPort;
	}
	
	/**
	 * Returns the host port to use for this configuration.
	 * @return the host port
	 */
	public int getHostPort()
	{
		return this.hostPort;
	}
	
	/**
	 * Sets if requests should be logged for this configuration.
	 * @param logRequests if requests should be logged
	 */
	public void setLogRequests(boolean logRequests)
	{
		this.logRequests = logRequests;
	}
	
	/**
	 * Returns if requests should be logged for this configuration.
	 * @return if requests should be logged
	 */
	public boolean getLogRequests()
	{
		return this.logRequests;
	}
	
	/**
	 * Sets if errors should be logged for this configuration.
	 * @param logErrors if errors should be logged
	 */
	public void setLogErrors(boolean logErrors)
	{
		this.logErrors = logErrors;
	}
	
	/**
	 * Returns if errors should be logged for this configuration.
	 * @return if errors should be logged
	 */
	public boolean getLogErrors()
	{
		return this.logErrors;
	}
	
	/**
	 * Returns the list of index files to use for this configuration.
	 * @return the list of index files to use
	 */
	public ArrayList<String> getIndexFiles()
	{
		return this.indexFiles;
	}
	
	private String serverName;
	private int hostPort;
	private boolean logRequests;
	private boolean logErrors;
	private ArrayList<String> indexFiles;
}
