package projet_grp8.methode;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

//https://github.com/praserocking/MedianFilter/blob/master/MedianFilter.java

/**
 * Class de filtre median sur une case de 3x3 ou 5x5
 * @author willy
 *
 */
public class MedianFilter{

	/**
	 * Fonction median : Filtre median 3x3, qui prend 9 pixel, met dans un tableau, tri puis applique la valeur median sur le pixel central 
	 * puis on prendre le pixel median qui va remplacer le pixel central
	 * @param Buffered Image
	 * @return 
	 * @throws IOException
	 */
	public BufferedImage median(BufferedImage img) throws IOException{ 

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


	/**
	 * Fonction median5 : Filtre median 5x5 , qui prend 25 pixel, met dans un tableau, tri puis applique la valeur median sur le pixel central 
	 * puis on prendre le pixel median qui va remplacer le pixel central
	 * @param Buffered Image
	 * @return 
	 * @throws IOException
	 */
	public BufferedImage median5(BufferedImage bfi) throws IOException{ 
		BufferedImage img = createBlackBorder(bfi);

		Color[] pixel=new Color[25];
		int[] R=new int[25];
		int[] B=new int[25];
		int[] G=new int[25];

		for(int i=2;i<img.getWidth()-2;i++)
			for(int j=2;j<img.getHeight()-2;j++)
			{

				pixel[0]=new Color(img.getRGB(i-2,j-2));
				pixel[1]=new Color(img.getRGB(i-2,j-1));
				pixel[2]=new Color(img.getRGB(i-2,j));
				pixel[3]=new Color(img.getRGB(i-2,j+1));
				pixel[4]=new Color(img.getRGB(i-2,j+2));

				pixel[5]=new Color(img.getRGB(i-1,j-2));
				pixel[6]=new Color(img.getRGB(i-1,j-1));
				pixel[7]=new Color(img.getRGB(i-1,j));
				pixel[8]=new Color(img.getRGB(i-1,j+1));
				pixel[9]=new Color(img.getRGB(i-1,j+2));

				pixel[10]=new Color(img.getRGB(i,j-2));
				pixel[11]=new Color(img.getRGB(i,j-1));
				pixel[12]=new Color(img.getRGB(i,j));
				pixel[13]=new Color(img.getRGB(i,j+1));
				pixel[14]=new Color(img.getRGB(i,j+2));

				pixel[15]=new Color(img.getRGB(i+1,j-2));
				pixel[16]=new Color(img.getRGB(i+1,j-1));
				pixel[17]=new Color(img.getRGB(i+1,j));
				pixel[18]=new Color(img.getRGB(i+1,j+1));
				pixel[19]=new Color(img.getRGB(i+1,j+2));

				pixel[20]=new Color(img.getRGB(i+2,j-2));
				pixel[21]=new Color(img.getRGB(i+2,j-1));
				pixel[22]=new Color(img.getRGB(i+2,j));
				pixel[23]=new Color(img.getRGB(i+2,j+1));
				pixel[24]=new Color(img.getRGB(i+2,j+2));

				for(int k=0;k<25;k++){
					R[k]=pixel[k].getRed();
					B[k]=pixel[k].getBlue();
					G[k]=pixel[k].getGreen();
				}

				Arrays.sort(R);
				Arrays.sort(G);
				Arrays.sort(B);

				img.setRGB(i,j,new Color(R[13],B[13],G[13]).getRGB());
			}

		return img;

	}
	/**
	 * Filtre median 5x5 , qui prend 25 pixel, met dans un tableau, tri puis applique la valeur median sur le pixel central 
	 * puis on prendre le pixel median qui va remplacer le pixel central
	 * @param Buffered Image
	 * @return 
	 * @throws IOException
	 */
	public BufferedImage median5b(BufferedImage bfi) throws IOException{ 
		BufferedImage img = createBlackBorder(bfi);

		Color[] pixel=new Color[25];
		int[] R=new int[25];
		int[] B=new int[25];
		int[] G=new int[25];

		for(int i=2;i<img.getWidth()-2;i++)
			for(int j=2;j<img.getHeight()-2;j++)
			{

				pixel[0]=new Color(img.getRGB(i-2,j-2));
				pixel[1]=new Color(img.getRGB(i-2,j-1));
				pixel[2]=new Color(img.getRGB(i-2,j));
				pixel[3]=new Color(img.getRGB(i-2,j+1));
				pixel[4]=new Color(img.getRGB(i-2,j+2));

				pixel[5]=new Color(img.getRGB(i-1,j-2));
				pixel[6]=new Color(img.getRGB(i-1,j-1));
				pixel[7]=new Color(img.getRGB(i-1,j));
				pixel[8]=new Color(img.getRGB(i-1,j+1));
				pixel[9]=new Color(img.getRGB(i-1,j+2));

				pixel[10]=new Color(img.getRGB(i,j-2));
				pixel[11]=new Color(img.getRGB(i,j-1));
				pixel[12]=new Color(img.getRGB(i,j));
				pixel[13]=new Color(img.getRGB(i,j+1));
				pixel[14]=new Color(img.getRGB(i,j+2));

				pixel[15]=new Color(img.getRGB(i+1,j-2));
				pixel[16]=new Color(img.getRGB(i+1,j-1));
				pixel[17]=new Color(img.getRGB(i+1,j));
				pixel[18]=new Color(img.getRGB(i+1,j+1));
				pixel[19]=new Color(img.getRGB(i+1,j+2));

				pixel[20]=new Color(img.getRGB(i+2,j-2));
				pixel[21]=new Color(img.getRGB(i+2,j-1));
				pixel[22]=new Color(img.getRGB(i+2,j));
				pixel[23]=new Color(img.getRGB(i+2,j+1));
				pixel[24]=new Color(img.getRGB(i+2,j+2));

				for(int k=0;k<25;k++){
					R[k]=pixel[k].getRed();
					B[k]=pixel[k].getBlue();
					G[k]=pixel[k].getGreen();
				}

				Arrays.sort(R);
				Arrays.sort(G);
				Arrays.sort(B);

				int[]Colo = {R[13],B[13],G[13], 255};
				if(Colo[1] == 0) { //si elle est 255 = si elle est blanc
					img.setRGB(i,j,new Color(R[13],0,0).getRGB());
				}else {
					img.setRGB(i,j,new Color(R[13],B[13],G[13]).getRGB());
				}
				
			}

		return img;

	}
	
	/**
	 * Fonction createBlackBorder : Cree une nouvelle image plus grande avec une bordure de 2 pixels pour effectuer un median 5x5
	 * @param height : la taile de l'image
	 * @param width : la taile de l'image
	 * @param bfi : image a agrandir
	 * @return
	 */
	public BufferedImage createBlackBorder(BufferedImage bfi) {
		int bordure = 2;
		int width = bfi.getWidth();
		int height = bfi.getHeight();
		int i, j ;
		int[] noir = { 0, 0, 0, 255 };
		int p = bfi.getRGB(0,0);

		int r = (p>>16)&0xff; 
		int g = (p>>8)&0xff; 
		int b = p&0xff; 
		BufferedImage img = new BufferedImage(width+bordure*2, height+bordure*2, BufferedImage.TYPE_INT_ARGB);
		// WritableRaster raster = img.getRaster();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if(x > bordure && x <= bfi.getWidth()+bordure && y > bordure && y <= bfi.getHeight()+bordure) {

					i=x-bordure;
					j=y-bordure;
					p = bfi.getRGB(i, j);

					r = (p>>16)&0xff; 
					g = (p>>8)&0xff; 
					b = p&0xff; 

					int colorImg[] = {r, g, b, 255};
					img.getRaster().setPixel(x, y, colorImg);

				}
				else {
					img.getRaster().setPixel(x, y, noir);
				}

			}
		}
		return img;
	}

	/**
	 * Fonction deleteBorder : Cree une nouvelle image plus petite avec une bordure de 2
	 * @param height : la taile de l'image
	 * @param width : la taile de l'image
	 * @param bfi : image a retrcir
	 * @return
	 */
	public BufferedImage deleteBorder(BufferedImage bfi) {
		int bordure = 2;
		int width = bfi.getWidth();
		int height = bfi.getHeight();
		int i, j ;
		int[] noir = { 0, 0, 0, 255 };
		int p = bfi.getRGB(0,0);

		int r = (p>>16)&0xff; 
		int g = (p>>8)&0xff; 
		int b = p&0xff; 
		BufferedImage img = new BufferedImage(width-bordure*2, height-bordure*2, BufferedImage.TYPE_INT_ARGB);
		// WritableRaster raster = img.getRaster();
		for (int x = bordure; x < width-bordure; x++) {
			for (int y = bordure; y < height-bordure; y++) {
				i=x-bordure;
				j=y-bordure;
				p = bfi.getRGB(i, j);

				r = (p>>16)&0xff; 
				g = (p>>8)&0xff; 
				b = p&0xff; 

				int colorImg[] = {r, g, b, 255};
				img.getRaster().setPixel(i, j, colorImg);

			}
		}
		return img;
	}
}



