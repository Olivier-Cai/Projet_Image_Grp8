package projet_grp8;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import projet_grp8.methode.Egalisation;
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
		File file = new File("Bdd" + File.separator+"escalier31.jpg"); 
//		File file = new File("Bdd" + File.separator+"escalier_11.jpg"); 
//		File file = new File("Bdd" + File.separator+"escalier_12.jpg"); 
//		File file = new File("Bdd" + File.separator+"escalier21.jpg"); 
//		File file = new File("Bdd" + File.separator+"escalier212.jpg"); 
//		File file = new File("Bdd" + File.separator+"escalier1.jpg"); 
//		File file = new File("Bdd" + File.separator+"escalier31.jpg"); 
//		File file = new File("Bdd" + File.separator+"escalierPerso.jpg"); 
//		File file = new File("Bdd" + File.separator+"model1.jpg"); 
//		File file = new File("Bdd" + File.separator+"Imag5.jpg"); 
		BufferedImage img = null;
		
		// Creation des objets classes pour utiliser les methodes 
		ImgController ic = new ImgController();
		MedianFilter mf = new MedianFilter();
		Otsu o = new Otsu();
		Sobel sb = new Sobel();
		ErosionDilatation ed = new ErosionDilatation();
		Egalisation eg = new Egalisation();
		
		img = ImageIO.read(file);
		Imshow.imshow(img);
		System.out.println("1) AFFICHAGE IMAGE");
//		img = eg.egalisation(img);
//		Imshow.imshow(img);
		
//		img = ic.gris(img);
//		Imshow.imshow(img);
//		System.out.println("5) Affichage image Sobel");
		
		BufferedImage img1 = eg.egalisation(img);
		Imshow.imshow(img);
		System.out.println("5) Affichage image Sobel");
//		img1 = ic.inverseBinary(img1);
		// Test : gris > otsu > seuil > median
		// img1 = ic.gris(img1);
//		Imshow.imshow(img1);
//		System.out.println("2) Affichage image gris");
		
		img1 = mf.median(img1); 
		img1 = mf.median(img1); 
		Imshow.imshow(img1);
		System.out.println("4) AFFICHAGE IMAGE");
		
		// Utile pour l'image sans bruit
		float seuil = o.otsu(img1);
		System.out.println("Otsu seuil : "+(int) (seuil-25));
		// Image en NB avec un seuillage auto grace a Otsu
		img1 = ic.seuillage(img1, (int) (seuil-25)); 
		Imshow.imshow(img1);
		System.out.println("2) AFFICHAGE IMAGE");
		
		img1 = mf.median(img1); 
		img1 = mf.median(img1); 
		img1 = mf.median(img1);
		Imshow.imshow(img1);
		System.out.println("4) AFFICHAGE IMAGE");
		
		img1 = ed.dilate(img1, 3);
		Imshow.imshow(img1);
		System.out.println("3) AFFICHAGE IMAGE");
		
		//Colorie pour une image noire
		img1 = ed.erode(img1, 3);
		img1 = ed.erode(img1, 3);
		Imshow.imshow(img1);
		System.out.println("3) AFFICHAGE IMAGE");
		
//		img1 = ed.dilate(img1, 6);
//		Imshow.imshow(img1);
//		System.out.println("3) AFFICHAGE IMAGE");
		
		// Supprime les bruits (petit détails en rendant plus flou) avec median
		img1 = mf.median(img1); 
		img1 = mf.median(img1); 
		Imshow.imshow(img1);
		System.out.println("4) AFFICHAGE IMAGE");
		
//		//test 1.1 : sobel > border > median > fusion
//		// Contour 
//		img1 = sb.sobel(img1); 
//		Imshow.imshow(img1);
//		System.out.println("5) AFFICHAGE IMAGE");
//		//augmente la taille pour appliquer le filtre median 5x5
		
//		img = ic.inverseBinary(img);
//		img1 = mf.createBlackBorder(img1);
//		Imshow.imshow(img1);
//		System.out.println("6) AFFICHAGE IMAGE");
		
////		img = ic.inverseBinary(img);
//		// Suppression de bruit sur sobel lvl.5 NE PLUS UTILISER
//		// BufferedImage imgSobel5 = mf.median5(Image); 
//		BufferedImage imgSobelbis = mf.median(img);
//
//		BufferedImage imgFinal = ic.fusionImgEtSobel(img1, img, (int) (seuil-25)); //applique les contours de sobel sur l'image bruite
//		Imshow.imshow(imgFinal);
//		System.out.println("8) AFFICHAGE IMAGE FINAL");
//		
//		// avec [55] de seuil resultat correcte //supression de bruit
////		imgFinal=mf.median(imgFinal); 
////		imgFinal=mf.median(imgFinal);
//		// avec double application mediant, meilleur resultat
//
//		imgFinal= ed.dilate(imgFinal, 3);
////		
//////		erode
//////		imgFinal=ed.erode(imgFinal, 3);
//////		Imshow.imshow(imgFinal);
//////		imgFinal = mf.median(imgFinal);		
//////		imgFinal = mf.median(imgFinal);	
////		Imshow.imshow(imgFinal);
////		System.out.println("Fin du programme");
////	
////		//Colorie pour une image noire
////		imgFinal = ed.erode(imgFinal, 3);
//		imgFinal = ed.erode(imgFinal, 3);
//		imgFinal = ed.erode(imgFinal, 2);
//		Imshow.imshow(imgFinal);
//		System.out.println("3) AFFICHAGE IMAGE");
//
//		//TODO [ok] : methode gris > seuillage > median > inverse NB > sobel + img
////		File file = new File("Bdd" + File.separator+"escalier1.jpg"); 
////		BufferedImage img = null;
////		img = ImageIO.read(file);
////		BufferedImage imgOne = ImgController.gris(img);
////		imgOne = ImgController.seuillageControle(imgOne, file);
////		imgOne = MedianFilter.median(imgOne);
////		Imshow.imshow(imgOne);
////		imgOne = ImgController.inverseBinary(imgOne);
////		Imshow.imshow(imgOne);
////		BufferedImage imgTwo = Sobel.sobel(img);
////		imgTwo = MedianFilter.median(imgTwo); 
////		Imshow.imshow(imgTwo);
////		BufferedImage imgFinal = ImgController.fusionImgEtSobel(imgOne, imgTwo);
////		Imshow.imshow(imgFinal);
	
		/**
		 * TODO generique [ok] : test qui fonctionne ne pas toucher, 
		 * connexite pour compter le nombre d'objet (marche) noir sur l'image
		 * 
		 * affichage du resultat
		 */
		img1 = ic.inverseBinary(img1);
		BufferedImage cc = Label8.getCC(img1);
		Imshow.imshow(cc);
		System.out.println("9) AFFICHAGE IMAGE");
		
		int nbColor = Label8.getNumberOfCC(cc);
		System.out.println("nombre de marche avec label8 : "+(nbColor));
	}
}
