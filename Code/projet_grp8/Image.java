package projet_grp8;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Image {
	public static void imshow(BufferedImage image) throws IOException {
		// Instantiate JFrame
		JFrame frame = new JFrame();

		// Set Content to the JFrame
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
<<<<<<< HEAD:Code/projet_grp8/Image.java
		File path = new File(
				"/Users/oliviercai/Downloads/ImageL3-master/Test_Images/test.png");
=======
		File path = new File("C:\\Users\\willy\\Documents\\Licence_maths_info\\S6\\Image\\projetImage\\Projet_Image_Grp8\\Bdd\\model1.jpg");
>>>>>>> master:Code/Image.java
		BufferedImage img = null;

		try {
			img = ImageIO.read(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imshow(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}