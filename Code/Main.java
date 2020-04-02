package code.java;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import code.java.*;

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
		File path = new File("C:\\Users\\willy\\Documents\\Licence_maths_info\\S6\\Image\\projetImage\\model1.jpg");
		BufferedImage img = null;
		img = ImageIO.read(path);
		//TODO: fonction nuace gris
		

		int width = img.getWidth();
		int height = img.getHeight();
		
		int p = img.getRGB(0,0); //récuperer le pixel à coordonné x et y
		int r = (p>>16)&0xff; 
		int g = (p>>8)&0xff; 
		int b = p&0xff; 


		//methode nuace de gris [OK]
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
//		imshow(img);
		
	
		//TODO: supression de bruit, amelioration de la qualite des marche 
		// de l'escalier pour faciliter la reconnaissante du seuil 
		
		//TODO: laisser l'utilisateur ecrire le seuil au fur et a mesure jusqu'a qu'il soit satisfait
		//TODO: faire saisir les valeurs pour que l'user puisse changer le seuil a sa convenance jusqu'a qu'il quitte
		//TODO: seuillage pour differencier la marche en noir -> on aura une image en NB
		boolean quit=false;
		int seuil; //compris entre 0 et 255
		int seuilMax; //si nessecite un encadrement
		int[] couleurNoir = {0, 0, 0, 255}; //couleur noir
        int[] couleurBlanc = {255, 255, 255, 255}; //pour donner la couleur blanche
        BufferedImage imgSeuil = ImageIO.read(path);
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
				imshow(imgSeuil);
			}
			else {
				quit = true;
			}
		}
        System.out.println("vous avez quitté voici le résultat de l'image");
        
        
		//TODO; a ce stade il faut que les "marches" de l'escalier seulement soit en noir
		
		//TODO: connexite pour compter le nombre d'objet (marche) noir sur l'image
		
		//TODO: affichage du resultat
	}
}
