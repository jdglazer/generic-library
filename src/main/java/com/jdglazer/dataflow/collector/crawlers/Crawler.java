package com.jdglazer.dataflow.collector.crawlers;

import java.util.ArrayList;
import java.util.List;
/**
 * Model class for crawler data from xml
 * @author jglazer
 *
 */
public class Crawler {
	
	private int maxDepth;
	private int maxPageCount;
	private int maxPageSize;
	private List<String> urlRegexes;
	
	public int getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	public int getMaxPageCount() {
		return maxPageCount;
	}
	public void setMaxPageCount(int maxPageCount) {
		this.maxPageCount = maxPageCount;
	}
	public int getMaxPageSize() {
		return maxPageSize;
	}
	public void setMaxPageSize(int maxPageSize) {
		this.maxPageSize = maxPageSize;
	}
	public List<String> getUrlRegexes() {
		if( urlRegexes == null)
			urlRegexes = new ArrayList<String>();
		return urlRegexes;
	}
	public void setUrlRegexes(List<String> urlRegexes) {
		this.urlRegexes = urlRegexes;
	}
	
	@Override
	public boolean equals( Object crawler ) {
		if( !( crawler instanceof Crawler) ) return false;
		Crawler c = (Crawler) crawler;
		return maxDepth == c.getMaxDepth() && maxPageCount == c.getMaxPageCount() && maxPageSize == c.getMaxPageSize() && urlRegexes.equals( c.getUrlRegexes() );
	}
}
