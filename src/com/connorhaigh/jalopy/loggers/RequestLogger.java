package com.connorhaigh.jalopy.loggers;

import java.io.File;

import com.connorhaigh.jalopy.configuration.Logger;
import com.connorhaigh.jalopy.resources.Directories;

public class RequestLogger extends Logger
{
	/**
	 * Create a new information logger.
	 * @param root the root directory
	 */
	public RequestLogger(File root)
	{
		super(root, Directories.LOGS, "requests");
	}
}
