package projet_grp8;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Egalisation {
	/*
	 * Affichage de l'image
	 */
	public static void imshow(BufferedImage image) throws IOException {
	      //Instantiate JFrame 
	      JFrame frame = new JFrame(); 

	      //Set Content to the JFrame 
	      frame.getContentPane().add(new JLabel(new ImageIcon(image))); 
	      frame.pack(); 
	      frame.setVisible(true);
	}
	
	/*
	 * Ecriture des barres de l'histogramme
	 */
	static BufferedImage zeros(int height, int width) {
		int[] noir = {0,0,0,255};
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
		//WritableRaster raster = img.getRaster();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height ; y++) {
				img.getRaster().setPixel(x, y, noir);
			}
		}
		return img; 
	}
	
	/*
	 * Egalisation de l'image du fichier f
	 * @param f 
	 */
	public static void egalisation(File f) {
		BufferedImage bi = null;
	
		try {
			bi = ImageIO.read(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int width = bi.getWidth();
		int height = bi.getHeight();
		System.out.println(width + "," +height);
		int nbPixel= width*height;
		int[] histogram = new int[255];
		int[] iarray = new int[10000];
		int i = 0;
		
		//read pixel intensities into histogram
		for (int x = 0; x < width; x++) {
			System.out.println("1");
			for (int y = 0; y < height; y++) {
			System.out.println("iarray : "+(iarray)[0]+" x : "+x+" y : "+y);
			int valueBefore = bi.getRaster().getPixel(x, y,iarray)[0];
			System.out.println("3");
			histogram[valueBefore]++;
			}
		}
		
		int histogramCum =0;
		// build a Lookup table newTab containing scale factor
		float[] newTab = new float[nbPixel];
		for ( i=0; i < 255; ++i ) {
			histogramCum += histogram[i];
			newTab[i] = histogramCum * 255 / nbPixel;
		}
		
		// transform image using histogramCum histogram as a Lookup table
		for (int x = 1; x < width; x++) {
			for (int y = 1; y < height; y++) {
				int valueBefore=bi.getRaster().getPixel(x, y,iarray)[0];
				int valueAfter= (int) newTab[valueBefore];
				iarray[0]=valueAfter;
				bi.getRaster().setPixel(x, y, iarray);
			}
		}
		
		System.out.println(histogram[119]);
		BufferedImage img1 = zeros(512, 1024);

		Graphics2D g1 = img1.createGraphics();

		int x1 = 0;  

		for(int k = 0; k < histogram.length; k++) {
			g1.drawLine(x1, 511, x1, 511-(histogram[k]/50));
			x1 += 4;
		}	

		try {
			imshow(bi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void egalisation2(File f) {
//		int k = 0;
		int x = 0; 
		int y = 0; 
		int tab[] = new int[512];
		int[] iarray = new int[10000];

		BufferedImage img = null;

		try {
			img = ImageIO.read(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int width  = img.getWidth();
		int height = img.getHeight();
		int nbPixel= width*height;
		
		System.out.println("height = " + height + ";width = " + width);

		for(int i = 0; i<width; i++) {
			x = i;
			for(int j = 0; j<height; j++) {
				y = j; 
				int p = img.getRGB(x,y); // récupération des couleurs RGB gris du pixel a la position (x, y)
				int r = (p>>16)&0xff; 
				tab[r] = tab[r]+1;
				//System.out.println("test "+j+" "+tab[r]);
			}
		}
		
		BufferedImage imgBefore = zeros(512, 1024);

		Graphics2D g1Before = imgBefore.createGraphics();

		int x1Before = 0;  

		for(int s = 0; s < tab.length; s++) {
			g1Before.drawLine(x1Before, 511, x1Before, 511-(tab[s]/50));
			x1Before += 4;
		}	

		try {
			imshow(imgBefore);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int histogramCum = 0;
		// build a Lookup table newTab containing scale factor
		int[] newTab = new int[nbPixel];
		for (int a = 0; a < 255; ++a ) {
			histogramCum += tab[a];
			System.out.println(tab[a]);
			newTab[a] = histogramCum * 255 / nbPixel;
			System.out.println("new : "+newTab[a]);
		}
		
		// transform image using histogramCum histogram as a Lookup table
		for (int z = 0; z < width; z++) {
			for (int e = 0; y < height; y++) {
				int valueBefore=img.getRaster().getPixel(z, e, iarray)[0];
				int valueAfter= (int) newTab[valueBefore];
				img.getRaster().setPixel(z, e, iarray);
			}
		}

		BufferedImage imgAfter = zeros(512, 1024);

		Graphics2D g1After = imgAfter.createGraphics();

		int x1After = 0;  

		for(int q = 0; q < newTab.length; q++) {
			g1After.drawLine(x1After, 511, x1After, 511-(newTab[q]/50));
			x1After += 4;
		}	

		try {
			imshow(imgAfter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		File path = new File("C:\\Users\\willi\\Desktop\\Image_TD\\escalier1.jpg");
		egalisation2(path);
	}
}
