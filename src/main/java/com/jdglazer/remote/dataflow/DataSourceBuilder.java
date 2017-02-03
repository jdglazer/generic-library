/**
 * A class to convert an xml data source definition into a DataSource object
 * @author Glazer, Joshua D.
 */
package com.jdglazer.remote.dataflow;

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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jdglazer.remote.dataflow.access.AccessCredentials;
import com.jdglazer.remote.dataflow.access.HTTPAccess;
import com.jdglazer.remote.dataflow.access.HTTPSAccess;
import com.jdglazer.remote.dataflow.access.SSHAccess;
import com.jdglazer.remote.dataflow.access.SocketAccess;
import com.jdglazer.utils.xml.XMLParser;

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
	 * @return DataSource object based on information in datasource file
	 */
	public Map<String, DataSource> build() {
		if( getData() ) {
			System.out.println("succeeded");
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
						 updateInterval = nm.getNamedItem(DataSourceFormat.DATA_SOURCE_UPDATE_INTERVAL_ATTR);
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
							logger.debug( "Invalid access credentials detected for data source of name: "+name+"in files: "+datasource);
							continue;
						}
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
	public AccessCredentials getAccessCredentials( Node dataSource ) {
		AccessCredentials accessCredentials = null;
		
		NodeList DsChilds = dataSource.getChildNodes();
		Node access = null;
		
		for( int j = 0; j < DsChilds.getLength(); j++ ) {
			access = DsChilds.item(j);
			if( access.getNodeType() == Node.ELEMENT_NODE ) {
				if( access.getNodeName().toLowerCase().equals(DataSourceFormat.CREDENTIAL_PROVIDER_NAME) ) {
					break;
				}
			}
		}
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
	
	private boolean populateHttpAccess( Node accessNode, HTTPAccess obj ) {
		if ( accessNode != null ) {
			NodeList nl = accessNode.getChildNodes();
			//true when fields are found
			boolean address = false, getList = false, postList = false;
			for( int i = 0; i < nl.getLength(); i++ ) {
				Node n = nl.item(i);
				if( n.getNodeType() == Node.ELEMENT_NODE ) {
					String nodeName = n.getNodeName();
					if( nodeName.equals( DataSourceFormat.WEB_ADDRESS_ELEMENT_NAME) ) {
						address = true;
						obj.setAddress( n.getTextContent().trim() );
					}
					else if( nodeName.equals( DataSourceFormat.GET_LIST_ELEMENT_NAME) ) {
						getList = true;
						obj.setGetVars( parseVarList( n ) );
					}
					else if( nodeName.equals( DataSourceFormat.POST_LIST_ELEMENT_NAME) ) {
						postList = true;
						obj.setPostVars( parseVarList( n ) );
					}
				}
				if( address && getList && postList  )
					return true;
			}
		} else {
			logger.debug( "Null datasource xml http(s) access node cannot be parsed");
		}
		return false;
	}
	
	private boolean populateSshAccess( Node accessNode, SSHAccess access ) {
		if( accessNode != null ) {
			NodeList nl = accessNode.getChildNodes();
			//true when elements are found
			boolean user = false, ip = false, password = false;
			for( int i = 0; i < nl.getLength(); i++ ) {
				Node n = nl.item(i);
				if( n.getNodeType() == Node.ELEMENT_NODE ) {
					String nodeName = n.getNodeName();
					if( nodeName.equals( DataSourceFormat.IP_ELEMENT_NAME ) ) {
						ip = true;
						access.setIp( n.getTextContent().trim() );
					}
					else if ( nodeName.equals( DataSourceFormat.PASSWORD_ELEMENT_NAME ) ) {
						password = true;
						access.setPassword( n.getTextContent().trim() );
					}
					else if ( nodeName.equals( DataSourceFormat.USER_ELEMENT_NAME ) ) {
						user = true;
						access.setUser( n.getTextContent().trim() );
					}
				}
				
				if( user && password && ip )
					return true;
			}
		} else {
			logger.debug( "Null datasource xml ssh access node cannot be parsed");
		}
		return false;
	}
	
	private boolean populateSocketAccess( Node accessNode, SocketAccess access ) {
		if(accessNode != null ) {
			NodeList nl = accessNode.getChildNodes();
			//true when elements are found
			boolean port = false, ip = false;
			for( int i = 0; i < nl.getLength(); i++ ) {
				Node n = nl.item(i);
				if( n.getNodeType() == Node.ELEMENT_NODE ) {
					String nodeName = n.getNodeName();
					if( nodeName.equals( DataSourceFormat.IP_ELEMENT_NAME ) ) {
						ip = true;
						access.setIp( n.getTextContent().trim() );
					}
					else if ( nodeName.equals( DataSourceFormat.PORT_ELEMENT_NAME ) ) {
						try {
							access.setPort( Short.parseShort( n.getTextContent().trim() ) );
							port = true;
						} catch( Exception e) {
							
						}
					}
				}
				
				if( port && ip )
					return true;
			}
		} else {
			logger.debug("Null datasource xml socket access node cannot be parsed");
		}
		return false;
	}
	
	private Map<String, String> parseVarList( Node listRoot ) {
		Map<String, String> varMap = new HashMap<String,String>();
		if( listRoot != null ) {
			NodeList nl = listRoot.getChildNodes();
			for( int i = 0; i < nl.getLength(); i++ ) {
				Node n = nl.item(i);
				if( n.getNodeType() == Node.ELEMENT_NODE ) {
					if( n.getNodeName().equals( DataSourceFormat.LIST_ELEMENT_ELEMENT_NAME ) ) {
						String [] keyValuePair = n.getTextContent().split( DataSourceFormat.LIST_KEY_VALUE_DELIMITER );
						if( keyValuePair.length == 2 ) {
							varMap.put( keyValuePair[0].trim(), keyValuePair[1].trim() );
						}
					} 
				}
			}
		}
		
		return varMap;
	}
	
}
