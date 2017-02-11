package com.jdglazer.remote.dataflow.test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.jdglazer.dataflow.collector.DataSource;
import com.jdglazer.dataflow.collector.DataSourceBuilder;
import com.jdglazer.dataflow.collector.DataSourceThreadManager;

public class DataSourceThreadHandlerTest {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DataSourceThreadManager dstm = new DataSourceThreadManager();
		long startTime = System.currentTimeMillis();
		DataSourceBuilder dsb = new DataSourceBuilder( new File ("C:\\Users\\jglazer\\git\\generic-library\\src\\test\\resources\\dataSourceTest.xml") );
		Map<String, DataSource> datasources = dsb.build();
		int iterator = 0;
		
		while( ( System.currentTimeMillis() - startTime ) < 6000000 ) {
			
			switch( iterator ) {
			case 0:
				Set< Entry< String, DataSource > > set = datasources.entrySet();
				for( Entry<String, DataSource> entry : set ) {
					dstm.addDataSource( entry.getValue() );
				}
				break;
			case 10:
				//dstm.removeDataSource("test-source-2");
				break;
			case 180:
				dstm.removeDataSource("test-source-4");
				break;
			case 240:
				dstm.cleanSourcesToRemove();
				break;
			case 300:
				dstm.cleanSourcesToRemove();
				break;
			case 390:
				dstm.addDataSource( datasources.get("test-source-4") );
				break;
			default:
				;
			}
			try {
				Thread.sleep(333);
			} catch (InterruptedException e) {	}
			iterator++;
		}
	}
}
