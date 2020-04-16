package projet_grp8;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Fichier de test pour tout les méthodes
 * @author willy
 *
 */
public class Main {
	
	public static void main (String[] args) throws IOException  {
		//TODO: input image
		File file = new File("Bdd" + File.separator+"escalier2.jpg"); 
		BufferedImage img = null;
		img = ImageIO.read(file);


		//test
		BufferedImage imgOne = ImgController.gris(img);
		float seuil = Otsu.otsu(img);
		imgOne = ImgController.seuillage(imgOne, seuil);

		imgOne = ImgController.inverseBinary(imgOne);
		Imshow.imshow(imgOne);
		imgOne = MedianFilter.median(imgOne); 
		Imshow.imshow(imgOne);

		

		
//		BufferedImage imgTwo = Sobel.sobel(img);
//		imgTwo = MedianFilter.median(imgTwo); 
//		Imshow.imshow(imgTwo);
//		BufferedImage imgFinal = ImgController.fusionImgEtSobel(imgOne, imgTwo);
//		Imshow.imshow(imgFinal);
		
//		BufferedImage imgMedian = MedianFilter.median(imgFinal);
//		Imshow.imshow(imgMedian);
		
//		BufferedImage imgErode = ErosionDilatation.erode(imgFinal, 7);
//		Imshow.imshow(imgErode);
//		
//		BufferedImage imgSobel = ImgController.fusionImgEtSobel(imgErode, imgTwo);
//		Imshow.imshow(imgSobel);
//		
//		BufferedImage imgDilate = ErosionDilatation.dilate(imgErode, 7);
//		Imshow.imshow(imgDilate);
		
//		
//		try {
//			imgOne = Lancher.testMorphMath(imgOne);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		imgOne = ImageColoring.inverseBinary(imgOne);
		
		//TODO [ok] : methode gris > seuillage > median > inverse NB > sobel + img
//		File file = new File("Bdd" + File.separator+"escalier1.jpg"); 
//		BufferedImage img = null;
//		img = ImageIO.read(file);
//		BufferedImage imgOne = ImgController.gris(img);
//		imgOne = ImgController.seuillageControle(imgOne, file);
//		imgOne = MedianFilter.median(imgOne);
//		Imshow.imshow(imgOne);
//		imgOne = ImgController.inverseBinary(imgOne);
//		Imshow.imshow(imgOne);
//		BufferedImage imgTwo = Sobel.sobel(img);
//		imgTwo = MedianFilter.median(imgTwo); 
//		Imshow.imshow(imgTwo);
//		BufferedImage imgFinal = ImgController.fusionImgEtSobel(imgOne, imgTwo);
//		Imshow.imshow(imgFinal);
		
		//TODO [en cours]: filtre de sobel
//		Sobel.sobel(img);
		
		//TODO [ok]: methode nuace de gris 
//		img = ImgController.gris(img);
	
		//TODO: supression de bruit, amelioration de la qualite des marche 
		// de l'escalier pour faciliter la reconnaissante du seuil 

		//TODO [ok]: methode filtre median de couleur
//		MedianFilter.median(img); 
		
		//TODO [ok]: methode luminosite 
//		int cmd = 0;
//		int nbdefois = 0;
//		do {
//			System.out.println("#### Commande luminosité ###");
//			System.out.println("Augmenter la luminosité : 1");
//			System.out.println("Baisser la luminosité :   2");
//			System.out.println("Quitter :                 3");
//			System.out.println("############################");
//			cmd = Saisie.lireEntier("Votre choix ? ");
//			ImgController.luminosite(cmd, img);
//			nbdefois ++;
//		}while ( cmd!=3);
//		System.out.println("vous avez quitté, il y'a eu "+nbdefois+" modification");
		

		//TODO [ok]: faire saisir les valeurs pour que l'user puisse changer le seuil a sa convenance jusqu'a qu'il quitte
		//TODO [ok]: seuillage pour differencier la marche en noir -> on aura une image en NB
//		BufferedImage imgSeuil = ImgController.seuillageControle(img, path);
      
		//TODO [ok]:histogram par ligne et par colonne
//        Image ima2 = new Image(imgSeuil);
//        ima2.histoX();
//        ima2.histoY();
		
		//TODO: a ce stade il faut que les "marches" de l'escalier seulement soit en noir
		
		//TODO: connexite pour compter le nombre d'objet (marche) noir sur l'image
		
		//TODO: affichage du resultat
	}
}
