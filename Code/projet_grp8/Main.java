package projet_grp8;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class Main {
	public static void imshow(BufferedImage image) throws IOException {
	      //Instantiate JFrame 
	      JFrame frame = new JFrame(); 

	      //Set Content to the JFrame 
	      frame.getContentPane().add(new JLabel(new ImageIcon(image))); 
	      frame.pack(); 
	      frame.setVisible(true);
	 }
	
	public static void main (String[] args) throws IOException  {
		//TODO: input image
		File path = new File("/Bdd/model1.jpg");
		BufferedImage img = null;
		img = ImageIO.read(path);
		//TODO: fonction nuace gris
		

		int width = img.getWidth();
		int height = img.getHeight();
		
		int p = img.getRGB(0,0); //récuperer le pixel à coordonné x et y
		int r = (p>>16)&0xff; 
		int g = (p>>8)&0xff; 
		int b = p&0xff; 


		//TODO [OK]: methode nuace de gris 
		img = ImageColoring.gris(img);
		
	
		//TODO: supression de bruit, amelioration de la qualite des marche 
		// de l'escalier pour faciliter la reconnaissante du seuil 

		//TODO [OK]: methode filtre median de couleur
		MedianFilter.median(img); 
		
		//TODO [ok]: methode luminosite 
		Image ima = new Image(img);
		int cmd = 0;
		int nbdefois = 0;
		do {
			System.out.println("#### Commande luminosité ###");
			System.out.println("Augmenter la luminosité : 1");
			System.out.println("Baisser la luminosité :   2");
			System.out.println("Quitter :                 3");
			System.out.println("############################");
			cmd = Saisie.lireEntier("Votre choix ? ");
			ImageColoring.brightness(cmd, img);
			nbdefois ++;
		}while ( cmd!=3);
		System.out.println("vous avez quitté, il y'a eu "+nbdefois+" modification");
		

		//TODO [ok]: faire saisir les valeurs pour que l'user puisse changer le seuil a sa convenance jusqu'a qu'il quitte
		//TODO [ok]: seuillage pour differencier la marche en noir -> on aura une image en NB
	boolean quit=false;
	int seuil; //compris entre 0 et 255
	int seuilMax; //si nessecite un encadrement
	int[] couleurNoir = {0, 0, 0, 255}; //couleur noir
    int[] couleurBlanc = {255, 255, 255, 255}; //pour donner la couleur blanche
    BufferedImage imgSeuil = ImageIO.read(path);
    boolean premierValeur = true; //necessaire pour entrer au moins un premier seuil
    while(!quit) {
		System.out.println("choisisser un seuil entre 0 et 255 ou une autre valeur pour quitter");
		seuil = Saisie.lireEntier("Valeur de seuil ?");
		if(seuil >= 0 && seuil <= 255) {	
		System.out.println("vous avez choisis : "+seuil);

			for(int x=0; x<img.getWidth(); x++) { //
				for(int y=0; y<img.getHeight(); y++) {
					p = img.getRGB(x, y);
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
			imshow(imgSeuil);
		}
		else if (premierValeur) {
			quit = false;
		}
		else {
			quit = true;
		}
	}
    System.out.println("vous avez quitté voici le résultat de l'image");
      
		//TODO [ok]:histogram par ligne et par colonne
        Image ima2 = new Image(imgSeuil);
        ima2.histoX();
        ima2.histoY();
		
		//TODO; a ce stade il faut que les "marches" de l'escalier seulement soit en noir
		
		//TODO: connexite pour compter le nombre d'objet (marche) noir sur l'image
		
		//TODO: affichage du resultat
	}
}
