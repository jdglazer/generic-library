package com.jdglazer.dataflow.collector.communicate.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.jdglazer.dataflow.collector.DataSource;
import com.jdglazer.dataflow.collector.DataSourceBuilder;
import com.jdglazer.dataflow.collector.communicate.Communicator;
import com.jdglazer.dataflow.utils.communicate.https.HttpsRequest;

public class CommunicatorTest {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		Map< String, DataSource> dsmap = ( new DataSourceBuilder( new File("C:\\Users\\jglazer\\git\\remote-data-collector\\src\\test\\resources\\httpDataSourceTest.xml") ) ).build();
		DataSource /* ds = dsmap.get("http1"), */
				   ds1 = dsmap.get("https1");
		/*Communicator comr = new Communicator( ds.getAccess() );
		HttpRequest request = (HttpRequest) comr.getRequest();
		
		System.out.println("Starting download at "+ new Date( System.currentTimeMillis() ) );
		request.readIntoFile( new File("C:\\Users\\jglazer\\NM_bedrock.min.pgrd") );
		System.out.println( "Download complete at "+new Date( System.currentTimeMillis() ) ); */
		
		Communicator comr1 = new Communicator( ds1.getAccess() );
		HttpsRequest request1 = (HttpsRequest) comr1.getRequest();
		
		byte [] ba;
		System.out.println("Starting download at "+ new Date( System.currentTimeMillis() ) );
		ba = request1.readAsByteArray();
		System.out.println( "Download complete at "+new Date( System.currentTimeMillis() )+", Download size: "+ba.length );		
	}

}
