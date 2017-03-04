package com.jdglazer.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.omg.CosNaming.IstringHelper;

public abstract class TimeProvider {
	
	public static String getFormattedDateTimeUTC() {
		ZonedDateTime zdt = ZonedDateTime.now( ZoneOffset.UTC );
		return zdt.format( DateTimeFormatter.ofPattern("yyyy-MM-dd-hh:mm:ss") );		
	}
}
