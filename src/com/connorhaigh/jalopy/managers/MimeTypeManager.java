package com.connorhaigh.jalopy.managers;

import java.io.File;
import java.util.ArrayList;

import com.connorhaigh.jalopy.configuration.Manager;
import com.connorhaigh.jalopy.core.MimeType;
import com.connorhaigh.jalopy.resources.Directories;

public class MimeTypeManager extends Manager<ArrayList<MimeType>>
{
	/**
	 * Create a new domain manager.
	 * @param root the root directory
	 */
	public MimeTypeManager(File root)
	{
		super(root, Directories.CONFIGURATION, "mime_types");
	}
	
	/**
	 * Generate a default set of properties for this domain manager.
	 * @return the default properties
	 */
	public ArrayList<MimeType> generateDefault()
	{
		//result
		ArrayList<MimeType> result = new ArrayList<MimeType>();
		
		//text
		result.add(new MimeType("txt", "text", "plain"));
		result.add(new MimeType("log", "text", "plain"));
		result.add(new MimeType("cfg", "text", "plain"));
		result.add(new MimeType("xml", "text", "xml"));
		result.add(new MimeType("json", "text", "plain"));
		result.add(new MimeType("htm", "text", "htm"));
		result.add(new MimeType("html", "text", "html"));
		result.add(new MimeType("shtml", "text", "html"));
		result.add(new MimeType("css", "text", "css"));
		result.add(new MimeType("rtf", "text", "rtf"));
		result.add(new MimeType("rdf", "text", "rdf"));
		result.add(new MimeType("java", "text", "plain"));
		result.add(new MimeType("cs", "text", "plain"));
		result.add(new MimeType("php", "text", "php"));
		result.add(new MimeType("js", "text", "javascript"));
		result.add(new MimeType("asp", "text", "asp"));
		
		//applications
		result.add(new MimeType("zip", "application", "zip"));
		result.add(new MimeType("jar", "application", "java-archive"));
		result.add(new MimeType("exe", "application", "octet-stream"));
		result.add(new MimeType("pdf", "application", "pdf"));
		result.add(new MimeType("dtd", "application", "xml-dtd"));
		result.add(new MimeType("ps", "application", "postscript"));
		
		//images
		result.add(new MimeType("png", "image", "png"));
		result.add(new MimeType("jpg", "image", "jpeg"));
		result.add(new MimeType("jpeg", "image", "jpeg"));
		result.add(new MimeType("gif", "image", "gif"));
		result.add(new MimeType("svg", "image", "xml+svg"));
		result.add(new MimeType("tiff", "image", "tiff"));
		result.add(new MimeType("bmp", "image", "bmp"));
		
		//audio
		result.add(new MimeType("mp3", "audio", "mpeg"));
		result.add(new MimeType("mp4", "audio", "mp4"));
		result.add(new MimeType("ogg", "audio", "ogg"));
		result.add(new MimeType("wav", "audio", "wav"));
		result.add(new MimeType("mid", "audio", "midi"));
		result.add(new MimeType("midi", "audio", "midi"));
		result.add(new MimeType("mod", "audio", "mod"));
		
		//video
		result.add(new MimeType("avi", "video", "avi"));
		result.add(new MimeType("mpg", "video", "mpeg"));
		result.add(new MimeType("mp4", "video", "mp4"));
		result.add(new MimeType("webm", "video", "webm"));
		result.add(new MimeType("mov", "video", "quicktime"));
		result.add(new MimeType("3gp", "video", "3gp"));
		
		//non-standard types
		result.add(new MimeType("ico", "image", "x-icon"));
		result.add(new MimeType("gz", "application", "x-gzip"));
		result.add(new MimeType("gzip", "application", "x-gzip"));
		result.add(new MimeType("tar", "application", "x-tar"));
		result.add(new MimeType("7z", "application", "x-7z-compressed"));
		result.add(new MimeType("rar", "application", "x-rar-compressed"));
		result.add(new MimeType("swf", "application", "x-shockwave-flash"));
		
		return result;
	}
}
