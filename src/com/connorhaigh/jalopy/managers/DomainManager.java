package com.connorhaigh.jalopy.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.connorhaigh.jalopy.configuration.Manager;
import com.connorhaigh.jalopy.core.Domain;
import com.connorhaigh.jalopy.resources.Directories;

public class DomainManager extends Manager<ArrayList<Domain>>
{
	/**
	 * Creates a new domain manager.
	 * @param root the root directory
	 */
	public DomainManager(File root)
	{
		super(root, Directories.CONFIGURATION, "domains");
	}
	
	/**
	 * Generates a default set of properties for this domain manager.
	 * @return the default properties
	 */
	public ArrayList<Domain> generateDefault()
	{
		return new ArrayList<Domain>(Arrays.asList(new Domain()));
	}
}
