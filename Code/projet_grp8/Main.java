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
		File file = new File("Bdd" + File.separator+"escalier21.jpg"); 
		BufferedImage img = null;
	
		// creation de class pour utiliser les methodes disponible
		ImgController ic = new ImgController();
		MedianFilter mf = new MedianFilter();
		Otsu o = new Otsu();
		Sobel sb = new Sobel();
		ErosionDilatation ed = new ErosionDilatation();
		
		img = ImageIO.read(file);
		

		//test : gris > otsu > seuil > inverseColor > median > fusion sobel > median + dilate
		BufferedImage imgOne = ic.gris(img);

		//utile pour l'image sans bruit
		float seuil = o.otsu(img);
		System.out.println("otsu seuil : "+seuil);
		imgOne = ic.seuillage(img, seuil); //image en NB avec un seuil auto grace a otsu()
		
		imgOne = ic.inverseBinary(imgOne); //les marche vont etre en noir
		BufferedImage bj = mf.median(imgOne);  //supprime les bruits avec median
		imgOne = mf.median(bj); //supprime une deuxieme fois les bruits avec median

		//utile pour enlever les bruit sur l'image initiale
//		bj = mf.median(bj);		bj = mf.median(bj);		bj = mf.median(bj);		bj = mf.median(bj);		bj = mf.median(bj);		bj = mf.median(bj);

		
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
		
		BufferedImage imgSobel = sb.sobel(img); // application de sobel sur l'image originalXj
		//augmente la taille pour appliquer le filtre median 5x5
		bj = ic.inverseBinary(bj);
		bj = mf.createBorder(bj);
		bj = ic.inverseBinary(bj);
		BufferedImage imgSobel5 = mf.median5(imgSobel); //suppression de bruit sur sobel lvl.5
		
		BufferedImage imgFinal = ic.fusionImgEtSobel(bj, imgSobel5); //applique les contours de sobel sur l'image bruite
		//avec [55] de seuil resultat correcte
		imgFinal=mf.median(imgFinal); //supression de bruit
		imgFinal=mf.median(imgFinal);
		imgFinal=mf.median(imgFinal);
		//avec double application mediant, meilleur resultat


		
		//on realise une ouverture
		//dilatation 
		imgFinal=ed.dilate(imgFinal, 6);
		//erode
//		imgFinal=ed.erode(imgFinal, 3);
//		Imshow.imshow(imgFinal);
		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);		imgFinal = mf.median(imgFinal);
		Imshow.imshow(imgFinal);
		System.out.println("fin du program");
		
		//test avec connexite
		
		Connexite co = new Connexite();
		imgFinal = co.connexite(imgFinal);
		int nbColora = Label8.getNumberOfCC(imgFinal);
		System.err.println("taille de haut en bas :"+imgFinal.getHeight());
		Imshow.imshow(imgFinal);
		System.out.println("nombre de marche avec connexité: "+(nbColora-1));
		
		//test qui fonctionne ne pas toucher 
//		imgFinal = ic.inverseBinary(imgFinal);
//		BufferedImage cc = Label8.getCC(imgFinal);
//		Imshow.imshow(cc);
//		int nbColor = Label8.getNumberOfCC(cc);
//		System.out.println("nombre de marche avec label8 : "+(nbColor-1));

		
		
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
