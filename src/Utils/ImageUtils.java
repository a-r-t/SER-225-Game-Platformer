package Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

public class ImageUtils {
	// https://stackoverflow.com/a/665428
	public static Image transformColorToTransparency(BufferedImage image, Color c1) {
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
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	// https://stackoverflow.com/a/665428
	public static BufferedImage convertImageToBufferedImage(Image image, int width, int height) {
		BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return dest;
	}

	/******************************************************************************************
	 * The below methods taken from the ImgScalr Library                                      *
	 * https://github.com/rkalla/imgscalr/blob/master/src/main/java/org/imgscalr/Scalr.java   *                 
	 * Modified a tad (mostly cleaned up all the code that this application didn't need)      *            
	 *****************************************************************************************/
	public static BufferedImage rotate(BufferedImage src, Rotation rotation, BufferedImageOp... ops) {
		int newWidth = src.getWidth();
		int newHeight = src.getHeight();

		AffineTransform tx = new AffineTransform();

		switch (rotation) {
		case CW_90:
			newWidth = src.getHeight();
			newHeight = src.getWidth();
			tx.translate(newWidth, 0);
			tx.quadrantRotate(1);
			break;
		case CW_270:
			newWidth = src.getHeight();
			newHeight = src.getWidth();
			tx.translate(0, newHeight);
			tx.quadrantRotate(3);
			break;
		case CW_180:
			tx.translate(newWidth, newHeight);
			tx.quadrantRotate(2);
			break;
		case FLIP_HORZ:
			tx.translate(newWidth, 0);
			tx.scale(-1.0, 1.0);
			break;
		case FLIP_VERT:
			tx.translate(0, newHeight);
			tx.scale(1.0, -1.0);
			break;
		}

		// Create our target image we will render the rotated result to.
		BufferedImage result = createOptimalImage(src, newWidth, newHeight);
		Graphics2D g2d = (Graphics2D) result.createGraphics();

		g2d.drawImage(src, tx, null);
		g2d.dispose();

		// Apply any optional operations (if specified).
		if (ops != null && ops.length > 0) {
			result = apply(result, ops);
		}

		return result;
	}

	public static enum Rotation {
		CW_90, CW_180, CW_270, FLIP_HORZ, FLIP_VERT;
	}

	protected static BufferedImage createOptimalImage(BufferedImage src, int width, int height) {
		return new BufferedImage(width, height, (src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB));
	}

	public static BufferedImage apply(BufferedImage src, BufferedImageOp... ops) {
		int type = src.getType();

		if (!(type == BufferedImage.TYPE_INT_RGB || type == BufferedImage.TYPE_INT_ARGB)) {
			src = copyToOptimalImage(src);
		}

		boolean hasReassignedSrc = false;

		for (int i = 0; i < ops.length; i++) {
			BufferedImageOp op = ops[i];

			// Skip null ops instead of throwing an exception.
			if (op == null) {
				continue;
			}
			Rectangle2D resultBounds = op.getBounds2D(src);

			BufferedImage dest = createOptimalImage(src, (int) Math.round(resultBounds.getWidth()), (int) Math.round(resultBounds.getHeight()));

			// Perform the operation, update our result to return.
			BufferedImage result = op.filter(src, dest);

			if (hasReassignedSrc) {
				src.flush();
			}
			src = result;
			hasReassignedSrc = true;
		}
		return src;
	}
	
	public static BufferedImage copyToOptimalImage(BufferedImage src) {
		// Calculate the type depending on the presence of alpha.
		int type = (src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
		BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), type);

		// Render the src image into our new optimal source.
		Graphics g = result.getGraphics();
		g.drawImage(src, 0, 0, null);
		g.dispose();

		return result;
	}
}
