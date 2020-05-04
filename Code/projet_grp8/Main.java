package projet_grp8;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import projet_grp8.ImgController;
import projet_grp8.MedianFilter;
import projet_grp8.Otsu;
import projet_grp8.Sobel;
import projet_grp8.ErosionDilatation;
import projet_grp8.Imshow;

/**
 * Fichier de test pour tout les m�thodes
 * @author willy
 *
 */
public class Main {
	
	public static void main (String[] args) throws IOException  {
		//TODO: input image
		File file = new File("Bdd" + File.separator+"escalier21.jpg"); 
		BufferedImage img = null;
		
		// creation de class pour utiliser les methodes disponible
		ImgController ic = new ImgController();
		MedianFilter mf = new MedianFilter();
		Otsu o = new Otsu();
		Sobel sb = new Sobel();
//		ErosionDilatation ed = new ErosionDilatation();

		try {
			img = ImageIO.read(file);
			//Imshow.imshow(img);
		} catch (IOException e1) {
			System.out.println("Erreur de lecture de fichier");
		}		
		
		//test 1.0 : gris > otsu > seuil > median
		BufferedImage img1 = ic.gris(img);
		//utile pour l'image sans bruit
		float seuil = o.otsu(img);
		System.out.println("otsu seuil : "+seuil);
		img1 = ic.seuillage(img, seuil); //image en NB avec un seuil auto grace a otsu
		BufferedImage bj = mf.median(img1);  //supprime les bruits avec median 3
		//Imshow.imshow(bj);
		
		
		//test 1.1 : sobel > border > median > fusion
		BufferedImage imgSobel = sb.sobel(img); // application de sobel sur l'image original

		//augmente la taille pour appliquer le filtre median 5x5
		bj = mf.createBlackBorder(bj); //ajuste la taille de l'image
		//Imshow.imshow(bj);
		BufferedImage imgSobel5 = mf.median5(imgSobel); //suppression de bruit sur sobel lvl.5
		BufferedImage imgSobelbis = mf.median(imgSobel);
		//Imshow.imshow(imgSobelbis);
		BufferedImage imgFinal = ic.fusionImgEtSobel(bj, imgSobel5, seuil); //applique les contours de sobel sur l'image bruite
		//avec [55] de seuil resultat correcte
		imgFinal=mf.median(imgFinal); //supression de bruit
		imgFinal=mf.median(imgFinal);
		imgFinal=mf.median(imgFinal);
		//avec plusieurs application mediant, meilleur resultat
		Imshow.imshow(imgFinal);
		
		//on realise une ouverture
		//dilatation 
//		Imshow.imshow(imgFinal);
		imgFinal= ErosionDilatation.dilate(imgFinal, 6);
		//erode
//		imgFinal=ed.erode(imgFinal, 3);
		Imshow.imshow(imgFinal);
		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);

		System.out.println("fin du program");
		


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
