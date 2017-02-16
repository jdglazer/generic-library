package com.jdglazer.utils.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

public abstract class XMLParser {
	
	Logger logger = LoggerFactory.getLogger(XMLParser.class);
	
	protected DocumentBuilder builder;
	
	protected Document document;
	
	public XMLParser() throws ParserConfigurationException {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}
	
	protected Document getDocument() {
		return document != null ? document : builder.newDocument();
	}
	
	protected void parseFile( File file ) throws SAXException, IOException {
		document = builder.parse(file);
	}
	
	protected void parseInput( InputStream input ) throws SAXException, IOException {
		document = builder.parse(input);
	}
	
	protected List<Node> getTagsByName( String name ) {
		if( document == null ){
			logger.debug("Null xml document detected");
			return null;
		}
		NodeList nodeList = document.getElementsByTagName(name);
		return nodeListToList( nodeList );
	}
	
	protected List<Node> getTagsByAttributeValue( String attribute, String value ) {
		if( document == null )
			return null;
		
		document.normalize();
		NodeList nodeList = document.getElementsByTagName("*");
		List<Node> list = new ArrayList<Node>();
		
		for( int i = 0; i < nodeList.getLength(); i++ ) {
			Node node = nodeList.item(i);
			NamedNodeMap nodeMap = node.getAttributes();
			
			if( nodeMap == null )
				continue;
			
			Node attr = nodeMap.getNamedItem(attribute);
			
			if( attr == null )
				continue;
			
			if( !attr.getNodeName().equals( attribute ) )
				continue;
			
			if( attr.getNodeValue().equals( value ) )
				list.add( node );			
		}
		
		return list;
	}
	
//Generic utilities
	private List<Node> nodeListToList( NodeList nodeList ) {
		List<Node> list = new ArrayList<Node>();
		for( int i = 0; i < nodeList.getLength(); i++ )
			list.add( nodeList.item(i) );
		return list;
	}
	
}
