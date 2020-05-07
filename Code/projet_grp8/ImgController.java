package projet_grp8;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class permettant de modifier le visuel d'une image
 * @author willy
 *
 */
public class ImgController {
	
	/**
	 * Fonction gris : tranforme un objet BufferedImage (image) en nuance de gris
	 * @param bfi l'image à modifier
	 * @return bfi nouvelle image
	 * @throws IOException 
	 */
	public BufferedImage gris(BufferedImage bfi) throws IOException {
		int p = bfi.getRGB(0,0); //récuperer le pixel à coordonné x et y
		int g = (p>>8)&0xff; 
		
		for(int x=0; x<bfi.getWidth(); x++) { //
			for(int y=0; y<bfi.getHeight(); y++) {
				p = bfi.getRGB(x, y);
				g = (p>>8)&0xff; 
				
				//assombrir une image "-", eclaircir "+"
				if((g-=50) <= 0 ) {
					g=0;
				}
				int[] gris = {g, g, g, 255};
				bfi.getRaster().setPixel(x, y, gris);
			}
		}
		return bfi;
	}
	/**
	 * Fonction luminosite : change la luminosité avec une commande int en argument
	 * @param cmd : commande + ou - sur la console
	 */
	public BufferedImage luminosite(int cmd, BufferedImage bfi) {
		System.out.println("je recois :"+cmd);
	    int width = bfi.getWidth();
	    int height = bfi.getHeight();
	    int bright = 0;
	    int p = bfi.getRGB(0,0); //récuperer le pixel à coordonné x et y
		int r = (p>>16)&0xff; 
		int g = (p>>8)&0xff; 
		int b = p&0xff;
		switch (cmd) {
		case 1: 
			bright = 10; break;
		case 2:
			bright = -10; break;
		case 3:
			break;
		default:
			System.out.println("Commande invalide, choisissez + ou -");
		}
		//si la valeur de pixel est trop petit <10 on a 0 ou trop grand >245 on a 255
		for(int x = 0;  x < width; x++) {
	       for(int y = 0; y < height; y++) {
	    	   p = bfi.getRGB(x, y);
	    	   r = (p>>16)&0xff;
	    	   g = (p>>8)&0xff; 
	    	   b = p&0xff; 
	    	   r = isMinOrMax(r, cmd, bright);
	    	   g = isMinOrMax(g, cmd, bright);
	    	   b = isMinOrMax(b, cmd, bright);
	    	   int [] newBright = {r, g, b, 255};
	    	   bfi.getRaster().setPixel(x, y, newBright);
	       }
		}
		return bfi;
	}
	/**
	 * Fonction isMinOrMax : cette methode evite de faire dapasser les valeur min et max car cela peut cree un pb
	 * @param rgb : valeur du pixel
	 * @param cmd : commande choisi
	 * @param bright : luminosite
	 * @return
	 */
	public static int isMinOrMax(int rgb,int cmd, int bright) {
		if (cmd==1 && rgb > 240) rgb = 255;
		else if (cmd==2 && rgb < 15) rgb = 0;
		else rgb = rgb + bright;
		
		return rgb;
	}
	
	/**
	 * Fonction inverseBinary : inversion des pixels noir et blanc de l'image
	 * @param bfi l'image initial
	 * @return bfi l'image résultat de l'inversion
	 */
	public BufferedImage inverseBinary(BufferedImage bfi) {
		int p = bfi.getRGB(0,0); //récuperer le pixel à coordonné x et y
		int g = (p>>8)&0xff; 
		int[] couleurNoir = {0, 0, 0, 255}; //couleur noir
	    int[] couleurBlanc = {255, 255, 255, 255}; //pour donner la couleur blanche
		for(int x=0; x<bfi.getWidth(); x++) { //
			for(int y=0; y<bfi.getHeight(); y++) {
				p = bfi.getRGB(x, y);
				g = (p>>8)&0xff; 
				
				if(g>=240) {
					bfi.getRaster().setPixel(x, y, couleurNoir);
				}else
					bfi.getRaster().setPixel(x, y, couleurBlanc);
			}
		}
		return bfi;
		
	}
	
	/**
	 * Fonction seuillage : seuil une image en noir et blanc
	 * @param img : image à seuiller pour donner une image NB
	 * @param s : le seuil en float
	 * @return l'image suille
	 */
	public BufferedImage seuillage(BufferedImage img, float s) {
		int rows = img.getHeight();
		int cols = img.getWidth();
		
		BufferedImage im_thresh = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		
		
		int[] noir = {0,0,0,255};
		int[] blanc = {255,255,255,255};
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int pixel = img.getRGB(j, i);
				int red = (pixel >> 16) & 0xff;
				if (red > s) {
					im_thresh.getRaster().setPixel(j, i, blanc);
				}else {
					im_thresh.getRaster().setPixel(j, i, noir );
				}
			}
		}
		
		return im_thresh;
	}
	
	
	/**
	 * Fonction seuillageControle : seuil une image en noir et blanc, cette methode laisse l'utilisateur de choisi un seuil manuellement
	 * @param bfi
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public BufferedImage seuillageControle(BufferedImage bfi, File path) throws IOException {
		int p = bfi.getRGB(0,0); //récuperer le pixel à coordonné x et y
//		int r = (p>>16)&0xff; 
		int g = (p>>8)&0xff; 
//		int b = p&0xff; 
		boolean quit=false;
		int seuil; //compris entre 0 et 255
//		int seuilMax; //si nessecite un encadrement
		int[] couleurNoir = {0, 0, 0, 255}; //couleur noir
	    int[] couleurBlanc = {255, 255, 255, 255}; //pour donner la couleur blanche
	    
	    BufferedImage imgSeuil = null;
		try {
			imgSeuil = ImageIO.read(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    boolean premierValeur = true; //necessaire pour entrer au moins un premier seuil
	    while(!quit) {
			System.out.println("choisisser un seuil entre 0 et 255 ou une autre valeur pour quitter");
			seuil = Saisie.lireEntier("Valeur de seuil ?");
			if(seuil >= 0 && seuil <= 255) {	
			System.out.println("vous avez choisis : "+seuil);
	
				for(int x=0; x<bfi.getWidth(); x++) { //
					for(int y=0; y<bfi.getHeight(); y++) {
						p = bfi.getRGB(x, y);
						g = (p>>8)&0xff; 
						
						if(g < seuil) {
							imgSeuil.getRaster().setPixel(x, y, couleurBlanc);
						}
						else {
							imgSeuil.getRaster().setPixel(x, y, couleurNoir);
		        			
		        		}
					}
				}
			    Imshow.imshow(imgSeuil);
				premierValeur = false;
			}
			else if (premierValeur) {
				quit = false;
			}
			else {
				quit = true;
			}
		}
	    System.out.println("vous avez quitté voici le résultat de l'image");

	    return imgSeuil;
	}
	
	/**
	 * Fonction fusionImgEtSobel : prend une image initiale bfi en NB et une image en sobel bfj, puis transpose les contours blanc uniquement de bfj à bfi 
	 * @param BufferedImage bfi : image a filtrer
	 * @param BufferedImage bfj : sobel 
	 * @return fusion des deux image avec un parametre particulier
	 */
	public BufferedImage fusionImgEtSobel(BufferedImage bfi, BufferedImage imgSobel, float OtsuSeuil) {
		int widthbfi = bfi.getWidth();
	    int heightbfi = bfi.getHeight();
	    
		int widthbfj = imgSobel.getWidth();
	    int heightbfj = imgSobel.getHeight();
	    
	    if(widthbfi != widthbfj || heightbfi != heightbfj) {
	    	System.err.println("les deux images n'ont pas la même dimension");
	    	
	    }
	    else {
	    	int pj = imgSobel.getRGB(0,0); //récuperer le pixel pour image de sobel
			int rj = (pj>>16)&0xff; 
			
		    int[] couleurBlanc = {255, 255, 255, 255}; //pour donner la couleur blanche
		    
			for(int x = 0;  x < widthbfi; x++) {
		       for(int y = 0; y < heightbfi; y++) {
		    	   pj = imgSobel.getRGB(x, y);
		    	   
		    	   rj = (pj>>16)&0xff;
		    	   
		    	   if(rj >= OtsuSeuil) {
		    		   bfi.getRaster().setPixel(x, y, couleurBlanc);
		    	   }
		       }
			}
			System.out.println("image fusionné");
	    }
	    System.out.println("seuil de supperposition de Sobel sur l'image NB : " + OtsuSeuil);
		return bfi;
	}
}
