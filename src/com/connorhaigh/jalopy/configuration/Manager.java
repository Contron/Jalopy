package com.connorhaigh.jalopy.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.connorhaigh.jalopy.core.Domain;
import com.connorhaigh.jalopy.core.MimeType;
import com.thoughtworks.xstream.XStream;

public abstract class Manager<Element>
{
	/**
	 * Create a new manager.
	 * @param root the root directory
	 * @param directory the sub directory
	 * @param file the file
	 */
	public Manager(File root, String directory, String file)
	{
		this.root = root;
		this.directory = new File(this.root, directory);
		this.file = new File(this.directory, file + ".xml");
		
		this.element = null;
	}
	
	/**
	 * Load this manager's properties from file.
	 * @throws IOException if the file could not be read or created
	 */
	public void loadAll() throws IOException
	{
		//xstream
		XStream xstream = new XStream();
		xstream.alias("configuration", Configuration.class);
		xstream.alias("domain", Domain.class);
		xstream.alias("mimeType", MimeType.class);
		
		try
		{
			this.element = this.loadFromFile(xstream);
		}
		catch (IOException ioException)
		{
			this.element = this.generateDefault();
		}
		
		if (!this.file.exists())
		{
			//create directory
			this.root.mkdir();
			this.directory.mkdir();
			
			try (FileOutputStream fileOutputStream = new FileOutputStream(this.file))
			{
				xstream.toXML(this.element, fileOutputStream);
			}
			catch (IOException ioException)
			{
				throw ioException;
			}
		}
	}
	
	/**
	 * Load this manager's properties from file.
	 * @param xstream the XStream instance
	 * @return the loaded properties
	 * @throws IOException if the file could not be read
	 */
	@SuppressWarnings("unchecked")
	public Element loadFromFile(XStream xstream) throws IOException
	{
		try (FileInputStream fileInputStream = new FileInputStream(this.file))
		{
			return (Element) xstream.fromXML(fileInputStream);
		}
	}
	
	/**
	 * Generate a default set of properties for this manager.
	 * @return the default properties
	 */
	public abstract Element generateDefault();
	
	/**
	 * Returns the loaded (or default) element for this manager.
	 * @return the loaded (or default) element
	 */
	public Element getElement()
	{
		return this.element;
	}
	
	private File root;
	private File directory;
	private File file;
	
	private Element element;
}
