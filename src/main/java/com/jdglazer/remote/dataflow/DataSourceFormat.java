/**
 * Defines all constant values for indentifier names in the DataSource xml format
 */
package com.jdglazer.remote.dataflow;

public class DataSourceFormat {
	
	/**
	 * The root element tag name for a defined data source in the xml file
	 * REQUIRED
	 */
	public static final String ROOT_ELEMENT_NAME = "datasource";
	
	/**
	 * The name of the name attribute for the data source. This is the primary identifier for the datasource used through out the code.
	 * REQUIRED
	 */
	public static final String DATA_SOURCE_NAME_ATTR = "name";
	
	/**
	 * This defined the name of the datasource element attribute defining the frequency of reading of the datasource
	 * REQUIRED
	 */
	public static final String DATA_SOURCE_UPDATE_INTERVAL_ATTR = "updateInterval";
	
	/**
	 * This is the tag name for the element that provides all information necessary to find the data source
	 * REQUIRED
	 */
	public static final String CREDENTIAL_PROVIDER_NAME = "access";
	
	/**
	 * The attribute name for the method of communication with the datasource.
	 * OPTIONAL
	 * DEFAULT: http
	 */
	public static final String ACCESS_PROTOCOL_ATTR = "protocol";
	/**
	 * The name of the element containing the web address of the datasource
	 * OPTIONAL
	 */
	public static final String WEB_ADDRESS_ELEMENT_NAME = "address";
	/**
	 * The name of the element containing the username needed for ssh or to verify user over https
	 * OPTIONAL
	 */
	public static final String USER_ELEMENT_NAME = "user";
	/**
	 * The name of the element containing ssh or web password needed
	 * OPTIONAL
	 */
	public static final String PASSWORD_ELEMENT_NAME = "password";
	/**
	 * The name of the element containing the ip address for ssh or socket connections
	 * OPTIONAL
	 */
	public static final String IP_ELEMENT_NAME ="ip";
	/**
	 * The name of the element containing the port needed for socket connections
	 * OPTIONAL
	 */
	public static final String PORT_ELEMENT_NAME = "port";
	/**
	 * The name of the element containing the encryption key for socket connections
	 * OPTIONAL
	 */
	public static final String KEY_ELEMENT_NAME = "key";
	/**
	 * The name of the element that provides a class to parse out raw data from the input source
	 * OPTIONAL
	 * DEFAULT: A default parser will be provided by the program
	 */			;
	public static final String PARSER_CLASS_ELEMENT_NAME = "parser";
	
	/**
	 * The name of the parser element attribute that defines the java user defined java parser class
	 * OPTIONAL
	 * DEFAULT: A default parser class will be provided by the program
	 */
	public static final String PARSER_CLASS_ATTR_NAME = "class";
	
	/**
	 * The minimum length between updates from a data source ( in milliseconds)
	 */
	public static final int MINIMUM_ALLOWED_UPDATE_INTERVAL = 1000;
	public static final int DEFAULT_UPDATE_INTERVAL = 3600000;

}