/**
 * Defines all constant values for indentifier names in the DataSource xml format
 */
package com.jdglazer.dataflow.collector;

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
	 * The name of the attribute indicating the hours in the week for which the data source should be active
	 * NOT REQUIRED
	 * DEFAULT: 0-167 (all hours)
	 */
	public static final String DATA_SOURCE_ACTIVE_INTERVAL_ATTR = "activeTime";
	
	/**
	 * The character that separates data source active time interval sets
	 */
	public static final String ACTIVE_INTERVAL_DELIMITER = ",";
	/**
	 * The character that separates the start and end time in a data source active interval
	 */
	public static final String ACTIVE_INTERVAL_START_END_DELIMETER = "-";
	
	public static final String ACTIVE_INTERVAL_DEFAULT = "0-167";
	
	/**
	 * This defined the name of the data source element attribute defining the frequency of reading of the datasource
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
	 * Name of the root element for a list of get variables in an http(s) request
	 */
	public static final String GET_LIST_ELEMENT_NAME = "getlist";
	
	/**
	 * Name the root element encasing a list of post variables for an http(s) request
	 */
	public static final String POST_LIST_ELEMENT_NAME = "postlist";
	
	/**
	 * Name of an element holding a key value pair in a list
	 */
	public static final String LIST_ELEMENT_ELEMENT_NAME = "element";
	
	/**
	 * The character chosen to separate a key from its associated value in a list
	 */
	public static final String LIST_KEY_VALUE_DELIMITER = ":";
	
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
	public static final String PARSER_LANG_ATTR_NAME = "lang";
	
	public static final String JAVA_PARSER_CLASS_ELEMENT_NAME = "class";
	
	public static final String PARSER_SCRIPT_ELEMENT_NAME = "script";
	
	public static final String PARSER_SCRIPT_PATH_ELEMENT_NAME = "path";
	
	public static final String PARSER_SCRIPT_OUTPUT_PATH_ELEMENT_NAME = "datapath";
	
	public static final String PARSER_REGEX_ELEMENT_NAME = "regex";
	
	//CRAWLER NAMES
	
	public static final String CRAWLER_CONFIGURATION_ELEMENT = "crawler";
	
	public static final String CRAWLER_MAX_DEPTH_ATTR = "maxdepth";
	
	public static final String CRAWLER_MAX_PAGES_ATTR = "maxpagecount";
	
	public static final String CRAWLER_MAX_PAGE_SIZE_ATTR = "maxpagesize";
	
	public static final String CRAWLER_URL_REGEX_ELEMENT = "urlregex";
	/**
	 * The minimum length between updates from a data source ( in milliseconds)
	 */
	public static final int MINIMUM_ALLOWED_UPDATE_INTERVAL = 1;
	
	/**
	 * The interval between updates assigned by default to a datasource
	 */
	public static final int DEFAULT_UPDATE_INTERVAL = 3600;

}
