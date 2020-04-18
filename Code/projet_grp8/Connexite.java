package projet_grp8;

import java.awt.image.BufferedImage;

public class Connexite {

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

	public BufferedImage connexite(BufferedImage img) {

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
}
