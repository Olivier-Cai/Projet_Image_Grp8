package projet_grp8;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class image
 * @author willy
 *
 */
public class Image {
	
	private static BufferedImage bfi;
	
	public Image(BufferedImage bfi) {
		Image.bfi = bfi;
	}
	
	/**
	 * boolean pour le depassement d'un seuil sur l'histogramme
	 * si la ligne est deja depasser alors il ne prend pas en compte
	 * 
	 * @param depassed
	 * @param seuilactuel
	 * @param seuil
	 * @return
	 */
	public static boolean isDepassed(boolean depassed, int seuilactuel, int seuil) {
		if(!depassed) {
			if(seuilactuel >= seuil) {
				return true;
			}else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * trace le graphique des pixels noir en fonction de l'image sur l'axe X et affiche le graph
	 * @throws IOException
	 */
	public void histoX () throws IOException {		
		BufferedImage imgBinaire = new BufferedImage(bfi.getWidth(), bfi.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
	    Graphics2D surfaceImg = imgBinaire.createGraphics();
	    surfaceImg.drawImage(bfi, null, null);      
	    bfi = imgBinaire;
	    
	    
	    int width = bfi.getWidth();
	    int height = bfi.getHeight();
	    int[]tabHeight=new int[height];
	    int sum = 0;
	    
	    for (int y=0; y<height; y++) { //histograme par ligne : axe X
		    for (int x=0; x<width; x++) {
		    	if(((bfi.getRGB(x, y)>>8)&0xff) == 0)
		    		sum++;
		    }
		    tabHeight[y]=sum;
		    sum=0;
	    }   

	    //trace de l'histogramme ligne
	    BufferedImage bi2 = new BufferedImage( width+50, height+50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi2.createGraphics();
		for (int i=0; i<tabHeight.length; i++)
			g2.drawLine(20, i, 20+(tabHeight[i]), i); 
		
		/**
		 * compteur de ligne horizontale sur l'axe X 
		 * Attention il faut prendre en compte le décalage de 
		 * drawLine(20,..,20,..) dans les methodes histoX() et histoY()
		 */
		int seuilMarche=60; //a regler en fonction de l'image binaire
	    int nbLigne=0;
	    boolean depassed = false;
	    for(int i=0; i<tabHeight.length; i++){
	    	if(	isDepassed( depassed,  tabHeight[i], seuilMarche) ) { //voir method en haut
	    		nbLigne++;
	    	}
	    }
	    System.out.println("il peu y avoir "+nbLigne+" ligne(s)");   

		Imshow.imshow(bi2);
	    Imshow.imshow(bfi);
	}
	
	/**
	 * trace le graphique des pixels noir en fonction de l'image sur l'axe Y et affiche le graph
	 * @throws IOException
	 */
	public void histoY () throws IOException {		
		BufferedImage imgBinaire = new BufferedImage(bfi.getWidth(), bfi.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
	    Graphics2D surfaceImg = imgBinaire.createGraphics();
	    surfaceImg.drawImage(bfi, null, null);      
	    bfi = imgBinaire;
	    
	    int width = bfi.getWidth();
	    int height = bfi.getHeight();
	    int[]tabWidth=new int[width];
	    int sum = 0;

	    for (int x=0; x<width; x++) { //histograme par colonne : axe Y
	    	for (int y=0; y<height; y++) {
	    		if(((bfi.getRGB(x, y)>>8)&0xff) == 0)
	    			sum++;
	    	}
	    	tabWidth[x]=sum;
	    	sum=0;
	    }
		
		//trace de l'histogramme colonne
		BufferedImage bi3 = new BufferedImage( width+50, height+50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g3 = bi3.createGraphics();
		//ligne droite
		for (int i=0; i<tabWidth.length; i++)
			g3.drawLine(i, 20, i, 20+(tabWidth[i]));
		
		Imshow.imshow(bi3);
	    Imshow.imshow(bfi);
	    
	}	
}
