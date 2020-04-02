package code.java;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Imshow {
	public static void imshow(BufferedImage image) throws IOException {
	      //Instantiate JFrame 
	      JFrame frame = new JFrame(); 

	      //Set Content to the JFrame 
	      frame.getContentPane().add(new JLabel(new ImageIcon(image))); 
	      frame.pack(); 
	      frame.setVisible(true);
	}
	public static void main(String[] args) throws IOException {
		File path = new File("C:\\Users\\willy\\Documents\\Licence_maths_info\\S6\\Image\\TD\\ImageL3-master\\ImageL3-master\\Test_Images\\text1.jpg");
		BufferedImage img = null;
		
		img = ImageIO.read(path);
		
		BufferedImage imgBinaire = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
	    Graphics2D surfaceImg = imgBinaire.createGraphics();
	    surfaceImg.drawImage(img, null, null);      
	    img = imgBinaire;
	    
	    int width = img.getWidth();
	    int height = img.getHeight();
	    int[]tabHeight=new int[height];
	    int[]tabWidth=new int[width];
	    int sum = 0;
	    
	    for (int y=0; y<height; y++) { //histograme par ligne
		    for (int x=0; x<width; x++) {
		    	if(((img.getRGB(x, y)>>8)&0xff) == 0)
		    		sum++;
		    }
		    tabHeight[y]=sum;
		    sum=0;
	    }   

	    for (int x=0; x<width; x++) { //histograme par colonne
	    	for (int y=0; y<height; y++) {
	    		if(((img.getRGB(x, y)>>8)&0xff) == 0)
	    			sum++;
	    	}
	    	tabWidth[x]=sum;
	    	sum=0;
	    }   
	    //trace de l'histogramme
	    BufferedImage bi2 = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi2.createGraphics();
		for (int i=0; i<tabHeight.length; i++)
			g2.drawLine(20, i, 20+(tabHeight[i]), i); 
		
		//trace de l'histogramme
		BufferedImage bi3 = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g3 = bi3.createGraphics();
		//ligne droite
		for (int i=0; i<tabWidth.length; i++)
			g3.drawLine(i, 20, i, 20+(tabHeight[i]));
		
		imshow(bi2);
		imshow(bi3);
	    imshow(img);
	}
}
