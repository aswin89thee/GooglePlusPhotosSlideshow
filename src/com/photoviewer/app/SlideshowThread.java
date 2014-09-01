package com.photoviewer.app;


public class SlideshowThread implements Runnable {
	
	long timeout = 5000;
	SlideshowWebClient client = null;

	public SlideshowThread(SlideshowWebClient slideshowWebClient, long timeOut){
		this.timeout = timeOut;
		this.client = slideshowWebClient;
	}

	@Override
	public void run() {
		DisplayFrame slideshowFrame = new DisplayFrame();
		
		PicasaImageRetriever retriever = null;
		retriever = new PicasaImageRetriever(client);
		
		while(true){
			String url = retriever.getNextImageUrl();
			if(!url.equals(""))
				slideshowFrame.updateImage(url);
			try {
				Thread.sleep(this.timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
