package com.jdglazer.remote.dataflow.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.jdglazer.remote.dataflow.DataSource;
import com.jdglazer.remote.dataflow.DataSourceBuilder;

public class DataSourceBuilderTest {
	
	public static void main( String [] args ) throws ParserConfigurationException, SAXException, IOException {
		
		long time = System.currentTimeMillis();
		DataSourceBuilder dsb = new DataSourceBuilder( new File("/home/joshua/EclipsePrograms/generic-library/src/test/resources/dataSourceTest.xml") );
		DataSource ds = dsb.build();
		
		System.out.println( "Time: "+(System.currentTimeMillis() - time)+", Name: "+ds.getName()+", Update Interval: "+ds.getUpdateInterval() );
		
	}
}
