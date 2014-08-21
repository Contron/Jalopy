package com.connorhaigh.jalopy.core;

import java.io.DataOutputStream;
import java.lang.reflect.Constructor;

import com.connorhaigh.jalopy.exceptions.ServerException;
import com.connorhaigh.jalopy.http.Request;

public class Mapping 
{
	/**
	 * Creates a new mapping.
	 * @param name the name of the request type
	 * @param handler the handler responsible
	 * @throws SecurityException if the permissions are not available
	 * @throws NoSuchMethodException if the handler class does not have the respective methods
	 */
	public Mapping(String name, Class<? extends Handler> handler) throws NoSuchMethodException, SecurityException
	{
		this.name = name;
		
		this.handler = handler;
		this.constructor = this.handler.getDeclaredConstructor(Server.class, Request.class, DataOutputStream.class);
	}
	
	/**
	 * Creates the handler this mapping represents.
	 * @param server the owning server
	 * @param requestHeader the received request
	 * @param dataOutputStream the data output stream of the request
	 * @return the handler instance
	 * @throws ServerException if the handler could not be instantiated
	 */
	public Handler createHandler(Server server, Request request, DataOutputStream dataOutputStream) throws ServerException
	{
		try
		{
			return this.constructor.newInstance(server, request, dataOutputStream);
		}
		catch (Exception exception)
		{
			throw new ServerException("Could not create handler");
		}
	}
	
	/**
	 * Returns the request type of this mapping.
	 * @return the request type
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Returns the handler for this mapping.
	 * @return the handler
	 */
	public Class<? extends Handler> getHandler()
	{
		return this.handler;
	}
	
	private String name;
	
	private Class<? extends Handler> handler;
	private Constructor<? extends Handler> constructor;
}
