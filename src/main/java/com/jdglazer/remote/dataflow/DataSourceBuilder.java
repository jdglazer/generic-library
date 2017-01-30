/**
 * A class to convert an xml data source definition into a DataSource object
 * @author Glazer, Joshua D.
 */
package com.jdglazer.remote.dataflow;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.jdglazer.utils.communicate.XMLParser;

public class DataSourceBuilder extends XMLParser {
	
	Logger logger = LoggerFactory.getLogger( DataSourceBuilder.class );
	
	/**
	 * stores the datasource xml file
	 */
	private File datasource;
	
	/**
	 * The DataSource object populated by data parsed out of xml
	 */
	private DataSource parsedOutDataSource;	
	
	
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
		parsedOutDataSource = new DataSource();
	}
	
	/**
	 * Parses datasource xml file and builds a DataSource object from it
	 * @return DataSource object based on information in datasource file
	 */
	public DataSource build() {
		parsedOutDataSource = new DataSource();
		
		System.out.println( getRootData() );
		
		return parsedOutDataSource;
	}
	
	/**
	 * Pull data from the root element of the data source xml
	 * 
	 * @return true if the root data source element was found with a non-null name attribute value and an update interval value
	 */
	private boolean getRootData() {
		
		List<Node> list = this.getTagsByName(DataSourceFormat.ROOT_ELEMENT_NAME);
		
		if( list.size() > 0 ) {
			Node root = list.get(0);
			NamedNodeMap nm = root.getAttributes();
			if( nm != null ) {
				Node name = nm.getNamedItem(DataSourceFormat.DATA_SOURCE_NAME_ATTR),
					 updateInterval = nm.getNamedItem(DataSourceFormat.DATA_SOURCE_UPDATE_INTERVAL_ATTR);
				if( name != null && updateInterval != null ) {
					
					String datasource_name = name.getTextContent().trim();
					int datasource_ui = 0;
					try {
						datasource_ui = Integer.parseInt( updateInterval.getTextContent() );
					}
					catch ( Exception e ) {
						logger.debug("Invalid string found for updateInterval in "+datasource);
					}
					
					if( datasource_name.length() > 0 )
						parsedOutDataSource.setName( datasource_name );
					else {
						logger.debug("Null data source name is not allowed. File: "+datasource );
						return false;
					}
					parsedOutDataSource.setUpdateInterval(
					  datasource_ui >= DataSourceFormat.MINIMUM_ALLOWED_UPDATE_INTERVAL
						? datasource_ui
						: DataSourceFormat.DEFAULT_UPDATE_INTERVAL
					);
				}
				else {
					logger.debug("Must set both name and update interval in root datesource tag. File: "+datasource);
					return false;
				}
			}
			else {
				logger.debug("Data source defined at "+datasource+" has no attributes.");
				return false;
			}
		} else {
			logger.debug("No data source root element found in "+datasource);
			return false;
		}
		return true;
	}
	
}
