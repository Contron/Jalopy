package com.connorhaigh.jalopy.html;

import com.connorhaigh.jalopy.core.interfaces.Assemblable;
import com.connorhaigh.jalopy.http.ResponseHeader;

public class Page implements Assemblable
{
	/**
	 * Creates a new page.
	 * @param responseHeader the complete response header
	 * @param content the complete HTML content
	 */
	public Page(ResponseHeader responseHeader, String content)
	{
		this.responseHeader = responseHeader;
		this.content = content;
	}
	
	/**
	 * Assembles this page into a HTML-compliant Web page.
	 * @return the assembled page
	 */
	@Override
	public String assemble()
	{
		return (this.responseHeader.assemble() + this.content);
	}
	
	private ResponseHeader responseHeader;
	private String content;
}
