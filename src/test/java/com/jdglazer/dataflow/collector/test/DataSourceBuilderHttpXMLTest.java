package com.jdglazer.dataflow.collector.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.jdglazer.dataflow.collector.DataSource;
/**A class to test a series of valid data source xml declarations based on established schema. Relies on an xml file
 * with valid registered data sources
 * @author jglazer
 *
 */
public class DataSourceBuilderHttpXMLTest {
	
	Map< String, DataSource> datasourceMap;
	
	public DataSourceBuilderHttpXMLTest(Map<String, DataSource> datasourceMap) {
		this.datasourceMap = datasourceMap;
	}


	@Test
	public void testHttpWithInsufficientInformation() {
	}
	/**
	 * Tests an http data source with all possible attributes & tags registered
	 */
	@Test
	public void testHttpWithAllValidItems() {
		fail("Not yet implemented");
	}
	
	/**
	 * Tests an http data source with all required attributes & tags registered
	 */
	@Test
	public void testHttpWithRequiredOnly() {
	}
	
	/**
	 * Tests an http data source with bunches of extra tags and attributes registered
	 */
	@Test
	public void testHttpWithExtraItems() {
	}
}
