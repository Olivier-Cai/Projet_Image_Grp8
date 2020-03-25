package imgProcsJava;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class td2 {

	public static void imshow(BufferedImage image) throws IOException {
		// Instantiate JFrame
		JFrame frame = new JFrame();

		// Set Content to the JFrame
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		frame.setVisible(true);
	}

	public static int max(int[] c) {
		int max = 0;
		for (int i = 0; i < 256; i++) {
			max = Math.max(max, c[i]);
		}
		return max;
	}

	static BufferedImage zeros(int height, int width) {

		int[] blanc = { 0, 0, 0, 255 };
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// WritableRaster raster = img.getRaster();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				img.getRaster().setPixel(x, y, blanc);
			}
		}
		return img;
	}

	public static void ex1() {
		File path = new File("/Users/oliviercai/Downloads/ImageL3-master/Test_Images/test.png");
		BufferedImage img = null;

		try {
			img = ImageIO.read(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int[] noir = { 0, 0, 0, 255 };
		int[] blanc = { 255, 255, 255, 255 };

		// Nombre de pixel
		int n = 0;

		// Parcourir une image pixel par pixel
		int w = img.getWidth();
		int h = img.getHeight();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int p = img.getRGB(i, j);
				int pi = (p >> 8) & 0xff;
				if (pi < 145) {
					img.getRaster().setPixel(i, j, noir);
					n++;
				} else {
					img.getRaster().setPixel(i, j, blanc);
				}
			}
		}

		try {
			imshow(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(n + " pixel noir");
	}
	
	public static void ex2() {
		File path = new File("/Users/oliviercai/Downloads/ImageL3-master/Test_Images/landscape.png");
		BufferedImage img = null;

		try {
			img = ImageIO.read(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] histo = new int[256];
		int w = img.getWidth();
		int h = img.getHeight();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int p = img.getRGB(i, j);
				int pi = (p >> 8) & 0xff;
				histo[pi] += 1;
			}
		}
		
		BufferedImage img2 = zeros(110, 513);
		Graphics2D g = img2.createGraphics();
		int m = max(histo);
		for (int i = 0; i < histo.length; i++) {
			double norm =((double)histo[i] / (double)m) * 100;
			g.drawLine(i*2 , 105, i*2 , 105 - (int)norm);
		}
		try {
			imshow(img2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
