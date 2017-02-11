package com.jdglazer.dataflow.collector.test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.jdglazer.dataflow.collector.DataSource;
import com.jdglazer.dataflow.collector.DataSourceBuilder;
import com.jdglazer.dataflow.collector.access.AccessCredentials;
import com.jdglazer.dataflow.collector.access.HTTPAccess;
import com.jdglazer.dataflow.collector.access.SSHAccess;
import com.jdglazer.dataflow.collector.access.SocketAccess;
import com.jdglazer.dataflow.collector.parsers.BashParserModel;
import com.jdglazer.dataflow.collector.parsers.JavaParserModel;

public class DataSourceBuilderTest {
	
	public static void main( String [] args ) throws ParserConfigurationException, SAXException, IOException {
		
		long time = System.currentTimeMillis();
		DataSourceBuilder dsb = new DataSourceBuilder( new File("C:\\Users\\jglazer\\git\\generic-library\\src\\test\\resources\\dataSourceTest.xml") );
		Map<String, DataSource> ds = dsb.build();
		
		DataSource ds1 = ds.get("test-source-1"), ds2 = ds.get("test-source-2");
		System.out.println( "Update Intervals: "+ds1.getUpdateInterval()+", "+ds2.getUpdateInterval() );
		System.out.println( "Protocols: "+ds1.getAccess().getProtocol().toString()+", "+ds2.getAccess().getProtocol().toString() );
		
		HTTPAccess ds1a = ( (HTTPAccess) ds1.getAccess() );
		SocketAccess ds2a = ( (SocketAccess) ds2.getAccess() );
		
		System.out.println("Access credentials http: "+ds1a.getAddress()+", "+ds1a.getGetVars().get("passwd")+", "+ds1a.getPostVars().get("usrname") );
		System.out.println( "Access Credentials socket: "+ds2a.getIp()+", "+ds2a.getPort() );
		
		JavaParserModel jpm = ( JavaParserModel ) ds1.getDatasourceParser();
		BashParserModel bpm = ( BashParserModel ) ds2.getDatasourceParser();
		
		System.out.println("java parser: "+jpm.getLanguage().toString()+", "+jpm.getParserFilePath() );
		System.out.println( "bash parser: "+bpm.getLanguage().toString()+", "+bpm.getDataOutputPath()+", "+bpm.getParserFilePath() );
		
		System.out.println( "Time to execute: "+(System.currentTimeMillis() - time ) );
	}
}
