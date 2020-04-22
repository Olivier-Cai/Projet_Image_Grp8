package Image;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.Arrays;

public class ImageTest {

	   public static BufferedImage chargerImage( String path) throws IOException {

	        return ImageIO.read(new File (path));
	    }


	    /**
	     * Permet de transformer une image en niveau de gris en  image binaire
	     *
	     * @param bi Le buffer contenant l'image
	     * @param seuil Le seuil à partir duquel le pixel sera blanc
	     *
	     * @return le bufferedImage avec la nouvelle image en noir et blanc
	     */
	    public static BufferedImage seuillage (BufferedImage bi,int seuil ) {

	        int couleur,red;

	        for (int x = 0 ; x<bi.getWidth() ; x ++) {
	            for (int y = 0 ; y <bi.getHeight() ; y ++) {
	                couleur = bi.getRGB(x, y);
	                red  = (couleur>>16)&0xff;


	                if (red < seuil)
	                    bi.setRGB(x, y, Color.black.getRGB());
	                else
	                    bi.setRGB(x, y, Color.white.getRGB());
	            }
	        }

	        return bi;
	    }
	    
	    public static BufferedImage w ( File bf) throws IOException {
	    	
	    	
	    	           //Input Photo File
	        Color[] pixel=new Color[9];
	        int[] R=new int[9];
	        int[] B=new int[9];
	        int[] G=new int[9];
	      
	        BufferedImage img=ImageIO.read(bf);
	        for(int i=1;i<img.getWidth()-1;i++)
	            for(int j=1;j<img.getHeight()-1;j++)
	            {
	               pixel[0]=new Color(img.getRGB(i-1,j-1));
	               pixel[1]=new Color(img.getRGB(i-1,j));
	               pixel[2]=new Color(img.getRGB(i-1,j+1));
	               pixel[3]=new Color(img.getRGB(i,j+1));
	               pixel[4]=new Color(img.getRGB(i+1,j+1));
	               pixel[5]=new Color(img.getRGB(i+1,j));
	               pixel[6]=new Color(img.getRGB(i+1,j-1));
	               pixel[7]=new Color(img.getRGB(i,j-1));
	               pixel[8]=new Color(img.getRGB(i,j));
	               for(int k=0;k<9;k++){
	                   R[k]=pixel[k].getRed();
	                   B[k]=pixel[k].getBlue();
	                   G[k]=pixel[k].getGreen();
	               }
	               Arrays.sort(R);
	               Arrays.sort(G);
	               Arrays.sort(B);
	               img.setRGB(i,j,new Color(R[4],B[4],G[4]).getRGB());
	            }
	    	
	               
				return img;
	            
	    	
	    }
	    

	    /**
	     * Met en négatif l'image
	     *
	     * @param bi
	     * @return
	     */
	    public static BufferedImage toNegatif (BufferedImage bi) {

	        BufferedImage img = bi;

	        for (int i = 0 ; i < bi.getWidth() ; i ++) {

	            for (int y = 0 ; y <bi.getHeight() ; y ++) {

	                int couleur = bi.getRGB(i, y);

	                //int a = (couleur>>24)&0xff;
	                int b = couleur&0xff;
	                int [] couleurBis = {255-b ,255-b ,255-b , 255 };

	                bi.getRaster().setPixel(i, y, couleurBis);
	            }
	        }

	        return img;
	    }

	    /**
	     * Affiche l'histogramme d'un image en noir et blanc
	     *
	     * @param matrice
	     * @throws IOException
	     */
	    public static void afficherHistogramme (int matrice []) throws IOException {

	        BufferedImage bf = new BufferedImage (256 ,100, BufferedImage.TYPE_INT_RGB);


	        for (int i =0 ; i < 256 ; i++ ) {

	            int y = 100 - matrice [i];
	            int compteur = y;

	            for ( compteur = y; y <100; y++) {

	                bf.setRGB(i, y, Color.WHITE.getRGB());
	            }
	        }

	        ImageTest.afficherImage(bf, "Image");
	        // On affiche le resultat
	    }

	    /**
	     * Affiche une image dans une fenetre à part
	     *
	     * @param image Le buffureredImage
	     *
	     * @return
	     * @throws IOException
	     */
	    public static void afficherImage(BufferedImage image , String title) throws IOException {
	        //Encoding the image
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write( image, "jpg", baos );
	        //Storing the encoded Mat in a byte array
	        byte[] byteArray = baos.toByteArray();
	        //Preparing the Buffered Image
	        InputStream in = new ByteArrayInputStream(byteArray);
	        BufferedImage bufImage = ImageIO.read(in);
	        //Instantiate JFrame
	        JFrame frame = new JFrame();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setTitle(title);
	        //Set Content to the JFrame
	        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
	        frame.pack();
	        frame.setVisible(true);

	    }


	    /**
	     * Transforme une image en couleur en noir niveau de gris
	     *
	     * @param bi Le bufferedImage qui contient l'image
	     * @return retourne le BufferedImage rentré en paramètre avec des valeurs en niveau de gris
	     */
	    public static BufferedImage greyScale (BufferedImage bi) {

	        int p , rouge , bleu, vert , gris;

	        BufferedImage ba = bi;

	        for (int i = 0 ;i <bi.getWidth() ; i ++ ) {

	            for (int y = 0 ; y < bi.getHeight() ; y ++) {

	                p = bi.getRGB(i, y);

	                rouge = (p>>16)&0xff;
	                bleu = p&0xff;
	                vert = (p>>8)&0xff;
	                gris = (rouge+vert+bleu)/3;

	                int [] couleur = { gris , gris , gris , 255 };

	                bi.getRaster().setPixel(i, y, couleur);
	            }
	        }

	        return ba;
	    }


	    /**
	     * Récupère le niveau de gris d'un pixel
	     *
	     * @param bi
	     * @param posX
	     * @param posY
	     * @return
	     */
	    public static int getGris (BufferedImage bi , int posX , int posY) {

	        int inter = bi.getRGB(posX, posY);
	        int couleur = (inter>>8)&0xff;

	        return couleur;
	    }


		public static float otsu(BufferedImage bf) {
				
				
		        int width = bf.getWidth();
		        int height = bf.getHeight();

		        int histData[] = new int[256];

		        for (int i = 0; i < width; i++) {
		            for (int j = 0; j < height; j++) {
		                int p = bf.getRGB(i, j);
		                int r = (p >> 16) & 0xff;
		                histData[r]++;

		            }
		        }

		        // Nombre total de pixel
		        int total = width * height;

		        float sum = 0;
		        for (int t = 0; t < 256; t++)
		            sum += t * histData[t];

		        float sumB = 0;
		        int wB = 0;
		        int wF = 0;

		        float varMax = 0;
		        float threshold = 0;

		        for (int t = 0; t < 256; t++) {
		            wB += histData[t]; // Weight arriere plan
		            if (wB == 0)
		                continue;

		            wF = total - wB; // Weight plan avant
		            if (wF == 0)
		                break;

		            sumB += (float) (t * histData[t]);

		            float mB = sumB / wB; // Mean arriere plan
		            float mF = (sum - sumB) / wF; // Mean avant plan

		            // calcul de la variance
		            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

		            // verifie si un nouveau maximum a été trouvé
		            if (varBetween > varMax) {
		                varMax = varBetween;
		                threshold = t;
		            }
		        }
		        System.out.println(threshold);
		        return threshold;
		    }
		public static BufferedImage imageBin(BufferedImage imgbin) throws IOException {

	        int width = imgbin.getWidth();
	        int height = imgbin.getHeight();

	        int noir[] = { 0, 0, 0, 255 };
	        int blanc[] = { 255, 255, 255, 255 };
	        float teta = otsu(imgbin);
	        for (int i = 0; i < height; i++) {
	            for (int j = 0; j < width; j++) {
	                int p = imgbin.getRGB(j, i);
	                int r = (p >> 16) & 0xff;
	                if (r < teta) {
	                    imgbin.getRaster().setPixel(j, i, noir);
	                } else {
	                    imgbin.getRaster().setPixel(j, i, blanc);
	                }
	            }
	        }

	        return imgbin;

	    }


		public static BufferedImage sobel(BufferedImage bfi) {
		int x = bfi.getWidth();
		int y = bfi.getHeight();

		int maxGval = 0;
		int[][] edgeColors = new int[x][y];
		int maxGradient = -1;

		for (int i = 1; i < x - 1; i++) {
			for (int j = 1; j < y - 1; j++) {

				int val00 = getGrayScale(bfi.getRGB(i - 1, j - 1));
				int val01 = getGrayScale(bfi.getRGB(i - 1, j));
				int val02 = getGrayScale(bfi.getRGB(i - 1, j + 1));

				int val10 = getGrayScale(bfi.getRGB(i, j - 1));
				int val11 = getGrayScale(bfi.getRGB(i, j));
				int val12 = getGrayScale(bfi.getRGB(i, j + 1));
				
				int val20 = getGrayScale(bfi.getRGB(i + 1, j - 1));
				int val21 = getGrayScale(bfi.getRGB(i + 1, j));
				int val22 = getGrayScale(bfi.getRGB(i + 1, j + 1));

				int gx =  ((1 * val00) + (0 * val01) + (-1 * val02)) 
						+ ((2 * val10) + (0 * val11) + (-2 * val12))
						+ ((1 * val20) + (0 * val21) + (-1 * val22));

				int gy =  ((1 * val00) + (2 * val01) + (1 * val02))
						+ ((0 * val10) + (0 * val11) + (0 * val12))
						+ ((-1 * val20) + (-2 * val21) + (-1 * val22));

				double gval = Math.sqrt((gx * gx) + (gy * gy));
				int g = (int) gval;

				if(maxGradient < g) {
					maxGradient = g;
				}

				edgeColors[i][j] = g;
			}
		}

		double scale = 255.0 / maxGradient;

		for (int i = 1; i < x - 1; i++) {
			for (int j = 1; j < y - 1; j++) {
				int edgeColor = edgeColors[i][j];
				edgeColor = (int)(edgeColor * scale);
				edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

				bfi.setRGB(i, j, edgeColor);
			}
		}
		//  File outputfile = new File("sobel.png");
		//  ImageIO.write(image, "png", outputfile);

//		System.out.println("max : " + maxGradient);
		return bfi;
	}

	public static int  getGrayScale(int rgb) {
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = (rgb) & 0xff;

		//from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
		int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
		//int gray = (r + g + b) / 3;

		return gray;
	}


	public static void egalisation(BufferedImage img) {
		int[] tab = new int[256];
		int min = 256; 
		int max = 0;
		int[] tabCum = new int[256];
		int cpt = 0;
		int sum = 0; 
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int p = img.getRGB(x,y);
				int r = (p>>16)&0xff;
				tab[r]++;
			
				if(r<min) {
					min = r; 
				}
				if (r>max) {
					max = r;
				}
			}
		}
		
		for (int x = 0; x < 256; x++) {
			sum += tab[x]; 
			tabCum[cpt] = sum;
			cpt++;
		}
		
		System.out.println("min "+min+", max "+max);
		
		int couleur[] = new int[3];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int p = img.getRGB(x,y);
				int r = (p>>16)&0xff;
				
				int j = (((256*(tabCum[r]-1))/(img.getHeight()*img.getWidth())));
				couleur[0] = j;
				couleur[1] = j;
				couleur[2] = j;
				
				img.getRaster().setPixel(x, y, couleur);
			}
		}
	}
	static BufferedImage zeros(int height, int width) {

		int[] blanc = { 255, 255, 255, 255 };
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// WritableRaster raster = img.getRaster();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				img.getRaster().setPixel(x, y, blanc);
			}
		}
		return img;
	}

	public static BufferedImage connexite(BufferedImage img) {

		int h = img.getHeight();
		int w = img.getWidth();
		int c = 1;

		int[] blanc = { 255, 255, 255, 255 };

		BufferedImage copie = zeros(h, w); //nouvelle image a retourner

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int pixel = img.getRGB(x, y);
				int pi = (pixel >> 8) & 0xff;
				int[] couleur = { c, c, c, 255 };

				if (pi == 0) { //si pixel est noir

					if (x == 0 && y == 0) { //px haut gauche

						int pixel_bas = img.getRGB(x, y + 1);
						int pib = (pixel_bas >> 8) & 0xff;
						
						int pixel_droite = img.getRGB(x + 1, y);
						int pid = (pixel_droite >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pib != 0 && pid != 0) {
							c++; //changement de couleur
						}
					}

					else if (x == w && y == 0) { //px haut droite

						int pixel_bas = img.getRGB(x, y + 1);
						int pib = (pixel_bas >> 8) & 0xff;
						int pixel_gauche = img.getRGB(x - 1, y);
						int pig = (pixel_gauche >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pib != 0 && pig != 0) {
							c++;
						}
					}

					else if (x == 0 && y == h) { //px bas gauche

						int pixel_haut = img.getRGB(x, y - 1);
						int pih = (pixel_haut >> 8) & 0xff;
						int pixel_droite = img.getRGB(x + 1, y);
						int pid = (pixel_droite >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pih != 0 && pid != 0) {
							c++;
						}
					}

					else if (x == w && y == h) { //px bas droite

						int pixel_gauche = img.getRGB(x - 1, y);
						int pig = (pixel_gauche >> 8) & 0xff;
						int pixel_droite = img.getRGB(x + 1, y);
						int pid = (pixel_droite >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pig != 0 && pid != 0) {
							c++;
						}
					}

					else if (x > 0 && x < w - 1 && y == 0) { //premiere ligne

						int pixel_bas = img.getRGB(x, y + 1);
						int pib = (pixel_bas >> 8) & 0xff;
						int pixel_gauche = img.getRGB(x - 1, y);
						int pig = (pixel_gauche >> 8) & 0xff;
						int pixel_droite = img.getRGB(x + 1, y);
						int pid = (pixel_droite >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pib != 0 && pid != 0 && pig != 0) {
							c++;
						}
					}

					else if (x > 0 && x < w - 1 && y == h) { //derniere ligne

						int pixel_bas = img.getRGB(x, y + 1);
						int pib = (pixel_bas >> 8) & 0xff;
						int pixel_gauche = img.getRGB(x - 1, y);
						int pig = (pixel_gauche >> 8) & 0xff;
						int pixel_droite = img.getRGB(x + 1, y);
						int pid = (pixel_droite >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pib != 0 && pid != 0 && pig != 0) {
							c++;
						}
					}

					else if (y > 0 && y < h - 1 && x == 0) { //colone de gauche

						int pixel_haut = img.getRGB(x, y - 1);
						int pih = (pixel_haut >> 8) & 0xff;
						int pixel_bas = img.getRGB(x, y + 1);
						int pib = (pixel_bas >> 8) & 0xff;
						int pixel_droite = img.getRGB(x + 1, y);
						int pid = (pixel_droite >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pib != 0 && pid != 0 && pih != 0) {
							c++;
						}
					}

					else if (y > 0 && y < h - 1 && x == w) { //colonne de droite

						int pixel_haut = img.getRGB(x, y - 1);
						int pih = (pixel_haut >> 8) & 0xff;
						int pixel_bas = img.getRGB(x, y + 1);
						int pib = (pixel_bas >> 8) & 0xff;
						int pixel_gauche = img.getRGB(x - 1, y);
						int pig = (pixel_gauche >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pib != 0 && pih != 0 && pig != 0) {
							c++;
						}
					}

					else if (x != 0 && x != w - 1 && y != 0 && y != h - 1) { //n'inclu pas les bords

						int pixel_haut = img.getRGB(x, y - 1);
						int pih = (pixel_haut >> 8) & 0xff;
						int pixel_bas = img.getRGB(x, y + 1);
						int pib = (pixel_bas >> 8) & 0xff;
						int pixel_gauche = img.getRGB(x - 1, y);
						int pig = (pixel_gauche >> 8) & 0xff;
						int pixel_droite = img.getRGB(x + 1, y);
						int pid = (pixel_droite >> 8) & 0xff;

						copie.getRaster().setPixel(x, y, couleur);
						img.getRaster().setPixel(x, y, blanc);

						if (pih != 0 && pib != 0 && pig != 0 && pid != 0) {
							c++;
						}
					}
				}
			}
		}
		return copie;
	}
	
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
    public static BufferedImage erode(BufferedImage img, int a) {
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
    public static BufferedImage dilate(BufferedImage img, int a) {
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
    private static int mask_dilate(int x, int y, BufferedImage img, int a) {
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
    public static BufferedImage open(BufferedImage img, int a) {
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
    public static BufferedImage close(BufferedImage img, int a) {
        // On d�finie une BufferedImage vide, aux dimensions de la BufferedImage source, et en niveau de gris
        BufferedImage tmpImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        // On effectue l'ouverture
        tmpImg = erode(dilate(img, a), a);

        return tmpImg;
    }


	public static BufferedImage median(BufferedImage img) {
		Color[] pixel=new Color[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];

        for(int i=1;i<img.getWidth()-1;i++)
            for(int j=1;j<img.getHeight()-1;j++)
            {
               pixel[0]=new Color(img.getRGB(i-1,j-1));
               pixel[1]=new Color(img.getRGB(i-1,j));
               pixel[2]=new Color(img.getRGB(i-1,j+1));
               
               pixel[3]=new Color(img.getRGB(i,j+1));
               pixel[4]=new Color(img.getRGB(i+1,j+1));
               pixel[5]=new Color(img.getRGB(i+1,j));
               
               pixel[6]=new Color(img.getRGB(i+1,j-1));
               pixel[7]=new Color(img.getRGB(i,j-1));
               pixel[8]=new Color(img.getRGB(i,j));
               
               for(int k=0;k<9;k++){
                   R[k]=pixel[k].getRed();
                   B[k]=pixel[k].getBlue();
                   G[k]=pixel[k].getGreen();
               }
               
               Arrays.sort(R);
               Arrays.sort(G);
               Arrays.sort(B);
               img.setRGB(i,j,new Color(R[4],B[4],G[4]).getRGB());
            }

		return img;
        
	}


	
}
