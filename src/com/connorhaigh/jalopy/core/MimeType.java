package com.connorhaigh.jalopy.core;

public class MimeType 
{
	/**
	 * Create a new MIME type.
	 * @param extension the extension this type is for
	 * @param file the file to represent
	 * @param type the MIME type
	 */
	public MimeType(String extension, String file, String type)
	{
		this.extension = extension;
		this.file = file;
		this.type = type;
	}
	
	/**
	 * Create a new, empty MIME type.
	 */
	public MimeType()
	{
		this("txt", "text", "plain");
	}
	
	/**
	 * Returns the actual MIME type represented by this object.
	 * @return the actual MIME type
	 */
	public String getRealType()
	{
		return (this.file + "/" + this.type);
	}
	
	/**
	 * Sets the extension of this MIME type.
	 * @param extension the extension
	 */
	public void setExtension(String extension)
	{
		this.extension = extension;
	}
	
	/**
	 * Returns the extension of this MIME type.
	 * @return the extension
	 */
	public String getExtension()
	{
		return this.extension;
	}
	
	/**
	 * Sets the file of this MIME type.
	 * @param file the file
	 */
	public void setFile(String file)
	{
		this.file = file;
	}
	
	/**
	 * Returns the file of this MIME type.
	 * @return the file
	 */
	public String getFile()
	{
		return this.file;
	}
	
	/**
	 * Sets the type of this MIME type.
	 * @param type the type
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	
	/**
	 * Returns the type of this MIME type.
	 * @return the type
	 */
	public String getType()
	{
		return this.type;
	}
	
	public static final MimeType PLAIN = new MimeType("txt", "text", "plain");
	public static final MimeType HTML = new MimeType("html", "text", "html");
	
	private String extension;
	private String file;
	private String type;
}
