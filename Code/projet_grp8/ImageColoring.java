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
public class ImageColoring {

	/**
	 * tranforme l'image de couleur en nuance de gris
	 * @param img
	 * @return
	 * @throws IOException 
	 */
	public static BufferedImage imGrey (BufferedImage img) throws IOException {
		//TODO: je ne sais pas si ca marche 
		System.out.println("image en nuace de gris");
		int tab[] = new int[256];
		for (int i=0; i<tab.length; i++ ) {
			tab[i]=0;
		}		
		int p = img.getRGB(0,0); //récuperer le pixel à coordonné x et y
		int g = (p>>8)&0xff; 
		for(int x=0; x<img.getWidth(); x++) { //recherche de chaque nuance de gris
			for(int y=0; y<img.getHeight(); y++) {
				p = img.getRGB(x, y);
				g = (p>>8)&0xff; 
				//        		System.out.println("valeur de g "+g);
				tab[g]= tab[g]+1;
			}
		}
		Imshow.imshow(img);
		return img;
	}
	
	
	/**
	 * tranforme l'image de couleur en nuance de gris
	 * @param img
	 * @return
	 * @throws IOException 
	 */
	public static BufferedImage gris(BufferedImage img) throws IOException {
		int p = img.getRGB(0,0); //récuperer le pixel à coordonné x et y
		int g = (p>>8)&0xff; 
		
		for(int x=0; x<img.getWidth(); x++) { //
			for(int y=0; y<img.getHeight(); y++) {
				p = img.getRGB(x, y);
				g = (p>>8)&0xff; 
				
				//assombrir une image "-", eclaircir "+"
				if((g-=50) <= 0 ) {
					g=0;
				}
				int[] gris = {g, g, g, 255};
				img.getRaster().setPixel(x, y, gris);
			}
		}
		Imshow.imshow(img);
		return img;
	}
	/**
	 * change la luminosité
	 * @param cmd
	 */
	public static BufferedImage brightness(int cmd, BufferedImage bfi) {
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
		try {
			Imshow.imshow(bfi);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		try {
			Imshow.imshow(bfi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bfi;
	}
	/**
	 * cette methode evite de faire dapasser les valeur min et max car cela peut cree un pb
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
	
	public static BufferedImage seuillageControle(BufferedImage bfi, File path) {
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
							imgSeuil.getRaster().setPixel(x, y, couleurNoir);
						}
						else {
							imgSeuil.getRaster().setPixel(x, y, couleurBlanc);
		        			
		        		}
					}
				}
				premierValeur = false;
				try {
					Imshow.imshow(imgSeuil);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
}
