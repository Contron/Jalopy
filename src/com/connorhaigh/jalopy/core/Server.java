package com.connorhaigh.jalopy.core;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.connorhaigh.jalopy.configuration.Configuration;
import com.connorhaigh.jalopy.core.handlers.GetHandler;
import com.connorhaigh.jalopy.core.handlers.HeadHandler;
import com.connorhaigh.jalopy.exceptions.ServerException;
import com.connorhaigh.jalopy.loggers.ErrorLogger;
import com.connorhaigh.jalopy.loggers.ExceptionLogger;
import com.connorhaigh.jalopy.loggers.RequestLogger;
import com.connorhaigh.jalopy.managers.ConfigurationManager;
import com.connorhaigh.jalopy.managers.DomainManager;
import com.connorhaigh.jalopy.managers.MimeTypeManager;
import com.connorhaigh.jalopy.resources.Requests;

public class Server implements Runnable
{
	/**
	 * Create a new server.
	 */
	public Server()
	{
		this.root = new File(System.getProperty("user.dir"));
		
		this.running = false;
		this.serverSocket = null;
		
		this.configuration = new Configuration();
		this.domains = new ArrayList<Domain>();
		this.mimeTypes = new ArrayList<MimeType>();
		this.mappings = new ArrayList<Mapping>();
		
		this.requestLogger = null;
		this.errorLogger = null;
		this.exceptionLogger = null;
	}
	
	/**
	 * Run and handle the main server process.
	 */
	@Override
	public void run()
	{
		while (this.running)
		{
			try
			{
				//listen
				Socket socket = this.serverSocket.accept();
				
				//handle
				Transaction transaction = new Transaction(this, socket);
				transaction.handle();
			}
			catch (IOException ioException)
			{
				ioException.printStackTrace();
			}
		}
	}
	
	/**
	 * Set up this server by loading all necessary files.
	 * Configuration will be read from the configuration file.
	 * Domains will be read from each of the files in the domain directory.
	 * MIME types will be read from the MIME types file.
	 * @throws IOException
	 */
	public void setUp() throws IOException
	{
		//create managers
		ConfigurationManager configurationManager = new ConfigurationManager(this.root);
		DomainManager domainManager = new DomainManager(this.root);
		MimeTypeManager mimeTypeManager = new MimeTypeManager(this.root);
		
		//create loggers
		this.requestLogger = new RequestLogger(this.root);
		this.errorLogger = new ErrorLogger(this.root);
		this.exceptionLogger = new ExceptionLogger(this.root);
		
		//load managers
		configurationManager.loadAll();
		domainManager.loadAll();
		mimeTypeManager.loadAll();
		
		//set up loggers
		this.requestLogger.setUp();
		this.errorLogger.setUp();
		this.exceptionLogger.setUp();
		
		//update
		this.configuration = configurationManager.getElement();
		this.domains = domainManager.getElement();
		this.mimeTypes = mimeTypeManager.getElement();
		
		//add mappings
		this.mappings.add(new Mapping(Requests.GET, GetHandler.class));
		this.mappings.add(new Mapping(Requests.HEAD, HeadHandler.class));
	}
	
	/**
	 * Start the server and begin to listen for connections.
	 * @throws ServerException if the server is not properly configured
	 * @throws IOException if the server could not be started
	 */
	public void start() throws ServerException, IOException
	{
		//check configuration
		if (this.configuration == null)
			throw new ServerException("Missing configuration");
		if (this.domains == null || this.domains.isEmpty())
			throw new ServerException("Missing or no domains");
		if (this.mimeTypes == null || this.mimeTypes.isEmpty())
			throw new ServerException("Missing or no MIME types");
		if (this.mappings == null || this.mappings.isEmpty())
			throw new ServerException("Missing or no request mappings");
		
		//create server
		this.running = true;
		this.serverSocket = new ServerSocket(this.configuration.getHostPort());
		
		//create thread
		Thread thread = new Thread(this);
		thread.setName("Server Thread");
		thread.start();
	}
	
	/**
	 * Stop the server and shut it down.
	 * @throws IOException if the server could not be stopped
	 */
	public void stop() throws IOException
	{
		//stop
		this.running = false;
		this.serverSocket.close();
		
		//close loggers
		this.requestLogger.shutDown();
		this.errorLogger.shutDown();
		this.exceptionLogger.shutDown();
	}
	
	/**
	 * Finds a domain for the specified host, or null.
	 * @param host the host
	 * @return the domain
	 */
	public Domain findDomainFor(String host)
	{
		return this.domains.stream()
			.filter(domain -> host.endsWith(domain.getHost()))
			.findFirst()
			.orElse(null);
	}
	
	/**
	 * Finds a MIME type for the specified file, or simply returns the default.
	 * @param file the file
	 * @return the MIME type, or the default
	 */
	public MimeType findMimeTypeFor(File file)
	{
		return this.mimeTypes.stream()
			.filter(mimeType -> file.getName().endsWith(mimeType.getExtension()))
			.findFirst()
			.orElse(MimeType.PLAIN);
	}
	
	/**
	 * Find a mapping for the specified request type, or null.
	 * @param type the type
	 * @return the mapping
	 */
	public Mapping findMappingFor(String method)
	{
		return this.mappings.stream()
			.filter(handlerMapper -> handlerMapper.getName().equals(method))
			.findFirst()
			.orElse(null);
	}
	
	/**
	 * Returns if this server is running.
	 * @return if this server is running
	 */
	public boolean isRunning()
	{
		return this.running;
	}
	
	/**
	 * Returns the detailed name of this server.
	 * @return the detailed name
	 */
	public String getDetailedName()
	{
		return String.format
		(
			"%s (Java %s (%s-bit), on %s (version %s))",
			this.configuration.getServerName(),
			System.getProperty("java.version"),
			System.getProperty("sun.arch.data.model"),
			System.getProperty("os.name"),
			System.getProperty("os.version")
		);
	}
	
	/**
	 * Returns the configuration for this server.
	 * @return the configuration
	 */
	public Configuration getConfiguration()
	{
		return this.configuration;
	}
	
	/**
	 * Returns the list of domains for this server.
	 * @return the list of domains
	 */
	public ArrayList<Domain> getDomains()
	{
		return this.domains;
	}
	
	/**
	 * Returns the list of MIME types for this server.
	 * @return the list of MIME types
	 */
	public ArrayList<MimeType> getMimeTypes()
	{
		return this.mimeTypes;
	}
	
	/**
	 * Returns the request logger for this server.
	 * @return the request logger
	 */
	public RequestLogger getRequestLogger()
	{
		return this.requestLogger;
	}
	
	/**
	 * Returns the error logger for this server.
	 * @return the error logger
	 */
	public ErrorLogger getErrorLogger()
	{
		return this.errorLogger;
	}
	
	/**
	 * Returns the exception logger for this server.
	 * @return the exception logger
	 */
	public ExceptionLogger getExceptionLogger()
	{
		return this.exceptionLogger;
	}
	
	private File root;
	
	private boolean running;
	private ServerSocket serverSocket;
	
	private Configuration configuration;
	private ArrayList<Domain> domains;
	private ArrayList<MimeType> mimeTypes;
	private ArrayList<Mapping> mappings;
	
	private RequestLogger requestLogger;
	private ErrorLogger errorLogger;
	private ExceptionLogger exceptionLogger;
}
