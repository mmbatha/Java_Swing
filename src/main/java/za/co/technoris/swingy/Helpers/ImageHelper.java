package za.co.technoris.swingy.Helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by mmbatha on 5/19/17.
 */
public class ImageHelper {

    public static BufferedImage loadImage(String path) {
        BufferedImage myPicture = null;

        try {
            myPicture = ImageIO.read(new File(path));
        } catch (IOException e) {
            LoggerHelper.print("Image loader: fail");
            System.exit(0);
        }
        return (myPicture);
    }
}
