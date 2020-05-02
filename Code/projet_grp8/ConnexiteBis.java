package projet_grp8;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class ConnexiteBis{

	/**
	 * filtre pour recupere une image en noir et blanc pour colorier
	 * @param img : image en noi et blanc
	 * @return
	 */
	public BufferedImage connexiteBis(BufferedImage bfi) {
		int height = bfi.getHeight();
		int width = bfi.getWidth();

		int p = bfi.getRGB(0, 0);
		int r = (p>>16)&0xff;
		int pxValue = 1;
		int newpxValue = 1;
		BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		for(int j = 1;  j < width-1; j++) { //gauche a droite
			for(int i = 1; i < height-1; i++) { //haut vers le bas
				p = bfi.getRGB(j, i);
				r = (p>>16)&0xff;
				int px = 0;
				px = (r > 127 ) ? 1 : 0; // 1:pixel blanc , 0 pixel noir
				/**
				 * Si le pixel de l'image binaire est nul, on ne fait rien
				 */
				if(px==0) { //255 blanc; 0 noir
					//rien
				}
				/**
				 * Sinon on regarde les prédécesseurs
				 */
				else {
					/**
					 * Si les predecesseurs n'ont pas de numero 
					 * on assigne un nouveau numéro a P
					 */
					if(predecesseur(j,i,bfi)) {
						newpxValue++;
						int[] value = { newpxValue, newpxValue, newpxValue, 255 };
						outImage.getRaster().setPixel(j, i, value);
						System.err.print(" newpxValue "+newpxValue);
//						System.err.print(newpxValue);
//						p = bfi.getRGB(j, i);
//						r = (p>>16)&0xff;
//						System.out.println(r);
					}
					/**
					 * Sinon on assigne à P le plus petit numéro de zone de ses predecesseurs 
					 * et on met a jour la table de correspondance
					 */
					else { 
						pxValue = lowestPxPredecesseur(j,i,bfi);
						int[] value = { pxValue, pxValue, pxValue, 255 };
						outImage.getRaster().setPixel(j, i, value);
					}
				}
			}
		}
		try {
			Imshow.imshow(outImage);
		} catch (IOException e) {
			System.out.println("image non affiché car elle dois etre null, n'a pas de pointeur ou inexistant");
		}
		return outImage;
	}
	
	/**
	 * savoir si le predecesseur possede une valeur 
	 * @param j : coordonnee x
	 * @param i : coordonnee y
	 * @param img
	 * @return
	 */
	public boolean predecesseur(int j, int i, BufferedImage img) {
		boolean isNotNum = false;

		int p1 = img.getRGB(j-1,i-1);
		int p2 =img.getRGB(j,i-1);
		int p3 =img.getRGB(j+1,i-1);
        int p4 =img.getRGB(j-1,i);
        if(((p1>>16)&0xff) <=1 && ((p2>>16)&0xff) <=1 && ((p3>>16)&0xff) <=1 && ((p4>>16)&0xff) <=1) {
        	isNotNum = true;
        }
		return isNotNum;
	}
	
	/**
	 * donne la valeur la plus petite parmis les predecesseurs
	 * @param x : coordonne x 
	 * @param y : coordonne y
	 * @param img
	 * @return
	 */
	public int lowestPxPredecesseur(int j, int i, BufferedImage img) {

		
		int p1 = img.getRGB(j-1,i-1);
		int p2 = img.getRGB(j,i-1);
		int p3 = img.getRGB(j+1,i-1);
        int p4 = img.getRGB(j-1,i);
        int p5 = img.getRGB(j,i);
        
		int r1 = (p1>>16)&0xff;
		int r2 = (p2>>16)&0xff;
		int r3 = (p3>>16)&0xff;
        int r4 = (p4>>16)&0xff;
        int r5 = (p5>>16)&0xff;
        
        System.out.print(r1+" "+r2+" "+r3+" "+r4+" ");
		int lowestPx = r5;
		
        lowestPx = lower(r1, r2, lowestPx);
        lowestPx = lower(r2, r3, lowestPx);
        lowestPx = lower(r3, r4, lowestPx);
        System.out.println(" / "+lowestPx);
		return lowestPx;
	}
	
	/**
	 * recupere le plus petit de a et b sans inclure la valeur 0 pour le noir
	 * @param a la premiere valeur 
	 * @param b la seconde valeur
	 * @param base la valeur initiale
	 * @return
	 */
	public int lower(int a, int b, int base) {
		if(a >= 1 && b >= 1) {
			base = Math.min(a, b);
		}
		else if (a>= 1) {
			base = a;
		}
		else if (b>= 1) {
			base = b;
		}
		else {
			System.err.println("erreur de condition les valeur sont a 0");
		}
		return base;
	}
}
