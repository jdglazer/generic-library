package com.jdglazer.utils.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XMLParserTools {	
	
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
}
