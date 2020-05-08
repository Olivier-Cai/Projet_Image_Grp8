package projet_grp8.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import projet_grp8.methode.*;
import projet_grp8.util.*;

/**
 * Class de test pour tout les méthodes
 * @author willy
 *
 */
public class Main {
	
	public static void main (String[] args) throws IOException  {
		//TODO: input image
		File file = new File("Bdd" + File.separator+"escalier212.jpg"); 
		File file1 = new File("Bdd" + File.separator+"EscalierLeo.jpg"); 
		BufferedImage img = null;
		
		// creation de class pour utiliser les methodes disponible
		ImgController ic = new ImgController();
		MedianFilter mf = new MedianFilter();
		Otsu o = new Otsu();
		Sobel sb = new Sobel();
		ErosionDilatation ed = new ErosionDilatation();

		try {
			img = ImageIO.read(file);
			//Imshow.imshow(img);
		} catch (IOException e1) {
			System.out.println("Erreur de lecture de fichier");
		}		
		Imshow.imshow(img);
		//test 1.0 : gris > otsu > seuil > median
		BufferedImage img1 = ic.gris(img);
		img1 = mf.median(img1);
		//utile pour l'image sans bruit
		float seuil = o.otsu(img);
		System.out.println("otsu seuil : "+seuil);
		img1 = ic.seuillage(img, seuil); //image en NB avec un seuil auto grace a otsu
		BufferedImage bj = mf.median(img1);  //supprime les bruits avec median 3
		Imshow.imshow(bj);
		
		//test 1.1 : sobel > border > median > fusion
		BufferedImage imgSobel = sb.sobel(img); // application de sobel sur l'image original
		//augmente la taille pour appliquer le filtre median 5x5
		bj = mf.createBlackBorder(bj); //ajuste la taille de l'image
		//Imshow.imshow(bj);
		BufferedImage imgSobel5 = mf.median5(imgSobel); //suppression de bruit sur sobel lvl.5
		BufferedImage imgFinal = ic.fusionImgEtSobel(bj, imgSobel5, seuil); //applique les contours de sobel sur l'image bruite


		Imshow.imshow(imgFinal);
		imgFinal=mf.median(imgFinal); //supression de bruit
		imgFinal=mf.median(imgFinal);
		imgFinal=mf.median(imgFinal);
		//avec plusieurs application mediant, meilleur resultat
		Imshow.imshow(imgFinal);
		
		//on realise une ouverture
		//dilatation 
//		Imshow.imshow(imgFinal);
		imgFinal= ed.erode(imgFinal, 3);
		//erode
//		imgFinal=ed.erode(imgFinal, 3);
		
		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);

		


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
		System.out.println("nombre de marche avec label8 : "+nbColor);
	}
}
