/**
 * A base interface that defines data input and output function necessary for all parsers
 */
package com.jdglazer.dataflow.collector;

public interface DataSourceParserBase {
	
	/**
	 * 
	 */
	public boolean rawDataIn( byte [] input );
	public <T> T rawDataOut();
	public void setEncoding(String encoding);
}