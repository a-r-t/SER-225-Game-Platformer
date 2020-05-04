import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite extends Rectangle {
	protected BufferedImage image;
	
	public Sprite(float x, float y, float width, float height, String imageFile) {
		super(x, y, width, height);
		this.setImage(imageFile);
	}
	
	public Sprite(float x, float y, float width, float height, String imageFile, Color transparentColor) {
		super(x, y, width, height);
		this.setImage(imageFile, transparentColor);
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(String imageFileName) {
		try {
			BufferedImage initialImage = ImageIO.read(new File(Config.resourcesPath + imageFileName));
		    image = initialImage;		
		} catch (IOException e) {
			System.out.println("Unable to find file " + Config.resourcesPath + imageFileName);
		}
	}
	
	public void setImage(String imageFileName, Color transparentColor) {
		try {
			BufferedImage initialImage = ImageIO.read(new File(Config.resourcesPath + imageFileName));
		    Image transparentImage = ImageUtils.transformColorToTransparency(initialImage, transparentColor, new Color(255, 255, 255));
		    BufferedImage transparentImageConvert = ImageUtils.convertImageToBufferedImage(transparentImage, transparentImage.getWidth(null), transparentImage.getHeight(null));	
			image = transparentImageConvert;
		} catch (IOException e) {
			System.out.println("Unable to find file " + Config.resourcesPath + imageFileName);
		}
	}
	
	@Override
	public void update(Keyboard keyboard) {
		super.update(keyboard);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, Math.round(getX()), Math.round(getY()), Math.round(width), Math.round(height), null);
	}

}
