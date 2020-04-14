package Image2;

import java.awt.Color;
import java.awt.image.BufferedImage;
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
	    
	    public static BufferedImage median ( BufferedImage bi) throws IOException {
	    	
	    	
	    	File f=new File("photo.jpg");                               //Input Photo File
	        Color[] pixel=new Color[9];
	        int[] R=new int[9];
	        int[] B=new int[9];
	        int[] G=new int[9];
	        File output=new File("output.jpg");
	        BufferedImage img=ImageIO.read(f);
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
	    	
	               ImageIO.write(img,"jpg",output);
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
	
}
