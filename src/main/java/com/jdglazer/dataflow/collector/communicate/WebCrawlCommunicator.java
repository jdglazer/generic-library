package com.jdglazer.dataflow.collector.communicate;

import com.jdglazer.web.crawler.WebCollectorConfig;
import com.jdglazer.web.crawler.WebCollectorFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * This is the primary wrapper class to implement the web crawler 
 * from external applications
 * @author Glazer, Joshua D.
 *
 */
public class WebCrawlCommunicator implements CommunicatorBase {
	
	private WebCollectorConfig config;
	private WebCollectorFactory factory;
	private CrawlController controller;
	
	public WebCrawlCommunicator( WebCollectorConfig config ) throws Exception {
		this.config = config;
		init();
	}
	
	private void init() throws Exception {
        String crawlStorageFolder = "C:\\Users\\jglazer\\Documents";
        
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(this.config.getStorageFolder() );
        config.setMaxDepthOfCrawling(this.config.getMaxDepth() );
        config.setMaxDownloadSize(this.config.getMaxPageSize() );

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed( this.config.getSeedUrl() );
        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        factory = new WebCollectorFactory( this.config );
	}

	public void start() {
		controller.start( factory, 1 );
	}
}
