package projet_grp8;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Egalisation {
	/*
	 * Affichage de l'image
	 */
	public static void imshow(BufferedImage image) throws IOException {
	      //Instantiate JFrame 
	      JFrame frame = new JFrame(); 

	      //Set Content to the JFrame 
	      frame.getContentPane().add(new JLabel(new ImageIcon(image))); 
	      frame.pack(); 
	      frame.setVisible(true);
	}
	
	/*
	 * Ecriture des barres de l'histogramme
	 */
	static BufferedImage zeros(int height, int width) {
		int[] noir = {0,0,0,255};
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
		//WritableRaster raster = img.getRaster();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height ; y++) {
				img.getRaster().setPixel(x, y, noir);
			}
		}
		return img; 
	}
	
	/*
	 * Egalisation de l'image du fichier f
	 * @param f 
	 */
	public void egalisation(BufferedImage img) {
		int[] tab = new int[256];
		int min = 256; 
		int max = 0;
		int[] tabCum = new int[256];
		int cpt = 0;
		int sum = 0; 
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int p = img.getRGB(x,y);
				int r = (p>>16)&0xff;
				tab[r]++;
			
				if(r<min) {
					min = r; 
				}
				if (r>max) {
					max = r;
				}
			}
		}
		
		for (int x = 0; x < 256; x++) {
			sum += tab[x]; 
			tabCum[cpt] = sum;
			cpt++;
		}
		
		System.out.println("min "+min+", max "+max);
		
		int couleur[] = new int[3];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int p = img.getRGB(x,y);
				int r = (p>>16)&0xff;
				
				int j = (((256*(tabCum[r]-1))/(img.getHeight()*img.getWidth())));
				couleur[0] = j;
				couleur[1] = j;
				couleur[2] = j;
				
				img.getRaster().setPixel(x, y, couleur);
			}
		}
	}
}
