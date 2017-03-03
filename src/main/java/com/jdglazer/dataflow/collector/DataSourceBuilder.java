/**
 * A class to convert an xml data source definition into a DataSource object
 * @author Glazer, Joshua D.
 */
package com.jdglazer.dataflow.collector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.jdglazer.dataflow.collector.access.AccessCredentials;
import com.jdglazer.dataflow.collector.access.HTTPAccess;
import com.jdglazer.dataflow.collector.access.HTTPSAccess;
import com.jdglazer.dataflow.collector.access.SSHAccess;
import com.jdglazer.dataflow.collector.access.SocketAccess;
import com.jdglazer.dataflow.collector.crawler.Crawler;
import com.jdglazer.dataflow.collector.parser.models.BashParserModel;
import com.jdglazer.dataflow.collector.parser.models.JavaParserModel;
import com.jdglazer.dataflow.collector.parser.models.ParserModelBase;
import com.jdglazer.dataflow.collector.parser.models.ParserModelBase.Language;
import com.jdglazer.dataflow.collector.parser.models.RegexModel;
import com.jdglazer.dataflow.collector.parser.models.RegexParserModel;
import com.jdglazer.dataflow.utils.communicate.TypeConversion;
import com.jdglazer.utils.xml.XMLParser;
import com.jdglazer.utils.xml.XMLParserTools;

public class DataSourceBuilder extends XMLParser {
	
	Logger logger = LoggerFactory.getLogger( DataSourceBuilder.class );
	
	/**
	 * stores the datasource xml file
	 */
	private File datasource;
	
	/**
	 * The DataSource map stored with data source name
	 */
	private Map<String, DataSource> parsedOutDataSources;	
	
	
	/**
	 * Takes the datasource xml file
	 * @param datasource Datasource xml
	 * @throws ParserConfigurationException if basic xml document builder can't be built
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public DataSourceBuilder( File datasource ) throws ParserConfigurationException, SAXException, IOException {
		super();
		parseFile( datasource );
		this.datasource = datasource;
		parsedOutDataSources=new HashMap<String, DataSource>();
	}
	
	/**
	 * Parses datasource xml file and builds a DataSource object from it
	 * @return DataSource map of data sources based on information in data source file
	 */
	public Map<String, DataSource> build() {
		if( getData() ) {
			System.out.println("DataSource build of "+datasource+" succeeded!");
		};
		return parsedOutDataSources;
	}
	
	/**
	 * Pull data from the root element of the data source xml
	 * @return true if the root data source element was found with a non-null name attribute value and an update interval value
	 */
	private boolean getData() {
		
		List<Node> list = this.getTagsByName(DataSourceFormat.ROOT_ELEMENT_NAME);
		boolean foundDataSources = false;
		
		if( list.size() > 0 ) {
			for( Node root : list ) {
				NamedNodeMap nm = root.getAttributes();
				if( nm != null ) {
					Node name = nm.getNamedItem(DataSourceFormat.DATA_SOURCE_NAME_ATTR),
						 updateInterval = nm.getNamedItem(DataSourceFormat.DATA_SOURCE_UPDATE_INTERVAL_ATTR),
						 activeTime = nm.getNamedItem( DataSourceFormat.DATA_SOURCE_ACTIVE_INTERVAL_ATTR );
					if( name != null && updateInterval != null ) {
						
						String datasource_name = name.getTextContent().trim();
						DataSource dataSource = new DataSource();
						int datasource_ui = 0;
						try {
							datasource_ui = Integer.parseInt( updateInterval.getTextContent() );
						}
						catch ( Exception e ) {
							logger.debug("Invalid string found for updateInterval in "+datasource);
						}
						
						if( datasource_name.length() > 0 )
							dataSource.setName( datasource_name );
						else {
							logger.debug("Null data source name is not allowed. File: "+datasource );
							dataSource = null;
							continue;
						}
						
						dataSource.setUpdateInterval(
						  datasource_ui >= DataSourceFormat.MINIMUM_ALLOWED_UPDATE_INTERVAL
							? datasource_ui
							: DataSourceFormat.DEFAULT_UPDATE_INTERVAL
						);
						
						AccessCredentials accessCredentials = getAccessCredentials( root );
						if( accessCredentials == null ) {
							logger.debug( "Invalid access credentials detected for data source of name: "+name+" in file: "+datasource);
							continue;
						}
						
						ParserModelBase parser = getParser( root );
						if( parser == null ) {
							logger.debug( "Invalid parser detected for data source of name "+name+" in file: "+datasource);
							continue;
						}
						
						//set the parser 
						ArrayList<short[]> ints = DataSourceAttrValueParser
									.parserUpdateAliveInterval( activeTime != null 
																	? activeTime.getTextContent() 
																	: DataSourceFormat.ACTIVE_INTERVAL_DEFAULT 
															  );
						
						dataSource.setAliveIntervals( ints );
						dataSource.setDatasourceParser( parser );
						dataSource.setAccess( accessCredentials );
						parsedOutDataSources.put( datasource_name, dataSource );
						foundDataSources = true;
					}
					else {
						logger.debug("Must set both name and update interval in root datesource tag. File: "+datasource);
						continue;
					}
				}
				else {
					logger.debug("Data source defined at "+datasource+" has no attributes.");
					continue;
				}
			}
		} else {
			logger.debug("No data source root element found in "+datasource);
			return false;
		}
		return foundDataSources;
	}
	
	/**
	 * 
	 */
	private AccessCredentials getAccessCredentials( Node dataSource ) {
		AccessCredentials accessCredentials = null;
		
		List<Node> nl = XMLParserTools.getTagsByName( dataSource,  DataSourceFormat.CREDENTIAL_PROVIDER_NAME );
		Node access = nl.size() > 0 ? nl.get(0) : null;
		
		if( access != null ) {
			NamedNodeMap nm = access.getAttributes();
			if( nm != null ) {
				Node protocolAttr = nm.getNamedItem( DataSourceFormat.ACCESS_PROTOCOL_ATTR );
				if( protocolAttr != null ) {
					String protocolStr = protocolAttr.getTextContent().trim().toLowerCase();	
					try {
						DataSource.Protocol protocol = DataSource.Protocol.valueOf( protocolStr );
						switch( protocol ) {
						case http:
							accessCredentials = new HTTPAccess();
							populateHttpAccess( access, (HTTPAccess) accessCredentials );
							break;
						case https:
							accessCredentials = new HTTPSAccess();
							populateHttpAccess( access, (HTTPSAccess) accessCredentials );
							break;
						case ssh:
							accessCredentials = new SSHAccess();
							populateSshAccess( access, (SSHAccess) accessCredentials );
							break;
						case socket:
							accessCredentials = new SocketAccess();
							populateSocketAccess( access, (SocketAccess) accessCredentials );
							break;
						}
					} catch ( Exception e ) {
						logger.debug( "Invalid protocol provided to data source access in file: "+datasource );
					}
				} else {
					logger.debug( "No protocol registered with data source access credentials in file "+datasource);
					return null;
				}
			}

		} else {
			logger.debug("No access credentials found for datasource in file "+datasource);
			return null;
		}
		
		return accessCredentials;		
	}
	
	private ParserModelBase getParser( Node datasource ) {
		ParserModelBase parserModel = null;
		List<Node> nl = XMLParserTools.getTagsByName( datasource, DataSourceFormat.PARSER_CLASS_ELEMENT_NAME );
		if( nl.size() > 0 ) {
			Node parserNode = nl.get(0);
			NamedNodeMap nm = parserNode.getAttributes();
			Node attr = nm.getNamedItem( DataSourceFormat.PARSER_LANG_ATTR_NAME );
			List<Node> dataOutputPath = XMLParserTools.getTagsByName(parserNode, DataSourceFormat.PARSER_SCRIPT_OUTPUT_PATH_ELEMENT_NAME);
			if( attr != null ) {
				String parserLanguage = attr.getTextContent().trim().toLowerCase();
				try {
					Language language = Language.valueOf(parserLanguage);
					switch( language ) {
					case java:
						parserModel = new JavaParserModel();
						parserModel.setLanguage(language);
						populateJavaParserModel(parserNode, (JavaParserModel) parserModel);
						break;
					case bash:
						parserModel = new BashParserModel();
						parserModel.setLanguage(language);
						populateBashParserModel( parserNode, (BashParserModel) parserModel );
						break;
					default:
						parserModel = new RegexParserModel();
						parserModel.setLanguage(language);
						populateRegexParserModel( parserNode, (RegexParserModel) parserModel );
						break;
					}
					
					String outPath = null;
					if( dataOutputPath.size() > 0) {
						outPath = dataOutputPath.get(0).getTextContent().trim();
					}
					parserModel.setOutputFileDirectory( outPath );
					
				} catch( Exception e ) {
					logger.error("Invalid language set for parser");
				}
			} else {
				logger.error( "No language set for parser in "+datasource );
			}
		} else {
			logger.error( "No parsers found for datasource in "+datasource );
		}
		return parserModel;
	}
	
	private boolean populateJavaParserModel( Node parser, JavaParserModel jpm ) {
		List<Node> list = XMLParserTools.getTagsByName( parser, DataSourceFormat.JAVA_PARSER_CLASS_ELEMENT_NAME );
		if( list.size() > 0 ) {
			Node classTag = list.get(0);
			jpm.setParserFilePath( classTag.getTextContent().trim() ); 
		} else {
			logger.error("No class found for java parser");
			return false;
		}
		
		return true;
	}
	
	private boolean populateBashParserModel( Node parser, BashParserModel bpm ) {
		List<Node> list = XMLParserTools.getTagsByName( parser, DataSourceFormat.PARSER_SCRIPT_ELEMENT_NAME);
		Node script = null;
		String path = null, datapath = null;
		if( list.size() > 0 ) {
			script = list.get(0);
			list = XMLParserTools.getTagsByName( script, DataSourceFormat.PARSER_SCRIPT_PATH_ELEMENT_NAME );
			path = list.size() > 0 ? list.get(0).getTextContent().trim() : null;
			bpm.setParserFilePath( path );
			
		} else {
			logger.error("No script tag found for bash parser");
		}
		
		return ( script != null && path != null && datapath != null );
	}
	
	private boolean populateRegexParserModel( Node parseMe, RegexParserModel rpm) {
		List<Node> list = XMLParserTools.getTagsByName( parseMe, DataSourceFormat.PARSER_REGEX_ELEMENT_NAME );
		List<RegexModel> regexList = new ArrayList<RegexModel>();
		for( Node regex : list ) {
			RegexModel regexModel = new RegexModel();
			
			String name = XMLParserTools.getNodeAttrValue( regex, DataSourceFormat.PARSER_REGEX_ELEMENT_NAME ),
				   message = XMLParserTools.getNodeAttrValue( regex, DataSourceFormat.PARSER_REGEX_MESSAGE_ATTR ),
				   value = XMLParserTools.getNodeAttrValue( regex, DataSourceFormat.PARSER_REGEX_VALUE_ATTR );
			
			if( name == null || value == null ) {
				return false;
			}
			regexModel.setName( name );
			regexModel.setMessage( message );
			regexModel.setRegex( value );
			regexList.add( regexModel );
		}
		return true;
	}
	
	private boolean populateHttpAccess( Node accessNode, HTTPAccess obj ) {
		String address = null;
		Map<String, String> getList = null, postList = null;
		if ( accessNode != null ) {
			List<Node> list = XMLParserTools.getTagsByName( accessNode, DataSourceFormat.WEB_ADDRESS_ELEMENT_NAME );
			address = list.size() > 0 ? list.get(0).getTextContent().trim() : null;
			list = XMLParserTools.getTagsByName( accessNode, DataSourceFormat.GET_LIST_ELEMENT_NAME );
			getList = list.size() > 0 ? parseVarList( list.get(0) ) : null;
			list = XMLParserTools.getTagsByName( accessNode, DataSourceFormat.POST_LIST_ELEMENT_NAME );
			postList = list.size() > 0 ? parseVarList( list.get(0) ) : null;
		} else {
			logger.error( "Null datasource xml http(s) access node cannot be parsed");
		}
		
		if( obj != null) {
			obj.setAddress(address);
			obj.setGetVars(getList);
			obj.setPostVars(postList);
			obj.setCrawler( parseCrawler( accessNode ) );
		}
		else {
			logger.error( "Values cannot be set for null HTTPAccess object" );
		}
		
		return (address != null);
	}
	
	private boolean populateSshAccess( Node accessNode, SSHAccess access ) {
		String user = null, ip = null, password = null;
		if( accessNode != null ) {
			//true when elements are found
			List<Node> list = XMLParserTools.getTagsByName( accessNode, DataSourceFormat.IP_ELEMENT_NAME );
			ip = list.size() > 0 ? list.get(0).getTextContent().trim() : null;
			list = XMLParserTools.getTagsByName( accessNode, DataSourceFormat.PASSWORD_ELEMENT_NAME );
			password = list.size() > 0 ? list.get(0).getTextContent().trim() : null;
			list = XMLParserTools.getTagsByName( accessNode, DataSourceFormat.USER_ELEMENT_NAME );
			user = list.size() > 0 ? list.get(0).getTextContent().trim() : null;
		} else {
			logger.error( "Null datasource xml ssh access node cannot be parsed");
		}
		
		if( access != null ) {
			access.setIp(ip);
			access.setPassword(password);
			access.setUser(user);
		} else {
			logger.error( "Values can not be set for null SSHAccess object");
		}
		
		return (user != null && ip != null && password != null);
	}
	
	private boolean populateSocketAccess( Node accessNode, SocketAccess access ) {
		short port = -1;
		String ip = null;
		
		if(accessNode != null ) {			
			List<Node> list = XMLParserTools.getTagsByName(accessNode, DataSourceFormat.IP_ELEMENT_NAME );
			ip = list.size() > 0 ? list.get(0).getTextContent().trim() : null;
			list = XMLParserTools.getTagsByName(accessNode, DataSourceFormat.PORT_ELEMENT_NAME );
			try {
				port = list.size() > 0 ? Short.parseShort( list.get(0).getTextContent().trim() ) : -1;
			} catch( Exception e ) {
				logger.error( "Invalid format provided for port element ");
			}

		} else {
			logger.debug("Null datasource xml socket access node cannot be parsed");
		}
		
		if( access != null ) {
			access.setIp(ip);
			access.setPort(port);
		} else {
			logger.error( "Values can not be set for a null SocketAccess object");
		}
		return ( port > 0 && ip != null );
	}
	
	public Crawler parseCrawler( Node access ) {
		Crawler c = new Crawler();
		String maxcount, maxsize, maxdepth;
		List<Node> crawlers = XMLParserTools.getTagsByName(access, DataSourceFormat.CRAWLER_CONFIGURATION_ELEMENT );
		if( crawlers.size() > 0 ) {
			maxdepth = XMLParserTools.getNodeAttrValue( crawlers.get(0), DataSourceFormat.CRAWLER_MAX_DEPTH_ATTR );
			maxcount = XMLParserTools.getNodeAttrValue( crawlers.get(0), DataSourceFormat.CRAWLER_MAX_PAGES_ATTR );
			maxsize = XMLParserTools.getNodeAttrValue( crawlers.get(0), DataSourceFormat.CRAWLER_MAX_PAGE_SIZE_ATTR );
			
			int depth = TypeConversion.stringToInt( maxdepth), 
				count = TypeConversion.stringToInt( maxcount ),
				size = TypeConversion.stringToInt( maxsize );
			
			c.setMaxDepth( depth );
			c.setMaxPageCount( count );
			c.setMaxPageSize( size == 0 ? 1000000 : size);
			
			List<Node> list = XMLParserTools.getTagsByName( crawlers.get(0), DataSourceFormat.CRAWLER_URL_REGEX_ELEMENT );
			
			List<String> urls = new ArrayList<String>();
			for( Node n : list ) {
				urls.add( n.getTextContent() );
			}
			c.setUrlRegexes( urls );
		}
		
		return c;
	}
	
	private Map<String, String> parseVarList( Node listRoot ) {
		Map<String, String> varMap = new HashMap<String,String>();
		List<Node> nodeList = XMLParserTools.getTagsByName( listRoot, DataSourceFormat.LIST_ELEMENT_ELEMENT_NAME );
		
		for( Node n : nodeList ) {
			String [] keyValuePair = n.getTextContent().split( DataSourceFormat.LIST_KEY_VALUE_DELIMITER );
			if( keyValuePair.length == 2 ) {
				varMap.put( keyValuePair[0].trim(), keyValuePair[1].trim() );
			}
		}
		return varMap;
	}
}
