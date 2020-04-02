package projet_grp8;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageColoring {

	/**
	 * tranforme l'image de couleur en nuance de gris
	 * @param img
	 * @return
	 */
	public BufferedImage ImgGrey (BufferedImage img) {
		int tab[] = new int[256];
		for (int i=0; i<tab.length; i++ ) {
			tab[i]=0;
		}		
		int p = img.getRGB(0,0); //récuperer le pixel à coordonné x et y
		int r = (p>>16)&0xff; 
		int g = (p>>8)&0xff; 
		int b = p&0xff; 
		for(int x=0; x<img.getWidth(); x++) { //recherche de chaque nuance de gris
			for(int y=0; y<img.getHeight(); y++) {
				p = img.getRGB(x, y);
				g = (p>>8)&0xff; 
				//        		System.out.println("valeur de g "+g);
				tab[g]= tab[g]+1;
			}
		}
		return img;
	}
}
