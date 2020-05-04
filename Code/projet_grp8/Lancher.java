package projet_grp8;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferFloat;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Lancher {
	
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
	
	/**
	 * 
	 * @throws IOException
	 */
	public static void cc() throws IOException {
		
		// TODO Auto-generated method stub
		File path = new File("C:\\Users\\willy\\Documents\\Licence_maths_info\\S6\\Image\\TD\\ImageL3-master\\ImageL3-master\\Test_Images\\shapesGray.jpg");

		BufferedImage img = null;
		ImgController ic = new ImgController();
		try {
			img = ImageIO.read(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		img = ic.seuillage(img, 245);
		img = ErosionDilatation.erode(img, 7);
		BufferedImage cc = Label8.getCC(img);
		Label8.getNumberOfCC(cc);
		
		Imshow.imshow(img);
		Imshow.imshow(cc);
		
	}
	
	
	
	public static BufferedImage getFloatBuuffredImage(int w, int h) {
        int bands = 1; // 4 bands for ARGB, 3 for RGB etc
        int[] bandOffsets = {0}; // length == bands, 0 == R, 1 == G, 2 == B and 3 == A

        // Create a TYPE_FLOAT sample model (specifying how the pixels are stored)
        SampleModel sampleModel = new PixelInterleavedSampleModel(DataBuffer.TYPE_FLOAT, w, h, bands, w  * bands, bandOffsets);
        // ...and data buffer (where the pixels are stored)
        DataBuffer buffer = new DataBufferFloat(w * h * bands);

        // Wrap it in a writable raster
        WritableRaster raster = Raster.createWritableRaster(sampleModel, buffer, null);

        // Create a color model compatible with this sample model/raster (TYPE_FLOAT)
        // Note that the number of bands must equal the number of color components in the 
        // color space (3 for RGB) + 1 extra band if the color model contains alpha 
        ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorModel colorModel = new ComponentColorModel(colorSpace, false, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_FLOAT);

        // And finally create an image with this raster
        BufferedImage image = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);

        //System.out.println("image = " + image);
        
        return image;
	}
	
	public static BufferedImage convolve(BufferedImage img, int[][] mask, int mask_size) {
		
		int rows = img.getHeight();
		int cols = img.getWidth();
		
		BufferedImage output = getFloatBuuffredImage(cols, rows);
		
			
		for(int i=1; i<rows-1 ;i++){
	        for(int j=1; j<cols-1 ;j++){
	        	
	            float conv_pix=0;
	            for(int x=-1; x<2;x++){
	                for(int y=-1;y<2;y++){
	                	float pixel = (float) ((img.getRGB(j+y, i+x) >> 16) & 0xff);
	                	conv_pix = conv_pix + (pixel * mask[x+1][y+1]);
	                	//System.out.println("\tmask " + mask[x+1][y+1] + "; conv pix " +conv_pix);
	                }
	            }
	            //System.out.println("; pixel value = " + conv_pix);
	            float[] new_pixel_value = {conv_pix, conv_pix, conv_pix, (float) 255.0};
	            
	            output.getRaster().setPixel(j, i, new_pixel_value);
	            
	            float[] out = new float[3];
	            output.getRaster().getPixel(j, i, out);
	            //int a = (p>>24)&0xff; 
	            //int r = (p>>16)&0xff; 
	            //int g = (p>>8)&0xff; 
	            //int b = p&0xff; 
	    		
	            //System.out.println("convRes = " + new_pixel_value[0] + "; " + out[0]);
	            //System.out.println("\tseted val =>  " + "a = " +  a + " r = " +  r + " g = " + g + " b = " + b);
	            //.setDataElements(j, i, new_pixel_value);
	            //
	        }
	    }
		
		return output;
	}

	
	public static void main(String[] args) throws Exception {
		
		cc();
//		testMorphMath();
		
	}

}