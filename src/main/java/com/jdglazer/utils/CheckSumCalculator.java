package com.jdglazer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;

/**
 * Container for all static checksum calculation methods
 * @author Glazer, Joshua D.
 *
 */
public abstract class CheckSumCalculator {
	
	public static String sha256( File file ) throws IOException {
		return file2Hash( file, "SHA-256");
	}
	
	public static String sha256( String input ) {
		return string2Hash( input, "SHA-256" );
	}
	/**
	 * Function that supports all file to hash conversion operations
	 * @param file The file handle
	 * @param hashType The string identifying the hash type. eg. "SHA-256"
	 * @return the hex encoded hash value
	 * @throws IOException Make sure you pass in a valid file
	 */
	private static String file2Hash( File file, String hashType) throws IOException {
		MessageDigest digester = null;
		byte [] hash = null;
		try {
			digester = MessageDigest.getInstance( hashType );
			byte [] fileContent = FileUtils.readFileToByteArray(file);
			hash = digester.digest(fileContent);
			return TypeConversions.byteArray2HexString(hash);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	/**
	 * The primary helper function for converting a string to a specified hash
	 * @param input String to get the hash of
	 * @param hashType The string identifier of the hashing algorithm. eg. sha-256
	 * @return The hex encoded hash
	 */
	private static String string2Hash( String input, String hashType) {
		MessageDigest digester = null;
		byte [] hash = null;
		try {
			digester = MessageDigest.getInstance( hashType );
			hash = digester.digest( input.getBytes() );
			return TypeConversions.byteArray2HexString(hash);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	public static void main( String [] args ) {
		try {
			String sha = sha256( new File("/home/jglazer/Documents/datasources/sources.xml") );
			String shaStr = sha256( "My Name is joshua" );
			System.out.println( sha + "\n" + shaStr );
		} catch (IOException e) {
			System.out.println("IOException thrown");
		}
	}
}
