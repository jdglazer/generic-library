package com.jdglazer.dataflow.utils.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.jdglazer.utils.xml.XMLParser;

public class XmlParse extends XMLParser {
	
	private String TEST_XML_FILE;
	
	public XmlParse() throws ParserConfigurationException, SAXException, IOException {
		super();
		
		File file = new File("/home/joshua/EclipsePrograms/generic-library/src/test/resources/test2.xml" );
		
		this.parseFile( file );
		List<Node> nodes = this.getTagsByAttributeValue("dm-result-handler","_4");
		
		System.out.print( nodes.get(0).getNodeName() );
		
		List<Node> list = this.getTagsByName("_li");
		
		for( Node n1 : list ) {
			System.out.print( n1.getNodeType() );
		}
	}
	
	public static void main( String [] args ) {
		try {
			new XmlParse();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}