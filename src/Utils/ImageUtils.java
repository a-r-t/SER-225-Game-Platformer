package Utils;

import java.awt.*;
import java.awt.image.*;

// This class hold ssome useful image methods that are used when loading in images to the game
public class ImageUtils {
	// https://stackoverflow.com/a/665428
	// changes desired color to be transparent (the chosen color will not be seen in game when drawn)
	public static BufferedImage transformColorToTransparency(BufferedImage image, Color c1) {
		// Primitive test, just an example
		final int r1 = c1.getRed();
		final int g1 = c1.getGreen();
		final int b1 = c1.getBlue();
		final int r2 = 255;
		final int g2 = 255;
		final int b2 = 255;
		ImageFilter filter = new RGBImageFilter() {
			public final int filterRGB(int x, int y, int rgb) {
				int r = (rgb & 0xFF0000) >> 16;
				int g = (rgb & 0xFF00) >> 8;
				int b = rgb & 0xFF;
				if (r >= r1 && r <= r2 && g >= g1 && g <= g2 && b >= b1 && b <= b2) {
					// Set fully transparent but keep color
					return rgb & 0xFFFFFF;
				}
				return rgb;
			}
		};

		ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
		Image result = Toolkit.getDefaultToolkit().createImage(ip);
		return convertImageToBufferedImage(result, image.getWidth(), image.getHeight());
	}

	// https://stackoverflow.com/a/665428
	// converts Image data type to BufferedImage data type -- needed for the transformColorToTransparency method because it returns just an Image and not a BufferedImage
	public static BufferedImage convertImageToBufferedImage(Image image, int width, int height) {
		BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return dest;
	}

	// https://stackoverflow.com/a/4216315
	// resizes an image
	public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
		BufferedImage resized = new BufferedImage(newWidth, newHeight, image.getType());
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, image.getWidth(),
				image.getHeight(), null);
		g.dispose();
		return resized;
	}

	public static BufferedImage createSolidImage(Color color) {
		BufferedImage nothing = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		nothing = ImageUtils.transformColorToTransparency(nothing, color);
		return nothing;
	}

	public static BufferedImage createSolidImage(Color color, int width, int height) {
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		image = ImageUtils.transformColorToTransparency(image, color);
		return resizeImage(image, width, height);
	}
}
