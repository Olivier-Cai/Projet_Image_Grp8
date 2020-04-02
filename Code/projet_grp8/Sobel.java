package projet_grp8;

import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.lang.Math;

import java.io.File;
import java.io.IOException;

public class Sobel {

	public static void imshow(BufferedImage image) throws IOException {
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

		//Set Content to the JFrame 
		frame.getContentPane().add(new JLabel(new ImageIcon(bufImage))); 
		frame.pack(); 
		frame.setVisible(true);
	}

	public static void main(String args[]) throws IOException {

		System.out.println("Started");
		/*System.out.println("Enter the file name :");
         Scanner ne1 = new Scanner(System.in);
         String filename = ne1.nextLine();*/

		String filename = "C:\\Users\\willy\\Documents\\Licence_maths_info\\S6\\Image\\projetImage\\model1.jpg";

		File file = new File(filename);
		BufferedImage image = ImageIO.read(file);

		int x = image.getWidth();
		int y = image.getHeight();

		int maxGval = 0;
		int[][] edgeColors = new int[x][y];
		int maxGradient = -1;

		for (int i = 1; i < x - 1; i++) {
			for (int j = 1; j < y - 1; j++) {

				int val00 = getGrayScale(image.getRGB(i - 1, j - 1));
				int val01 = getGrayScale(image.getRGB(i - 1, j));
				int val02 = getGrayScale(image.getRGB(i - 1, j + 1));

				int val10 = getGrayScale(image.getRGB(i, j - 1));
				int val11 = getGrayScale(image.getRGB(i, j));
				int val12 = getGrayScale(image.getRGB(i, j + 1));
				int val20 = getGrayScale(image.getRGB(i + 1, j - 1));
				int val21 = getGrayScale(image.getRGB(i + 1, j));
				int val22 = getGrayScale(image.getRGB(i + 1, j + 1));

				int gx =  ((-1 * val00) + (0 * val01) + (1 * val02)) 
						+ ((-2 * val10) + (0 * val11) + (2 * val12))
						+ ((-1 * val20) + (0 * val21) + (1 * val22));

				int gy =  ((-1 * val00) + (-2 * val01) + (-1 * val02))
						+ ((0 * val10) + (0 * val11) + (0 * val12))
						+ ((1 * val20) + (2 * val21) + (1 * val22));

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

				image.setRGB(i, j, edgeColor);
			}
		}
		imshow(image);
		//  File outputfile = new File("sobel.png");
		//  ImageIO.write(image, "png", outputfile);

		System.out.println("max : " + maxGradient);
		System.out.println("Finished");
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
}