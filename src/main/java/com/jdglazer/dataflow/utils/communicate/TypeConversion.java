package com.jdglazer.utils.communicate;

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
}
