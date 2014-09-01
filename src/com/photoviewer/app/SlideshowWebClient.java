package com.photoviewer.app;

import com.google.gdata.client.photos.PicasawebService;
import com.photoviewer.test.PicasawebClient;


public class SlideshowWebClient extends PicasawebClient {

	public SlideshowWebClient(PicasawebService service, String uname,
			String passwd) {
		super(service, uname, passwd);
	}

	public void start() {
		long timeout = 5000;
		Thread t = new Thread(new SlideshowThread(this,timeout));
		t.start();
	}
	
	
}
