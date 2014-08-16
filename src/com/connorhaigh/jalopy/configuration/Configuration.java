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
		this.indexFiles = new ArrayList<String>(Arrays.asList("index.html", "index.htm", "index.txt"));
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
	 * Returns the host port to use for this configuration.
	 * @return the host port
	 */
	public int getHostPort()
	{
		return this.hostPort;
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
