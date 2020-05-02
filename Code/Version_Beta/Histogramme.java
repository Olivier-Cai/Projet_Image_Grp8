package Version_Beta;

import java.awt.image.BufferedImage;

public class Histogramme {

    /**
     * Permet de calculer l'histogramme cumulé croissant d'un histogramme.
     *
     * @param histogramme L'histogramme dont il faut calculer l'hcc
     * @return La matrice de l'histogramme cumulé croissant
     */
    public static int [] hcc (int [] histogramme) {

        int matricePoubelle [] = new int  [256];

        for (int i = 0 ; i <histogramme.length ; i ++) {

            if (i == 0)
                matricePoubelle [i] = histogramme [i];
            else
                matricePoubelle [i] = matricePoubelle [i-1] + histogramme[i];
        }

        return matricePoubelle;
    }
    /**
     * Retourne la valeur maximum dans un tableau
     *
     * @param matrice Tableau d'entiers
     *
     * @return le nombre maximal dans le tableau
     */
    public static int getMax (int [] matrice) {

        int max = 0;

        for (int i = 0 ; i<256 ; i ++)
            if (max < matrice [i])
                max = matrice[i];

        return max;
    }

    /**
     * Normalise toutes les valeurs du tableau entre O et 100
     *
     * @param matrice la matrice qui va être normalisé
     * @param max La valeur maximale qui va être utilisé pour normaliser la matrice
     *
     * @return Une matrice normalisé
     */
    public static int [] normalise (int [] matrice , int max) {

        int [] inter = new int [256];

        for (int i = 0 ; i <256 ; i++) {

            inter [i] = (int) (((double) matrice[i]/max)*100);
            //System.out.println(i + " "+inter [i]);
        }

        return inter;
    }

    /**
     * Normalise un histogramme entre 0 et 100
     *
     * @param matrice Histogramme non normalisé.
     *
     * @return L'histogramme normalisé
     */
    public static int [] normalise (int [] matrice) {

        int [] inter = new int [256];
        int max = Histogramme.getMax(matrice); // On récupère la valeur maximale de la matrice

        for (int i = 0 ; i <256 ; i++) {

            inter [i] = (int) (((double) matrice[i]/max)*100);
            //System.out.println(i + " "+inter [i]);
        }

        return inter;
    }

    /**
     * Calcul l'histogramme à partir de d'
     *
     * @param matrice la matrice qui va etre utilisé pour le calcul de l'histogramme
     * @param tailleX La taille du buffer en longeur
     * @param tailleY La taille du buffer en largeur
     * @param i Le niveau de gris
     * @return L'histogramme
     */
    public static int [] calculHistogramme (BufferedImage bi ,int matrice [], int tailleX , int tailleY , int i) {

        int indice;

        for (int x = 0 ; x < tailleX ; x ++) {

            for (int y = 0 ; y <tailleY ; y ++) {

                indice = i;
                matrice [indice] += 1;
            }
        }

        return matrice;
    }


    /**
     * Calcul l'histogramme des niveaux de gris dans une images DEJA GRISE
     *
     * @param bi Le buffer comptenant l'image en niveau de gris
     * @return retourne l'histogramme des niveaux de gris.
     */
    public static int [] calculHistogramme (BufferedImage bi) {

        int [] histo = new int [256];// Un tableau de 256 valeurs. Chaque case du tableau représente un niveau de gris.

        int x = bi.getWidth();// Longueur de l'image
        int y = bi.getHeight();//Largeur de l'image

        // On calcul tout l'histogramme

        for (int i = 0 ; i < y ; i ++) {// On compte les Y

            for (int j = 0 ; j < x ; j ++) {// On compte les x

                int couleur = bi.getRGB(j,i);
                int gris = (couleur>>8)&0xff;

                histo [gris] += 1;
            }
        }

        return histo;
    }
}