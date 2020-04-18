package projet_grp8;

import java.awt.image.BufferedImage;

public class Otsu {

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
}