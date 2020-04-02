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

		File path = new File(
				"/Users/oliviercai/Downloads/ImageL3-master/Test_Images/test.png");

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
