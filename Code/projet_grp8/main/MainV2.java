package projet_grp8.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import projet_grp8.methode.*;
import projet_grp8.util.*;

public class MainV2 {
	public static void main (String[] args) throws IOException  {
		//		File file = new File("Bdd" + File.separator+"escalierPerso.jpg"); 
		//		File file = new File("Bdd" + File.separator+"escalier_11.jpg"); 
		//		File file = new File("Bdd" + File.separator+"escalier_12.jpg"); 
		File file = new File("Bdd" + File.separator+"escalier21.jpg"); 
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

		// Image originale
		img = ImageIO.read(file);
		Imshow.imshow(img);
		System.out.println("1) AFFICHAGE IMAGE");

		// Amelioration de l'image 
		BufferedImage img1 = eg.egalisation(img);
		Imshow.imshow(img);
		System.out.println("2) Affichage IMAGE");

		img1 = mf.median(img1); 
		img1 = mf.median(img1); 
		Imshow.imshow(img1);
		System.out.println("3) AFFICHAGE IMAGE");

		// Utilisation d'Otsu pour le seuillage et obtenir une image en noir et blanc
		float seuil = o.otsu(img1);
		System.out.println("Otsu seuil : "+(int) (seuil-25));

		img1 = ic.seuillage(img1, (int) (seuil-25)); 
		Imshow.imshow(img1);
		System.out.println("4) AFFICHAGE IMAGE");

		// Suppression des bruits et amelioration des détails 
		img1 = mf.median(img1); 
		img1 = mf.median(img1); 
		img1 = mf.median(img1);
		Imshow.imshow(img1);
		System.out.println("5) AFFICHAGE IMAGE");

		img1 = ed.dilate(img1, 3);
		Imshow.imshow(img1);
		System.out.println("6) AFFICHAGE IMAGE");

		img1 = ed.erode(img1, 3);
		img1 = ed.erode(img1, 3);
		Imshow.imshow(img1);
		System.out.println("7) AFFICHAGE IMAGE");

		img1 = mf.median(img1); 
		img1 = mf.median(img1); 
		Imshow.imshow(img1);
		System.out.println("8) AFFICHAGE IMAGE");

		/**
		 * Methode ok : pour colorier les formes/objets et les compter 
		 * Utilisation de Label8 pour compter le nombre d'objet (marche) noir sur l'image
		 * Puis affichage du resultat
		 */
		img1 = ic.inverseBinary(img1);
		BufferedImage cc = Label8.getCC(img1);
		Imshow.imshow(cc);
		System.out.println("9) AFFICHAGE IMAGE");

		int nbColor = Label8.getNumberOfCC(cc);
		System.out.println("nombre de marche avec label8 : "+(nbColor));
	}
}