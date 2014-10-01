package com.connorhaigh.jalopy.http;

public class StatusCode 
{
	/**
	 * Creates a new status code.
	 * @param code the code
	 * @param message the short message
	 * @param description the detailed description of the message
	 */
	public StatusCode(int code, String message, String description)
	{
		this.code = code;
		this.message = message;
		this.description = description;
	}
	
	/**
	 * Returns the actual status code represented.
	 * @return the actual status code
	 */
	public String getRealCode()
	{
		return (this.code + " " + this.message);
	}
	
	/**
	 * Returns the numeric code for this status code.
	 * @return the numeric code
	 */
	public int getCode()
	{
		return this.code;
	}
	
	/**
	 * Returns the short message for this status code.
	 * @return the short message
	 */
	public String getMessage()
	{
		return this.message;
	}
	
	/**
	 * Returns the detailed description for this status code.
	 * @return the detailed description
	 */
	public String getDescription()
	{
		return this.description;
	}
	
	public static final StatusCode OKAY = new StatusCode(200, "OK", "The server processed the request successfully.");
	
	public static final StatusCode MOVED_PERMANENTLY = new StatusCode(301, "Moved Permanently", "The requested resource has been moved to a different location.");
	public static final StatusCode FOUND = new StatusCode(302, "Found", "The requested resource has been found at a different location.");
	
	public static final StatusCode BAD_REQUEST = new StatusCode(400, "Bad Request", "The server received a malformed request and cannot complete it.");
	public static final StatusCode ACCESS_DENIED = new StatusCode(403, "Access Denied", "You do not have permission to access the requested resource.");
	public static final StatusCode NOT_FOUND = new StatusCode(404, "Not Found", "The requested resouce could not be found on this server.");
	
	public static final StatusCode INTERNAL_SERVER_ERROR = new StatusCode(500, "Internal Server Error", "The server encountered an unexpected error and cannot continue.");
	public static final StatusCode NOT_IMPLEMENTED = new StatusCode(501, "Not Implemented", "The requested feature is not implemented on the server.");
	public static final StatusCode SERVICE_UNAVAILABLE = new StatusCode(503, "Service Unavailable", "The server is currently unavailable to respond to any new requests.");
	
	private int code;
	private String message;
	private String description;
}
