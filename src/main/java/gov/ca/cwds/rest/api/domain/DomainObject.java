package gov.ca.cwds.rest.api.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DomainObject {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

	public DomainObject() {
	}
	
	public static String cookBoolean(Boolean uncookedoolean) {
		return Boolean.TRUE.equals(uncookedoolean) ? "Y" : "N";
	}
	
	public static Boolean uncookBooleanString(String cookedBoolean) {
		if( "N".equalsIgnoreCase(cookedBoolean) ) {
			return Boolean.FALSE;
		}
		if( "Y".equalsIgnoreCase(cookedBoolean) ) {
			return Boolean.TRUE;
		}
		return null;
		
	}
	
	public static String cookDate(Date date) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		return df.format(date);
	}
	
	public static String cookTimestamp(Date date) {
		DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
		return df.format(date);
	}
	
	public static Date uncookDateString(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		return df.parse(date);
	}

	public static Date uncookTimestampString(String timestamp) throws ParseException {
		DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
		return df.parse(timestamp);
	}
	
}
