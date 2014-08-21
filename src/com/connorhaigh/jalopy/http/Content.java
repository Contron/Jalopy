package com.connorhaigh.jalopy.http;

import com.connorhaigh.jalopy.core.MimeType;

public class Content 
{
	/**
	 * Creates a new content definition.
	 * @param mimeType the matching MIME type
	 * @param length the length of the content
	 */
	public Content(MimeType mimeType, long length)
	{
		this.mimeType = mimeType;
		this.length = length;
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
	
	private MimeType mimeType;
	private long length;
}
