package projet_grp8;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import projet_grp8.methode.ErosionDilatation;
import projet_grp8.methode.ImgController;
import projet_grp8.methode.Label8;
import projet_grp8.methode.MedianFilter;
import projet_grp8.methode.Otsu;
import projet_grp8.methode.Sobel;
import projet_grp8.util.Imshow;
public class LancherWBis {
	
	public static void main (String[] args) throws IOException  {
		// Choix de l'image
		File file = new File("Bdd" + File.separator+"8.jpg"); 
		BufferedImage img = null;
		
		// Creation des objets classes pour utiliser les methodes 
		ImgController ic = new ImgController();
		MedianFilter mf = new MedianFilter();
		Otsu o = new Otsu();
		Sobel sb = new Sobel();
		ErosionDilatation ed = new ErosionDilatation();
		
		img = ImageIO.read(file);
		Imshow.imshow(img);
		System.out.println("1) Affichage image réelle");
		
		// Test : gris > otsu > seuil > median
		BufferedImage img1 = ic.gris(img);
		Imshow.imshow(img1);
		System.out.println("2) Affichage image gris");
		
		// Utile pour l'image sans bruit
		float seuil = o.otsu(img1);
		System.out.println("Otsu seuil : "+seuil);
		// Image en NB avec un seuillage auto grace a Otsu
		img1 = ic.seuillage(img1, seuil); 
		Imshow.imshow(img1);
		System.out.println("3) Affichage image seuillage avec Otsu");
		
		// Supprime les bruits (petit détails en rendant plus flou) avec median
		BufferedImage bj = mf.median(img1);  
		Imshow.imshow(bj);
		System.out.println("4) Affichage image avec médian");
		
		//TODO : image fond noir avec trait blanc mais pas complet
//		BufferedImage imgSobel = sb.sobel(img); // application de sobel sur l'image original
//		BufferedImage imgSobel2 = mf.median(imgSobel); //suppression de bruit sur sobel lvl.3 (taille 3x3)
//		Imshow.imshow(imgSobel2);
//		
//		//TODO : trouver un moyen d'accentuer les pixels clairs
//		float seuilSobel = o.otsu(imgSobel);
//		BufferedImage imgFinal = ic.seuillage(imgSobel2, seuilSobel);
//		imgFinal = ic.inverseBinary(imgFinal);
//		Imshow.imshow(imgFinal);
		
		//test 1.1 : sobel > border > median > fusion
		// Contour 
		BufferedImage imgSobel = sb.sobel(img); 
		Imshow.imshow(imgSobel);
		System.out.println("5) Affichage image Sobel");
		//augmente la taille pour appliquer le filtre median 5x5
		bj = ic.inverseBinary(bj);
		bj = mf.createBlackBorder(bj);
		bj = ic.inverseBinary(bj);
		BufferedImage imgSobel5 = mf.median5(imgSobel); //suppression de bruit sur sobel lvl.5
		BufferedImage imgSobelbis = mf.median(imgSobel);
		Imshow.imshow(imgSobelbis);
		BufferedImage imgFinal = ic.fusionImgEtSobel(bj, imgSobel5, seuil); //applique les contours de sobel sur l'image bruite
		//avec [55] de seuil resultat correcte
		imgFinal=mf.median(imgFinal); //supression de bruit
		imgFinal=mf.median(imgFinal);
		imgFinal=mf.median(imgFinal);
		//avec double application mediant, meilleur resultat


		
		//on realise une ouverture
		//dilatation 
//		Imshow.imshow(imgFinal);
		imgFinal= ErosionDilatation.dilate(imgFinal, 6);
//		erode
//		imgFinal=ed.erode(imgFinal, 3);
//		Imshow.imshow(imgFinal);
		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);

		System.out.println("Fin du programme");
		
		
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
		

      
		//TODO [ok]:histogram par ligne et par colonne
//        Image ima2 = new Image(imgSeuil);
//        ima2.histoX();
//        ima2.histoY();
		
		/**
		 * TODO generique [ok] : test qui fonctionne ne pas toucher, 
		 * connexite pour compter le nombre d'objet (marche) noir sur l'image
		 * 
		 * affichage du resultat
		 */
		imgFinal = ic.inverseBinary(imgFinal);
		BufferedImage cc = Label8.getCC(imgFinal);
		Imshow.imshow(cc);
		int nbColor = Label8.getNumberOfCC(cc);
		System.out.println("nombre de marche avec label8 : "+(nbColor-1));
	}
}
