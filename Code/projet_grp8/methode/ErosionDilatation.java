package projet_grp8.methode;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;

/**
 * Class d'erosion et de dilatation
 * @author willy
 *
 */
public class ErosionDilatation {

	/**
     * Fonction blur : D�finition floue de l'�l�ment structurant des op�rations de dilatation et d'�rosion
     * @param t la distance entre le pixel en cours de traitement (centre de l'�l�ment structurant) et du point de l'�l�ment structurant courant
     * @param a le diam�tre du carr� constituant l'�l�ment structurant
     * @return le degr� d'appartenance du point � l'�l�ment structurant
     */
    private static double blur(int t, int a) {
        double res = Math.min(1, Math.max(0, 1 - (t / a)));
        return res;
    }

    /**
     * Fonction hamming : Calcul de la norme Hamming entre le pixel en cours de traitement et le point de l'�l�ment structurant courant
     * @param x la diff�rence en abscisse entre les deux points
     * @param y la diff�rence en abscisse entre les deux points
     * @return la norme Hamming entre les deux points
     */
    private static int hamming(int x, int y) {
        int res = Math.max(Math.abs(x), Math.abs(y));
        return res;
    }


    /**
     * Fonction convertToGrayscale : Conversion d'un objet BufferedImage en niveau de gris
     * @param source la BufferedImage correspondant � l'image que l'utilisateur a choisi
     * @return la copie de la BufferedImage en niveau de gris
     */
    public static BufferedImage convertToGrayscale(BufferedImage source) {
        BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return op.filter(source, null);
    }

    /**
     * Fonction erode : Erosion de la BufferedImage courante
     * @param img la BufferedImage source, qui ne sera pas modifi� � l'int�rieur de la fonction
     * @param a le rayon de l'�l�ment structurant
     * @return la nouvelle BufferedImage apr�s �rosion de la BufferedImage source
     */
    public BufferedImage erode(BufferedImage img, int a) {
        // On cr�e la BufferedImage destination, de m�me dimension que la source, et en niveau de gris
        BufferedImage newImgGray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        int newGrayLevel = 0;
        int newRGB = 0;

        // On parcourt l'image
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                // On r�cup�re la valeur de gris du pixel courant apr�s �rosion
                newGrayLevel = mask_erode(j, i, img, a);
                // On la transforme en couleur RGB, afin de pouvoir l'utiliser via setRGB
                newRGB = (0xFF << 24) | ((newGrayLevel & 0xFF) << 16) | ((newGrayLevel & 0xFF) << 8) | ((newGrayLevel & 0xFF));
                // Et on affecte cette couleur au pixel courant
                newImgGray.setRGB(j, i, newRGB);
            }
        }
        return newImgGray;
    }

    /**
     * Fonction mask_erode : Calcul du niveau de gris � affecter au pixel courant � partir des niveaux de gris des voisins
     * @param x l'abscisse du pixel courant
     * @param y l'ordonn�e du pixel courant
     * @param img l'image d'origine en niveau de gris
     * @param a le rayon de l'�l�ment structurant
     * @return le niveau de gris "�rod�" du pixel courant
     */
    private static int mask_erode(int x, int y, BufferedImage img, int a) {
        // Variables temporaires stockant les deux valeurs calcul�es de niveau de gris, pour chaque pixel de l'�l�ment structurant
        double tempMuGL, tempFGL;
        // Variable temporaire stockant le maximum des deux variables temporaires ci-dessus
        int maxGL;
        // Le niveau de gris final, initialis� � la valeur maximale autoris�e
        int finalGL = 255;

        // On parcourt la zone de l'image que forme l'�l�ment structurant
        for (int i = -a; i <= a; i++) {
            for (int j = -a; j <= a; j++) {
                boolean imgEdge = false;

                // On v�rifie que l'on n'est pas en dehors de l'image
                if (x + j < 0 || x + j > img.getWidth() - 1) {
                    imgEdge = true;
                }
                if (y + i < 0 || y + i > img.getHeight() - 1) {
                    imgEdge = true;
                }

                if (!imgEdge) {
                    // R�cup�ration de la valeur par la fonction d'appartenance de l'ensemble flou de l'image
                    tempMuGL = (double) new Color(img.getRGB(x + j, y + i)).getBlue() / 255;
                    // R�cup�ration de la valeur par l'�l�ment structurant
                    tempFGL = 1 - blur(hamming(j, i), a);
                    // On utilise la t-norme de Zadeh comme crit�re de s�lection
                    maxGL = (int) (Math.max(tempMuGL, tempFGL) * 255);

                    // Et on stocke finalement la valeur calcul�e si elle est plus faible que la valeur r�cup�r�e dans les tours de boucles pr�c�dents
                    if (maxGL < finalGL) {
                        finalGL = maxGL;
                    }
                }
            }
        }
        return finalGL;
    }

    /**
     * Fonction dilate : Dilatation de la BufferedImage courante
     * @param img la BufferedImage source, qui ne sera pas modifi� � l'int�rieur de la fonction
     * @param a le rayon de l'�l�ment structurant
     * @return la nouvelle BufferedImage apr�s dilatation de la BufferedImage source
     */
    public BufferedImage dilate(BufferedImage img, int a) {
        // On cr�e la BufferedImage destination, de m�me dimension que la source, et en niveau de gris
        BufferedImage newImgGray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        int newGrayLevel = 0;
        int newRGB = 0;

        // On parcourt l'image
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                // On r�cup�re la valeur de gris du pixel courant apr�s dilatation
                newGrayLevel = mask_dilate(j, i, img, a);
                // On la transforme en couleur RGB, afin de pouvoir l'utiliser via setRGB
                newRGB = (0xFF << 24) | ((newGrayLevel & 0xFF) << 16) | ((newGrayLevel & 0xFF) << 8) | ((newGrayLevel & 0xFF));
                // Et on affecte cette couleur au pixel courant
                newImgGray.setRGB(j, i, newRGB);
            }
        }
        return newImgGray;
    }

    /**
     * Fonction mask_dilate : Calcul du niveau de gris � affecter au pixel courant � partir des niveaux de gris des voisins
     * @param x l'abscisse du pixel courant
     * @param y l'ordonn�e du pixel courant
     * @param img l'image d'origine en niveau de gris
     * @param a le rayon de l'�l�ment structurant
     * @return le niveau de gris "dilat�" du pixel courant
     */
    private int mask_dilate(int x, int y, BufferedImage img, int a) {
        // Variables temporaires stockant les deux valeurs calcul�es de niveau de gris, pour chaque pixel de l'�l�ment structurant
        double tempMuGL, tempFGL;
        // Variable temporaire stockant le minimum des deux variables temporaires ci-dessus
        int minGL;
        // Le niveau de gris final, initialis� � la valeur minimale autoris�e
        int finalGL = 0;

        // On parcourt la zone de l'image que forme l'�l�ment structurant
        for (int i = -a; i <= a; i++) {
            for (int j = -a; j <= a; j++) {
                boolean imgEdge = false;

                // On v�rifie que l'on n'est pas en dehors de l'image
                if (x + j < 0 || x + j > img.getWidth() - 1) {
                    imgEdge = true;
                }
                if (y + i < 0 || y + i > img.getHeight() - 1) {
                    imgEdge = true;
                }

                if (!imgEdge) {
                    // R�cup�ration de la valeur par la fonction d'appartenance de l'ensemble flou de l'image
                    tempMuGL = (double) new Color(img.getRGB(x + j, y + i)).getBlue() / 255;
                    // R�cup�ration de la valeur par l'�l�ment structurant
                    tempFGL = blur(hamming(j, i), a);
                    // On utilise la co-norme de Zadeh comme crit�re de s�lection
                    minGL = (int) (Math.min(tempMuGL, tempFGL) * 255);

                    // Et on stocke finalement la valeur calcul�e si elle est plus forte que la valeur r�cup�r�e dans les tours de boucles pr�c�dents
                    if (minGL > finalGL) {
                        finalGL = minGL;
                    }
                }
            }
        }
        return finalGL;
    }

    /**
     * Fonction open : Ouverture math�matique de l'image, soit une �rosion, puis une dilatation successive de l'image
     * @param img la BufferedImage ouverte par l'utilisateur
     * @param a le rayon de l'�l�ment structurant
     * @return la BufferedImage �ord�e et dilat�e correspondante
     */
    public BufferedImage open(BufferedImage img, int a) {
        // On d�finie une BufferedImage vide, aux dimensions de la BufferedImage source, et en niveau de gris
        BufferedImage tmpImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        // On effectue l'ouverture
        tmpImg = dilate(erode(img, a), a);

        return tmpImg;
    }

    /**
     * Fonction close : Fermeture math�matique de l'image, soit une dilatation, puis une �rosion successive de l'image
     * @param img la BufferedImage ouverte par l'utilisateur
     * @param a le rayon de l'�l�ment structurant
     * @return la BufferedImage dilat�e et �rod�e correspondante
     */
    public BufferedImage close(BufferedImage img, int a) {
        // On d�finie une BufferedImage vide, aux dimensions de la BufferedImage source, et en niveau de gris
        BufferedImage tmpImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        // On effectue l'ouverture
        tmpImg = erode(dilate(img, a), a);

        return tmpImg;
    }
	
}