package projet_grp8;

import java.awt.image.BufferedImage;

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
		int p = img.getRGB(0,0); //r�cuperer le pixel � coordonn� x et y
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
