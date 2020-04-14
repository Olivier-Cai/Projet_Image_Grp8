package Image2;

import Image.ImageTest;
import Image.Histogramme;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static void main (String [] args) {

        try {
            BufferedImage bf = ImageTest.chargerImage("/Users/leosok/Desktop/brave/escalier1.jpg");
           //ImageTest.greyScale(bf);
           //ImageTest.seuillage(bf, 120);
           ImageTest.imageBin(bf);
            
           // ImageTest.median(bf);
            ImageTest.afficherImage(bf , "Coucou");

            // On calcul l'histogramme ici
          int [] his = Histogramme.normalise(Histogramme.calculHistogramme(bf)); // On calcul et normalise l'histogramme

           ImageTest.afficherHistogramme(his);

        }catch (IOException e) {
            System.out.println("ERREUR CHARGEMENT IMAGE.");
        }
    }
}