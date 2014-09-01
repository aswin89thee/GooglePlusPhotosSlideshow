package com.photoviewer.app;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;

public class PicasaImageRetriever {
	List<AlbumEntry> albums;
	Random random = new Random();
	SlideshowWebClient client;

	public PicasaImageRetriever(SlideshowWebClient client) {
		try {
			this.client = client;
			albums = this.client.getAlbums();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the url of a random image from the web albums
	 * @return
	 */
	public String getNextImageUrl() {
		String url = "";
		int albumIndex = getRandomIndex(albums);
		if(albumIndex == -1) return url;
		List<PhotoEntry> photos = null;
		PhotoEntry photo = null;
		try {
			photos = client.getPhotos(albums.get(albumIndex));
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
		}
		if(photos != null && photos.size() != 0)
		{
			photo = photos.get(getRandomIndex(photos));
			url = photo.getMediaThumbnails().get(0).getUrl();
			url = get800pUrl(url);
		}
		else{
			getNextImageUrl();
		}

		return url;
	}
	
	private String get800pUrl(String strUrl) {
		  String regex = "/s[0-9]*/";
		  StringBuffer sb = new StringBuffer();
		  Pattern p = Pattern.compile(regex);
		  Matcher m = p.matcher(strUrl);
		  
		  while (m.find())
		  {
		      // Avoids throwing a NullPointerException in the case that you
		      // Don't have a replacement defined in the map for the match
		      String repString = "/s800/";
		      if (repString != null)    
		          m.appendReplacement(sb, repString);
		  }
		  m.appendTail(sb);
		  
		  return sb.toString();
	  }

	private int getRandomIndex(List list) {
		int index = -1;
		if(list == null) return -1;
		if(list.size() != 0){
			index = Math.abs(random.nextInt() % list.size());
		}
		return index;
	}

}
