package com.jdglazer.utils.xml;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XMLParserTools {	
	
	/**
	 * Gets the value associated with a specific node's attribute
	 * @param node
	 * @param attrName
	 * @return
	 */
	public static String getNodeAttrValue( Node node, String attrName ) {
		String attrVal = null;
		if( node != null && attrName != null ) {
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Node attr = node.getAttributes().getNamedItem( attrName );
				if( attr != null ) {
					attrVal = attr.getNodeValue();
				}
			}
		}
		return attrVal;
	}
	
	/**
	 * Gets a list of all nodes under a specified name in a given parent node
	 * @param parent
	 * @param name
	 * @return
	 */
	public static List<Node> getTagsByName( Node parent, String name ) {
		List<Node> childs = new ArrayList<Node>();
		NodeList nl = parent.getChildNodes();
		for( int i = 0; i < nl.getLength(); i++ ) {
			Node n = nl.item(i);
			if( n instanceof Element && n.getNodeName().equals(name) ) {
				childs.add( n );
			}
		}
		return childs;
		
	}
	
	/**
	 * Converts the given node to an xml string
	 * @param node
	 * @return
	 */
	public static String getNodeAsString( Node node ) {
		StringWriter sw = new StringWriter();
		try {
		 Transformer t = TransformerFactory.newInstance().newTransformer();
		 t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		 t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {		 
		}
		return sw.toString();
	}
	
}
