package com.connorhaigh.jalopy.resources;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatters 
{
	public static final DateTimeFormatter HTTP_TIME = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss");
	public static final DateTimeFormatter LOGGER = DateTimeFormatter.ofPattern("EEEE dd MMMM HH:mm:ss");
}
