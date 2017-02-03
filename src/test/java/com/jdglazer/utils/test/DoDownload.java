package com.jdglazer.utils.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.jdglazer.utils.communicate.http.HttpRequest;
import com.jdglazer.utils.communicate.http.HttpRequestParams;

public class DoDownload  {
	
	public static void main( String [] args ) {
		new DoDownload().run();
	}

	public void run() {
		try {
			HttpRequest hrq = new HttpRequest( new HttpRequestParams().setRequestURL("http://www.usbedrock.com/dataFiles/compressedFiles/NM_bedrock.min.pgrd") );
			MonitorDownloads md = new MonitorDownloads();
			md.request = hrq;
			Thread t = new Thread( md );
			t.start();
			File f = new File("/home/joshua/Downloads/NM_bedrock.min.pgrd");
			f.createNewFile();
			hrq.readIntoFile( f );
			md.running = false;
		} catch (MalformedURLException e) {	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class MonitorDownloads implements Runnable {

		public HttpRequest request;
		
		public boolean running = true;
		
		public void run() {
			
			while( running ) {
				try {
					Thread.sleep( 500 );
				} catch (InterruptedException e) {	}
				System.out.println( (int) ( ( (double) request.getHttpInputProgress()/(double) request.getHttpInputSize() ) * 100.0 ) );
			}
			
		}
	}
}
