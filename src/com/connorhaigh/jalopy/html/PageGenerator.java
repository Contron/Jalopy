package com.connorhaigh.jalopy.html;

import com.connorhaigh.jalopy.core.MimeType;
import com.connorhaigh.jalopy.core.Server;
import com.connorhaigh.jalopy.exceptions.HttpException;
import com.connorhaigh.jalopy.http.Content;
import com.connorhaigh.jalopy.http.Http;
import com.connorhaigh.jalopy.http.StatusCode;
import com.connorhaigh.jalopy.http.responses.GenericResponseHeader;

public class PageGenerator 
{
	/**
	 * Generates a placeholder page as HTML text.
	 * @param server the owning server
	 * @param title the title of the message
	 * @param message the message to display
	 * @return the generated HTML text
	 */
	public static String generateHtml(Server server, String title, String message)
	{
		//result
		StringBuilder result = new StringBuilder();
		
		//page
		result.append("<!DOCTYPE html>" + Http.CARRIAGE_RETURN);
		result.append("<html>" + Http.CARRIAGE_RETURN);
		result.append("\t<head>" + Http.CARRIAGE_RETURN);
		result.append("\t\t<title>" + title + "</title>" + Http.CARRIAGE_RETURN);
		result.append("\t</head>" + Http.CARRIAGE_RETURN);
		result.append("\t<body>" + Http.CARRIAGE_RETURN);
		result.append("\t\t<h1>" + title + "</h1>" + Http.CARRIAGE_RETURN);
		result.append("\t\t<p>" + message + "</p>" + Http.CARRIAGE_RETURN);
		result.append("\t\t<hr />" + Http.CARRIAGE_RETURN);
		result.append("\t\t<p><i>" + server.getDetailedName() + "</i></p>" + Http.CARRIAGE_RETURN);
		result.append("\t</body>" + Http.CARRIAGE_RETURN);
		result.append("</html>");
		
		return result.toString();
	}
	
	/**
	 * Generates a HTML page for a HTTP exception.
	 * @param server the owning server
	 * @param httpException the HTTP exception
	 * @return the generated HTML text
	 */
	public static String generateHtmlFor(Server server, HttpException httpException)
	{
		return PageGenerator.generateHtml(server, httpException.getStatusCode().getRealCode(), httpException.getStatusCode().getDescription());
	}
	
	/**
	 * Generates a complete page (headers and HTML content) for a placeholder HTML page.
	 * @param server the owning server
	 * @param title the title of the message
	 * @param message the message to display
	 * @return the generated complete response
	 */
	public static Page generatePageCompletelyFor(Server server, String title, String message)
	{
		//generate html
		String html = PageGenerator.generateHtml(server, title, message);
		
		//generate page
		Content content = new Content(MimeType.HTML, html.length(), server.getClock().getNow());
		GenericResponseHeader genericResponseHeader = new GenericResponseHeader(server, StatusCode.OKAY, content);
		Page page = new Page(genericResponseHeader, html);
		
		return page;
	}
	
	/**
	 * Generates a complete page (headers and HTML content) for a HTTP exception.
	 * @param server the owning server
	 * @param httpException the HTTP exception
	 * @return the generated complete response
	 */
	public static Page generatePageCompletelyFor(Server server, HttpException httpException)
	{
		//generate html
		String html = PageGenerator.generateHtmlFor(server, httpException);
		
		//generate page
		Content content = new Content(MimeType.HTML, html.length(), server.getClock().getNow());
		GenericResponseHeader genericResponseHeader = new GenericResponseHeader(server, httpException.getStatusCode(), content);
		Page page = new Page(genericResponseHeader, html);
		
		return page;
	}
}
