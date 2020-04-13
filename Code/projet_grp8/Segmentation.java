package projet_grp8;

import java.awt.image.BufferedImage;

 
/* Fonction Segmentation qui regrouppe les differentes regions de pixels 
 * A partir d'une image en couleur, on va la diviiser en plusieurs classes 
 * Similaire a la segmentation lorsqu'il n'y a que 2 classes
 */
public class Segmentation {
	
	public static BufferedImage threshold(BufferedImage img, int s) {
		int rows = img.getHeight();
		int cols = img.getWidth();
		
		BufferedImage im_thresh = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		
		int[] noir = {0,0,0,255};
		int[] blanc = {255,255,255,255};
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int pixel = img.getRGB(j, i);
				int red = (pixel >> 16) & 0xff;
				if (red > s) {
					im_thresh.getRaster().setPixel(j, i, noir);
				}else {
					im_thresh.getRaster().setPixel(j, i,blanc );
				}
			}
		}
		
		return im_thresh;
	}
}
