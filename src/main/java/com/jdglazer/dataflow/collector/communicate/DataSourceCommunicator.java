package com.jdglazer.dataflow.collector.communicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdglazer.dataflow.collector.DataSource;
import com.jdglazer.dataflow.collector.DataSource.Protocol;
import com.jdglazer.dataflow.collector.DataSourceParserBase;
import com.jdglazer.dataflow.collector.WebCrawl;
import com.jdglazer.dataflow.collector.access.HTTPAccess;
import com.jdglazer.dataflow.collector.crawlers.Crawler;
import com.jdglazer.dataflow.collector.parser.models.ParserModelBase;
import com.jdglazer.dataflow.collector.parser.models.ParserModelBase.Language;
import com.jdglazer.web.crawler.WebCollectorConfig;
import com.jdglazer.web.crawler.WebCollectorParser;

import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class DataSourceCommunicator {
	
	Logger logger = LoggerFactory.getLogger( DataSourceCommunicator.class );
	
	private DataSource dataSource;
	
	private DataSourceParserBase parserBase;
	
	public DataSourceCommunicator( DataSource dataSource ) {
		this.dataSource = dataSource;
	}
	
	private void init() {
		Protocol protocol = dataSource.getAccess().getProtocol();
		
		switch( protocol ) {
		case http:
			;
		case https:
			try {
				parserBase = createCrawler();
			} catch (Exception e) {
				logger.debug("Failed to build an instance of the web crawler.");
			}
			break;
		case socket:
			//to add code soon
			break;
		case ssh:
			//to add code soon
			break;
		}
	}
	
	private WebCrawl createCrawler() throws Exception {
		WebCollectorConfig config;
		HTTPAccess access = (HTTPAccess) dataSource.getAccess();
		ParserModelBase parserBase = dataSource.getDatasourceParser();
		Language language = parserBase.getLanguage();
		
		config = new WebCollectorConfig( access.getAddress() );
		config.setStorageFolder( parserBase.getOutputFileDirectory() );
		
		switch( language ) {
		case none:
			
		}
		Crawler crawler = access.getCrawler();
		if( crawler != null ) {
			config.setMaxDepth( crawler.getMaxDepth() );
			config.setMaxPages( crawler.getMaxPageCount() );
			config.setMaxPageSize( crawler.getMaxPageSize() );
			config.setUrlRegexes( crawler.getUrlRegexes() );
		}
		
		config.setParser( new ParseIt() );
		WebCrawl webCrawl = new WebCrawl( config );
		return webCrawl;
	}
	
	//private void 
	
	public void execute() {
		init();
		if( parserBase instanceof WebCrawl ) {
			( (WebCrawl) parserBase ).start();
		}
	}
	
	public class ParseIt implements WebCollectorParser {

		public void parseCrawlerData(String contentType, WebURL url, ParseData parseData) {
			if(parseData instanceof HtmlParseData) {
				HtmlParseData data = (HtmlParseData) parseData;
				String text = data.getText();
				System.out.println(" "+text.length()+"  contains trump reference: "+text.toLowerCase().contains("lockheed martin")+" "+url.getURL());
			}
			
		}
		
	}
} 
