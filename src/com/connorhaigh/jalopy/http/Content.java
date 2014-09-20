package com.connorhaigh.jalopy.http;

import java.time.ZonedDateTime;

import com.connorhaigh.jalopy.core.MimeType;

public class Content 
{
	/**
	 * Creates a new content definition.
	 * @param mimeType the matching MIME type
	 * @param length the length of the content
	 * @param lastModified the last modified date of the content
	 */
	public Content(MimeType mimeType, long length, ZonedDateTime lastModified)
	{
		this.mimeType = mimeType;
		this.length = length;
		this.lastModified = lastModified;
	}
	
	/**
	 * Returns the MIME type of this content.
	 * @return the MIME type
	 */
	public MimeType getMimeType()
	{
		return this.mimeType;
	}
	
	/**
	 * Returns the length of this content.
	 * @return the length
	 */
	public long getLength()
	{
		return this.length;
	}
	
	/**
	 * Returns the last modified date/time of this content.
	 * @return the last modified date/time
	 */
	public ZonedDateTime getLastModified()
	{
		return this.lastModified;
	}
	
	private MimeType mimeType;
	private long length;
	private ZonedDateTime lastModified;
}
