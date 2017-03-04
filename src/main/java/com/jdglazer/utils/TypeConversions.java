package com.jdglazer.utils;

public abstract class TypeConversions {
	
	public static String byteArray2HexString( byte [] input ) {
		if( input != null ) {
			String str = "";
			for( byte b : input ) {
				str += String.format("%02x", b);
			}
			return str;
		}
		return null;
	}
}
