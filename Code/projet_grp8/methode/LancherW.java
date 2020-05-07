package projet_grp8.methode;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LancherW {

	public static void imshow(BufferedImage image) throws IOException {
		//Instantiate JFrame 
		JFrame frame = new JFrame(); 

		//Set Content to the JFrame 
		frame.getContentPane().add(new JLabel(new ImageIcon(image))); 
		frame.pack(); 
		frame.setVisible(true);
	}

	public static void binarisation(BufferedImage img) {
		int[] Blanc = {255, 255, 255, 255};
		int[] Noir = {0, 0, 0, 255};
		
		// Binarisation - Seuillage optimal 
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int p = img.getRGB(x,y);
				int r = (p>>16)&0xff;
				if(r>160) {
					img.getRaster().setPixel(x, y, Noir);
				}
				else 
					img.getRaster().setPixel(x, y, Blanc);
			}
		}
		try {
			imshow(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void segmentation(BufferedImage img, int seuil) {
		int[] rouge = {255, 0, 0, 255};
		
		// Binarisation - Seuillage optimal 
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int p = img.getRGB(x,y);
				int r = (p>>16)&0xff;
				if(r>seuil) {
					img.getRaster().setPixel(x, y, rouge);
				}
			}
		}
		try {
			imshow(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		try {
			imshow(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		try {
			imshow(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void seuillageGris(BufferedImage img) throws IOException {
		int w = img.getWidth();
		int h = img.getHeight();
		
		for(int i = 0; i < w ; i++) {
			for(int j = 0; j < h ; j++) {
				int p = img.getRGB(i, j);
				
				// Passage en gris 
				int px = (p>>16)&0xff;
				int[] couleur = {px, px, px, 255};
				img.getRaster().setPixel(i, j, couleur);
			}
		}
		
		try {
			imshow(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //MedianFilter.median(img);
        //MedianFilter.median(img);
	}
	
	public static void negative() {
		BufferedImage img = null; 
		File f = null; 

		f = new File("C:\\Users\\willi\\Desktop\\Image_TD\\escalier1.jpg");
		img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

		try {
			img = ImageIO.read(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int w = img.getWidth();
		int h = img.getHeight();
		
		for(int i = 0; i < w ; i++) {
			for(int j = 0; j < h ; j++) {
				int p = img.getRGB(i, j);

				// Passage en négatif
				int r = (p>>16)&0xff; 
				int g = (p>>8)&0xff; 
				int b = p&0xff; 
				r = 255 - r; 
				g = 255 - g; 
				b = 255 - b; 
				int[] couleur1 = {r, g, b, 255};
				img.getRaster().setPixel(i, j, couleur1);
			}
		}
		try {
			imshow(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	public static void histogramme(BufferedImage img) {
		int i = 0; 
		int j = 0; 
		int k = 0;
		int x = 0; 
		int y = 0; 
		int tab[] = new int[512];

		int width  = img.getWidth();
		int height = img.getHeight();

		for(i = 0; i<width; i++) {
			x = i;
			for(j = 0; j<height; j++) {
				y = j; 
				int p = img.getRGB(x,y); // récupération des couleurs RGB du pixel a la position (x, y)
				int r = (p>>8)&0xff; 
				tab[r] = tab[r]+1;
				//System.out.println("test "+j+" "+tab[r]);
			}
		}	

		BufferedImage img1 = zeros(512, 1024);

		Graphics2D g1 = img1.createGraphics();

		int x1 = 0;  

		for(k = 0; k < tab.length; k++) {
			g1.drawLine(x1, 511, x1, 511-(tab[k]/50));
			x1 += 4;
		}	

		try {
			imshow(img1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void histogrammeProjection(File f) {
		int i = 0; 
		int j = 0; 
		int k = 0;
		int x = 0; 
		int y = 0; 
		int tab[] = new int[512];
		int tabProjectionVertical[] = new int[512];

		BufferedImage img = null;

		try {
			img = ImageIO.read(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int width  = img.getWidth();
		int height = img.getHeight();

		System.out.println("height = " + height + ";width = " + width);


		for(i = 0; i<width; i++) {
			x = i;
			for(j = 0; j<height; j++) {
				y = j; 
				int p = img.getRGB(x,y); // récupération des couleurs RGB du pixel a la position (x, y)
				int r = (p>>16)&0xff; 
				tab[r] = tab[r]+1;
				//System.out.println("test "+j+" "+tab[r]);
			}
		}	

		System.out.println(tab[119]);
		BufferedImage img1 = zeros(512, 1024);

		Graphics2D g1 = img1.createGraphics();

		int x1 = 0;  

		for(k = 0; k < tab.length; k++) {
			g1.drawLine(x1, 511, x1, 511-(tab[k]/50));
			x1 += 4;
		}	

		try {
			imshow(img1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedImage img = null; 
		File f = null; 

		f = new File("C:\\Users\\willi\\Desktop\\Image_TD\\escalier1.jpg");
		img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		
		try {
			img = ImageIO.read(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		histogramme(img);
//		seuillageGris(img);
//		Sobel.sobel(img); //Image tout en noir mais contour en blanc
		egalisation(img);
		histogramme(img);
//		binarisation(img);
//      segmentation(img, 10);
//		negative();

	}
}
