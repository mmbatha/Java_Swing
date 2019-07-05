/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:51:32 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:51:32 
 */
package za.co.technoris.swingy.Helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHelper {

	public static BufferedImage loadImage(String path) {
		BufferedImage myPicture = null;

		try {
			myPicture = ImageIO.read(new File(path));
		} catch (IOException ex) {
			LoggerHelper.print("Error: Image loader failed");
			System.exit(0);
		}
		return (myPicture);
	}
}
