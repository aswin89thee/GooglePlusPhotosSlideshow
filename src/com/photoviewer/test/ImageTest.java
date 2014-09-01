package com.photoviewer.test;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class ImageTest {
	public static void main(String[] args){
		new ImageFrame();		
	}
}

class ImageFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ImageFrame(){
		setTitle("ImageTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);

		ImageComponent component = new ImageComponent();
		add(component);

		pack();
		setVisible(true);
	}

	public ImageFrame(String url)
	{
		setTitle("ImageTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);

		ImageComponent component = new ImageComponent(url);
		add(component);

		pack();
		setVisible(true);
	}

	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 700;
}


class ImageComponent extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	public ImageComponent(){
		try{
			File image2 = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg");
			image = ImageIO.read(image2);


		}
		catch (IOException e){
			e.printStackTrace();
		}
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

	public void paintComponent (Graphics g){
		if(image == null) return;
		
		BufferedImage originalImage = (BufferedImage) image;
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizeImageJpg = resizeImage(originalImage, type);
		
		g.drawImage(image, 50, 50, this);
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type){
		int IMG_WIDTH = this.getWidth();
		int IMG_HEIGHT = this.getHeight();

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}

	private BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){

		int IMG_WIDTH = this.getWidth();
		int IMG_HEIGHT = this.getHeight();
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}


}