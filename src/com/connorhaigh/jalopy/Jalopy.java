package com.connorhaigh.jalopy;

import com.connorhaigh.jalopy.core.Server;

public class Jalopy 
{
	/**
	 * Main method.
	 * @param args application arguments
	 */
	public static void main(String[] args)
	{
		//header
		System.out.println("Jalopy");
		System.out.println("(C) Connor Haigh 2014");
		System.out.println();
		
		try
		{
			//create
			System.out.println("Creating server...");
			Server server = new Server();
			
			//start
			System.out.println("Starting server...");
			server.start();
			System.out.println("Server started sucessfully");
		}
		catch (Exception exception)
		{
			//error
			System.err.println();
			System.err.println("Could not create and start server: " + exception.getMessage());
		}
	}
}
