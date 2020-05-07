package projet_grp8.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import projet_grp8.methode.*;
import projet_grp8.util.*;
/**
 * Fichier de test pour tout trouver le nombre d'escalier avec differente image
 * @author willy
 *
 */
public class Test1 {
	
	public static void main (String[] args) throws IOException  {

		File file = new File("Bdd" + File.separator+"escalier21.jpg"); //esclalier noire
		File file1 = new File("Bdd" + File.separator+"escalier_11.jpg"); //escalier du prof
		File file2 = new File("Bdd" + File.separator+"8.jpg"); //escalier du prof
		BufferedImage img = null;
		
		// creation de class pour utiliser les methodes disponible
		ImgController ic = new ImgController();
		MedianFilter mf = new MedianFilter();
		Otsu o = new Otsu();
		Sobel sb = new Sobel();
		ErosionDilatation ed = new ErosionDilatation();
		Egalisation eg = new Egalisation();
		
		
		try {
			img = ImageIO.read(file2);
			//Imshow.imshow(img);
		} catch (IOException e1) {
			System.err.println("Erreur de lecture de fichier");
		}		
		
		//test 1.0 : gris > otsu > seuil > median
		BufferedImage img1 = ic.gris(img);
//		Imshow.imshow(img1);
		BufferedImage bj = mf.median(img1);
//		Imshow.imshow(bj);
		float seuil = o.otsu(img);
		System.out.println("otsu seuil : "+seuil);
		img1 = ic.seuillage(img, seuil); //image en NB avec un seuil auto grace a otsu
		bj = mf.median(img1);  //supprime les bruits avec median 3
//		Imshow.imshow(bj);
		
		
		//test 1.1 : sobel > border > median > fusion
		BufferedImage imgSobel = sb.sobel(img); // application de sobel sur l'image original
//		Imshow.imshow(imgSobel);
		//augmente la taille pour appliquer le filtre median 5x5
		bj = ed.dilate(bj, 5); //dilatation du blanc rayon de 2pix
		bj=mf.median(bj);

//		Imshow.imshow(bj);
		Imshow.imshow(bj);//TODO
		BufferedImage imgFinal = ic.fusionImgEtSobel(bj, imgSobel, (int)seuil-10); //applique les contours de sobel sur l'image bruite

		Imshow.imshow(imgFinal);
		
//		BufferedImage imgSobelSeuil = eg.egalisation(imgSobel);
//		Imshow.imshow(imgSobelSeuil);
//		BufferedImage imgFinal = ic.seuillage(imgSobelSeuil, 200);

	
//		BufferedImage imgFinal = ic.fusionImgEtSobel(bj, imgSobel5, seuil-10); //applique les contours de sobel sur l'image bruite

		
		
//		//on realise une ouverture
//		//dilatation 
////		Imshow.imshow(imgFinal);
//		imgFinal= ErosionDilatation.dilate(imgFinal, 6);
//		//erode
////		imgFinal=ed.erode(imgFinal, 3);
//		Imshow.imshow(imgFinal);
//		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);
//
//		System.out.println("fin du program");
//		
//
//
//		/**
//		 * TODO generique [ok] : test qui fonctionne ne pas toucher, 
//		 * connexite pour compter le nombre d'objet (marche) noir sur l'image
//		 * 
//		 * affichage du resultat
//		 */
//		imgFinal = ic.inverseBinary(imgFinal);
//		BufferedImage cc = Label8.getCC(imgFinal);
//		Imshow.imshow(cc);
//		int nbColor = Label8.getNumberOfCC(cc);
//		System.out.println("nombre de marche avec label8 : "+(nbColor-1));
	}
}