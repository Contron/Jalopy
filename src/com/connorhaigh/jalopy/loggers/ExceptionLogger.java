package com.connorhaigh.jalopy.loggers;

import java.io.File;

import com.connorhaigh.jalopy.configuration.Logger;
import com.connorhaigh.jalopy.resources.Directories;

public class ExceptionLogger extends Logger
{
	/**
	 * Creates a new information logger.
	 * @param root the root directory
	 */
	public ExceptionLogger(File root)
	{
		super(root, Directories.LOGS, "exceptions");
	}
}
