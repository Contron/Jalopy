package com.connorhaigh.jalopy.loggers;

import java.io.File;

import com.connorhaigh.jalopy.configuration.Logger;
import com.connorhaigh.jalopy.resources.Directories;

public class ErrorLogger extends Logger
{
	/**
	 * Create a new information logger.
	 * @param root the root directory
	 */
	public ErrorLogger(File root)
	{
		super(root, Directories.LOGS, "errors");
	}
}
