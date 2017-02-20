package com.jdglazer.dataflow.collector;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataSourceAttrValueParser {
	
	private static Logger logger = LoggerFactory.getLogger( DataSourceAttrValueParser.class );
	
	/**
	 * Turns the active interval string passed in into a set of start-end intervals
	 * @param updateInt Update interval string
	 * @return array list of start end intervals
	 */
	public static ArrayList<short[]> parserUpdateAliveInterval( String updateInt ) {
		ArrayList<short[]> updateInts = new ArrayList<short[]>();
		if( updateInt != null ) {
			String [] intervals = updateInt.split( DataSourceFormat.ACTIVE_INTERVAL_DELIMITER );
			String [] startEnd;
			for ( String s : intervals ) {
				startEnd = s.split( DataSourceFormat.ACTIVE_INTERVAL_START_END_DELIMETER );
				if( startEnd.length == 2 ) {
					try {
						short start = Short.parseShort(startEnd[0]);
						short end = Short.parseShort( startEnd[1] );
						
						if( start < 168 && start > -1 && end > -1 && end < 168 ) {
							updateInts.add(new short[] { start, end } );
						} 
						else {
							throw new Exception();
						}
					} catch( Exception e ) {
						logger.debug( "Invalid update interval detected, "+s+"." );
					}
				}
			}
		}
		return updateInts;
	}
}
