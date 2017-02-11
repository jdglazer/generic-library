/**
 * A base interface that defines data input and output function necessary for all parsers
 */
package com.jdglazer.remote.dataflow;

public interface DataSourceParserBase {
	
	/**
	 * 
	 */
	public boolean rawDataIn( byte [] input );
	public <T> T rawDataOut();
	public void setEncoding(String encoding);
}
