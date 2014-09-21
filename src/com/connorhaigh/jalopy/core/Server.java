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
	 * Creates a new server.
	 * @throws IOException if the server could not be created or initialised
	 * @throws SecurityException if the permissions are not available
	 * @throws NoSuchMethodException if any handler class does not have the respective methods
	 */
	public Server() throws IOException, NoSuchMethodException, SecurityException
	{
		this.root = new File(System.getProperty("user.dir"));
		
		this.running = false;
		this.serverSocket = null;
		
		this.configuration = new Configuration();
		this.clock = new Clock();
		
		this.domains = new ArrayList<Domain>();
		this.mimeTypes = new ArrayList<MimeType>();
		this.mappings = new ArrayList<Mapping>();
		
		this.requestLogger = null;
		this.errorLogger = null;
		this.exceptionLogger = null;
		
		//initialise
		this.createLoggers();
		this.createManagers();
		this.addMappings();
	}
	
	/**
	 * Runs and handles the main server process.
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
				
			}
		}
	}
	
	/**
	 * Starts the server and begin to listen for connections.
	 * @throws ServerException if the server is not properly configured
	 * @throws IOException if the server could not be started
	 */
	public void start() throws ServerException, IOException
	{
		//validate
		this.validateServer();
		
		//create server
		this.running = true;
		this.serverSocket = new ServerSocket(this.configuration.getHostPort());
		
		//create thread
		Thread thread = new Thread(this);
		thread.setName("Server Thread");
		thread.start();
	}
	
	/**
	 * Stops the server and shut it down.
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
			.filter(domain -> host.equals(domain.getHost()))
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
	 * Finds a mapping for the specified request type, or null.
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
	 * Creates the server loggers.
	 * @throws IOException if any files could not be created
	 */
	private void createLoggers() throws IOException
	{
		//create
		this.requestLogger = new RequestLogger(this.root);
		this.errorLogger = new ErrorLogger(this.root);
		this.exceptionLogger = new ExceptionLogger(this.root);
		
		//set up
		this.requestLogger.setUp();
		this.errorLogger.setUp();
		this.exceptionLogger.setUp();
	}
	
	/**
	 * Creates the configuration managers.
	 * @throws IOException if any files could not be read
	 */
	private void createManagers() throws IOException
	{
		//create
		ConfigurationManager configurationManager = new ConfigurationManager(this.root);
		DomainManager domainManager = new DomainManager(this.root);
		MimeTypeManager mimeTypeManager = new MimeTypeManager(this.root);
		
		//load
		configurationManager.loadAll();
		domainManager.loadAll();
		mimeTypeManager.loadAll();
		
		//update
		this.configuration = configurationManager.getElement();
		this.domains = domainManager.getElement();
		this.mimeTypes = mimeTypeManager.getElement();
	}
	
	/**
	 * Adds the default mappings for requests.
	 * @throws SecurityException if the permissions are not available
	 * @throws NoSuchMethodException if the handler class does not have the respective methods
	 */
	private void addMappings() throws NoSuchMethodException, SecurityException
	{
		//add
		this.mappings.add(new Mapping(Requests.GET, GetHandler.class));
		this.mappings.add(new Mapping(Requests.HEAD, HeadHandler.class));
	}
	
	/**
	 * Validates that the server is in a working state.
	 * @throws ServerException if the server is not in a working state
	 */
	private void validateServer() throws ServerException
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
		
		//validate domain directories exist
		for (Domain domain : this.domains)
			if (!domain.getDirectory().exists())
				throw new ServerException("Directory for domain '" + domain.getName() + "' does not exist at '" + domain.getDirectory() + "'");
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
			"Jalopy Web Server (Java %s (%s-bit), on %s (version %s))",
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
	 * Returns the clock for this server.
	 * @return the clock
	 */
	public Clock getClock()
	{
		return this.clock;
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
	private Clock clock;
	
	private ArrayList<Domain> domains;
	private ArrayList<MimeType> mimeTypes;
	private ArrayList<Mapping> mappings;
	
	private RequestLogger requestLogger;
	private ErrorLogger errorLogger;
	private ExceptionLogger exceptionLogger;
}
