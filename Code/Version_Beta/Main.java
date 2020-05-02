package Version_Beta;

import Version_Beta.ImageTest;
import Version_Beta.Histogramme;
import java.awt.image.BufferedImage;
import java.io.IOException;
import Version_Beta.Sobel;
import projet_grp8.Connexite;
import projet_grp8.Imshow;

public class Main {

    public static void main (String [] args) {

        try {
            BufferedImage bf = ImageTest.chargerImage("/Users/leosok/Downloads/IMG_3530.JPG");
            
            
           //ImageTest.seuillage(bf, 192);
           
           //ImageTest.egalisation(bf);
            
           ImageTest.median(bf);
           ImageTest.imageBin(bf);
           ImageTest.sobel(bf);
           
           
           //ImageTest.connexite(bf);
           //ImageTest.toNegatif(bf);
        
       
   		int nbColora = Label8.getNumberOfCC(bf);
   		//System.err.println("taille de haut en bas :"+bf.getHeight());
   		Imshow.imshow(bf);
   		System.out.println("nombre de marche avec connexite: "+((nbColora/2)+1));
            
        ImageTest.afficherImage(bf , "Coucou");
        

            // On calcul l'histogramme ici
          //int [] his = Histogramme.normalise(Histogramme.calculHistogramme(bf)); // On calcul et normalise l'histogramme

          // ImageTest.afficherHistogramme(his);

        }catch (IOException e) {
            System.out.println("ERREUR CHARGEMENT IMAGE.");
        }
    }
}