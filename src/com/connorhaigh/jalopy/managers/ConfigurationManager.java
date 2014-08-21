package com.connorhaigh.jalopy.managers;

import java.io.File;

import com.connorhaigh.jalopy.configuration.Configuration;
import com.connorhaigh.jalopy.configuration.Manager;
import com.connorhaigh.jalopy.resources.Directories;

public class ConfigurationManager extends Manager<Configuration>
{
	/**
	 * Creates a new domain manager.
	 * @param root the root directory
	 */
	public ConfigurationManager(File root)
	{
		super(root, Directories.CONFIGURATION, "configuration");
	}
	
	/**
	 * Generates a default set of properties for this domain manager.
	 * @return the default properties
	 */
	public Configuration generateDefault()
	{
		return new Configuration();
	}
}
