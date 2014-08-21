package com.connorhaigh.jalopy.core;

import java.io.File;

public class Domain 
{
	/**
	 * Creates a new domain.
	 * @param name the name of the domain
	 * @param directory the path to the domain's root
	 * @param host the host URL of this domain
	 */
	public Domain(String name, String host, File directory)
	{
		this.name = name;
		this.host = host;
		this.directory = directory;
	}
	
	/**
	 * Creates a new, empty domain.
	 */
	public Domain()
	{
		this("Untitled Domain", "127.0.0.1", new File(System.getProperty("user.home")));
	}
	
	/**
	 * Returns if a file's absolute path starts with this domain's directory.
	 * @param file the file
	 * @return if the file is in the directory
	 */
	public boolean isPathInDirectory(File file)
	{
		return file.getAbsolutePath().startsWith(this.directory.getAbsolutePath());
	}
	
	/**
	 * Resolves an absolute file path to a relative URL path on the host.
	 * @param file the file
	 * @return the relative path from this host
	 */
	public String resolvePathToRelative(File file)
	{
		return file.getAbsolutePath().substring(this.directory.getAbsolutePath().length()).replace("\\", "/");
	}
	
	/**
	 * Sets the name of this domain.
	 * @param name the domain
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Returns the name of this domain.
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Sets the directory of this domain.
	 * @param directory the directory
	 */
	public void setDirectory(File directory)
	{
		this.directory = directory;
	}
	
	/**
	 * Returns the directory of this domain.
	 * @return the directory
	 */
	public File getDirectory()
	{
		return this.directory;
	}
	
	/**
	 * Sets the host of this domain.
	 * @param host the domain
	 */
	public void setHost(String host)
	{
		this.host = host;
	}
	
	/**
	 * Returns the host of this domain.
	 * @return the host
	 */
	public String getHost()
	{
		return this.host;
	}
	
	private String name;
	private String host;
	private File directory;
}
