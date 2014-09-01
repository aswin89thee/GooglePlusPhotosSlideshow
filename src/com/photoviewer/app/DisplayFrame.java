package com.photoviewer.app;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class DisplayFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 700;
	ImageComponent component;

	public DisplayFrame(){
		setTitle("Slideshow of your Google Plus images!");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);

		component = new ImageComponent();
		add(component);

		pack();
		setVisible(true);
	}

	public void setImageUrl(String url) {
		component.setImage(url);
	}
	
	public void updateImage(String url){
		setImageUrl(url);
		this.repaint();
	}

}

class ImageComponent extends JComponent{
	private static final long serialVersionUID = 1L;
	private Image image;
	public ImageComponent(){
	}

	public ImageComponent(String url)
	{
		try{
			image = ImageIO.read(new URL(url));
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	void setImage(String url)
	{
		try{
			image = ImageIO.read(new URL(url));
		}
		catch (IOException e){
			System.out.println("Exception trying to read image from url " + url);
			e.printStackTrace();
		}
	}

	public void paintComponent (Graphics g){
		if(image == null) return;
		//Position the image to the center
		int x = (this.getWidth() - image.getWidth(null)) / 2;
		int y = (this.getHeight() - image.getHeight(null)) / 2;
		
		//Fit image to the screen
		int type = ((BufferedImage)(image)).getType() == 0? BufferedImage.TYPE_INT_ARGB : ((BufferedImage)(image)).getType();
		BufferedImage resizeImageJpg = resizeImage(((BufferedImage)(image)), type);

		g.drawImage(resizeImageJpg, 0, 0, this);
	}
	
	private BufferedImage resizeImage(BufferedImage img, int type){
		int IMG_WIDTH = this.getWidth();
		int IMG_HEIGHT = this.getHeight();

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		
		//Scale the image but maintain the aspect ratio
		if(img.getWidth() > img.getHeight())
			g.drawImage(img.getScaledInstance(IMG_WIDTH, -1, Image. SCALE_SMOOTH), 0, 0, this);
		else
		{
			int x = (IMG_WIDTH - img.getWidth(null)) / 2;
			int y = (IMG_HEIGHT - img.getHeight(null)) / 2;
			g.drawImage(img.getScaledInstance(-1, IMG_HEIGHT, Image. SCALE_SMOOTH), x, y, this);
		}
		
		g.dispose();

		return resizedImage;
	}


}