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
		result.add(new MimeType("htm", "text", "htm"));
		result.add(new MimeType("html", "text", "html"));
		result.add(new MimeType("css", "text", "css"));
		result.add(new MimeType("rtf", "text", "rtf"));
		
		//applications
		result.add(new MimeType("js", "application", "javascript"));
		result.add(new MimeType("xml", "application", "xml"));
		result.add(new MimeType("zip", "application", "zip"));
		
		//images
		result.add(new MimeType("png", "image", "png"));
		result.add(new MimeType("jpg", "image", "jpeg"));
		result.add(new MimeType("jpeg", "image", "jpeg"));
		result.add(new MimeType("gif", "image", "gif"));
		result.add(new MimeType("svg", "image", "xml+svg"));
		
		//audio
		result.add(new MimeType("mp3", "audio", "mpeg"));
		result.add(new MimeType("mp4", "audio", "mp4"));
		result.add(new MimeType("ogg", "audio", "ogg"));
		result.add(new MimeType("wav", "audio", "vnd.wave"));
		
		//video
		result.add(new MimeType("avi", "video", "avi"));
		result.add(new MimeType("mpg", "video", "mpeg"));
		result.add(new MimeType("mp4", "video", "mp4"));
		result.add(new MimeType("webm", "video", "webm"));
		
		return result;
	}
}
