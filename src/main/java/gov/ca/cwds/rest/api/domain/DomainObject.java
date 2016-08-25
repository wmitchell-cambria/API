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
	
	public static String cookBoolean(Boolean uncookedBoolean) {
		if( uncookedBoolean != null ) {
			return Boolean.TRUE.equals(uncookedBoolean) ? "Y" : "N";	
		}
		return null;
		
	}
	
	public static Boolean uncookBooleanString(String cookedBoolean) {
		if( "N".equalsIgnoreCase(cookedBoolean) ) {
			return Boolean.FALSE;
		}
		if( "Y".equalsIgnoreCase(cookedBoolean) ) {
			return Boolean.TRUE;
		}
		throw new DomainException(new ParseException("Unable to generate boolean", 0));
		
	}
	
	public static String cookDate(Date date) {
		if( date != null ) {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			return df.format(date);
		}
		return null;
	}
	
	public static String cookTimestamp(Date date) {
		if(date != null) {
			DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
			return df.format(date);
		}
		return null;
	}
	
	public static Date uncookDateString(String date) {
		if( date != null ) {
			try {
				DateFormat df = new SimpleDateFormat(DATE_FORMAT);
				return df.parse(date);
			} catch (Exception e) {
				throw new DomainException(e);
			}
		}
		return null;
	}

	public static Date uncookTimestampString(String timestamp) {
	    try {
			DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
			return df.parse(timestamp);
		} catch (Exception e) {
			throw new DomainException(e);
		}
	}
	
}
