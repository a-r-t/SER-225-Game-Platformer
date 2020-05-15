package Engine;

import Utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage load(String imageFileName) {
        try {
            BufferedImage initialImage = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
            Image transparentImage = ImageUtils.transformColorToTransparency(initialImage, Config.TRANSPARENT_COLOR);
            return ImageUtils.convertImageToBufferedImage(transparentImage, transparentImage.getWidth(null), transparentImage.getHeight(null));
        } catch (IOException e) {
            System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
            throw new RuntimeException(e);
        }
    }
}
