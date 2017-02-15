package com.jdglazer.dataflow.collector.parser;

import com.jdglazer.dataflow.collector.parser.models.ParserModelBase;

/**
 * Designed to convert a parser model to an actual instance of a parser
 * 
 * @author jglazer
 *
 */
public class ParserBuilder {
	
	private ParserModelBase parserModelBase;
	
	public ParserBuilder( ParserModelBase parserModelBase ) {
		this.parserModelBase = parserModelBase;
	}
	
	public Parser getParser() {
		Parser parser = null;
		
		return parser;
	}
}
