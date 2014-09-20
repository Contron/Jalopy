package com.connorhaigh.jalopy.core;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Clock 
{
	/**
	 * Creates a new clock.
	 */
	public Clock()
	{
		this.zoneId = ZoneId.of("GMT");
	}
	
	/**
	 * Formats the specific date according to the RFC 1123 standard.
	 * @param offsetDateTime the offset date time
	 * @return the formatted result
	 */
	public String format(ZonedDateTime offsetDateTime)
	{
		return DateTimeFormatter.RFC_1123_DATE_TIME.format(offsetDateTime);
	}	
	
	/**
	 * Returns the zoned date/time for the current point in time.
	 * @return the zoned date/time
	 */
	public ZonedDateTime getNow()
	{
		return ZonedDateTime.now(this.zoneId);
	}
	
	/**
	 * Returns the zoned date/time for the specified point in time.
	 * @param time the point in time
	 * @return the zoned date/time
	 */
	public ZonedDateTime getZonedDateTime(long time)
	{
		Instant instant = Instant.ofEpochMilli(time);
		ZonedDateTime zonedDateTime = instant.atZone(this.zoneId);
		
		return zonedDateTime;
	}
	
	private ZoneId zoneId;
}
