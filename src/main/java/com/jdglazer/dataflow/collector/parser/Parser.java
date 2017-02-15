package com.jdglazer.dataflow.collector.parser;

/**
 * Base class for all java parser implementations
 * @author jglazer
 *
 */
public class Parser {
	public String CLASS_NAME;
	public Parser( String CLASS_NAME ) {
		this.CLASS_NAME = CLASS_NAME;
	}
}
