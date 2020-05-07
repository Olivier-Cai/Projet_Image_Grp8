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

		File file = new File("Bdd" + File.separator+"escalier212.jpg"); //escalier212.jpg, marche couleur sombre
		File file1 = new File("Bdd" + File.separator+"8.jpg"); //8.jpg, marche pas car trop de contraste B&N
		File file2 = new File("Bdd" + File.separator+"escalierPerso.jpg"); //escalierPerso.jpg, marche de couleur sombre
		File file3 = new File("Bdd" + File.separator+"imag5.jpg"); //imag5.jpg, marche pas car la marche est de couleur clair
		File file4 = new File("Bdd" + File.separator+"eescalier.jpg"); //eescalier.jpg, marche pas car l'ombre a cote est colorie
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
//			Imshow.imshow(img);
			//Imshow.imshow(img);
		} catch (IOException e1) {
			System.err.println("Erreur de lecture de fichier");
		}		
//		Imshow.imshow(img);
		
		//test 1.0 : gris > otsu > seuil > median
		BufferedImage img1 = ic.gris(img);
//		Imshow.imshow(img1);
		BufferedImage bj = mf.median(img1);
//		Imshow.imshow(bj);
		float seuil = o.otsu(img);
		System.out.println("otsu seuil : "+seuil);
		float seuila = seuil-25;
		img1 = ic.seuillage(img, seuila); //image en NB avec un seuil auto grace a otsu
		bj = mf.median(img1);  //supprime les bruits avec median 3
//		Imshow.imshow(bj);
		bj = ed.close(bj, 3); // rayon de n-pixels
//		Imshow.imshow(bj);
		BufferedImage bjMedian = mf.median(bj);
		
		//test 1.1 : sobel > border > median > fusion
		BufferedImage imgSobel = sb.sobel(img); // application de sobel sur l'image original
//		Imshow.imshow(imgSobel);
		//augmente la taille pour appliquer le filtre median 5x5
//		Imshow.imshow(imgSobel);
		imgSobel = mf.median(imgSobel);
		Imshow.imshow(imgSobel);
		float seuilb = seuil;
		
		
		seuilb -= (seuil*0.65);
		System.out.println("seuilb vaut "+seuilb+".");
		
		imgSobel = ic.seuillage(imgSobel, seuilb);
		Imshow.imshow(imgSobel);
		BufferedImage imgFinal = ic.fusionImgEtSobel(bjMedian, imgSobel, (int)seuil-20); //applique les contours de sobel sur l'image bruite
		imgFinal = mf.median(imgFinal);

//		Imshow.imshow(imgFinal);
		
//		BufferedImage imgSobelSeuil = eg.egalisation(imgSobel);
//		Imshow.imshow(imgSobelSeuil);
//		BufferedImage imgFinal = ic.seuillage(imgSobelSeuil, 200);

	
//		BufferedImage imgFinal = ic.fusionImgEtSobel(bj, imgSobel5, seuil-10); //applique les contours de sobel sur l'image bruite




		/**
		 * TODO generique [ok] : test qui fonctionne ne pas toucher, 
		 * connexite pour compter le nombre d'objet (marche) noir sur l'image
		 * 
		 * affichage du resultat
		 */
		imgFinal = ic.inverseBinary(imgFinal); //change les marches "Noir" en "Blanc"
		BufferedImage cc = Label8.getCC(imgFinal);
		Imshow.imshow(cc);
		int nbColor = Label8.getNumberOfCC(cc);
		System.out.println("nombre de marche avec label8 : "+(nbColor-1)); //-1 car on ne va pas prendre en compte la couleur noire
	}
}