package com.jdglazer.dataflow.utils.communicate;

import java.io.UnsupportedEncodingException;

public abstract class TypeConversion {

	public static String byteArrayToString( byte [] input ) {
		try {
			return new String(input, "US-ASCII" );
		} catch (UnsupportedEncodingException e) {
			//log failed conversion exception
		}
		return "";
	}
	
	public static int stringToInt( String str ) {
		int i = 0;
		try{
			i = Integer.parseInt( str );
		} catch ( Exception e ) {
		}
		return i;
	}
}
