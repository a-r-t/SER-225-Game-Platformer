package Engine;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Painter {
    private Graphics2D g;

    public void setGraphics(Graphics2D g) {
        this.g = g;
    }

    public void paintImage(BufferedImage image, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    public void paintImage(BufferedImage image, int x, int y, int scale) {
        g.drawImage(image, x, y, image.getWidth() * scale, image.getHeight() * scale, null);
    }

    public void paintImage(BufferedImage image, int x, int y, int scale, ImageEffect imageEffect) {
        if (imageEffect == ImageEffect.FLIP_HORIZONTAL) {
            g.drawImage(image, x + image.getWidth(), y, -(image.getWidth() * scale), image.getHeight() * scale, null);
        } else if (imageEffect == ImageEffect.FLIP_VERTICAL) {
            g.drawImage(image, x, y + image.getHeight(), image.getWidth(), -(image.getHeight() * scale), null);
        } else {
            paintImage(image, x, y, scale);
        }
    }

    public void paintImage(BufferedImage image, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void paintImage(BufferedImage image, int x, int y, int width, int height, int scale) {
        g.drawImage(image, x, y, width * scale, height * scale, null);
    }

    public void paintImage(BufferedImage image, int x, int y, int width, int height, ImageEffect imageEffect) {
        if (imageEffect == ImageEffect.FLIP_HORIZONTAL) {
            g.drawImage(image, x + width, y, -width, height, null);
        } else if (imageEffect == ImageEffect.FLIP_VERTICAL) {
            g.drawImage(image, x, y + height, width, -height, null);
        } else {
            paintImage(image, x, y, width, height);
        }
    }

    public void paintImage(BufferedImage image, int x, int y, int width, int height, int scale, ImageEffect imageEffect) {
        if (imageEffect == ImageEffect.FLIP_HORIZONTAL) {
            g.drawImage(image, x + width, y, -(width * scale), height * scale, null);
        } else if (imageEffect == ImageEffect.FLIP_VERTICAL) {
            g.drawImage(image, x, y + height, width, -(height * scale), null);
        } else {
            paintImage(image, x, y, width, height, scale);
        }
    }

    public void paintRectangle(int x, int y, int width, int height, Color color) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    public void paintRectangle(int x, int y, int width, int height, Color color, int borderThickness) {
        g.setStroke(new BasicStroke(borderThickness));
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    public void paintFilledRectangle(int x, int y, int width, int height, Color color) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void paintString(String text, int x, int y, Font font, Color color) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    // https://stackoverflow.com/a/35222059 and https://stackoverflow.com/a/31831120
    public void paintStringWithOutline(String text, int x, int y, Font font, Color textColor, Color outlineColor, float outlineThickness) {
        // remember original settings
        Color originalColor = g.getColor();
        Stroke originalStroke = g.getStroke();
        RenderingHints originalHints = g.getRenderingHints();
        g.setStroke(new BasicStroke(outlineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // create a glyph vector from your text
        GlyphVector glyphVector = font.createGlyphVector(g.getFontRenderContext(), text);

        // get the shape object
        Shape textShape = glyphVector.getOutline();
        AffineTransform at = new AffineTransform();
        at.setToTranslation(Math.round(x), Math.round(y));
        textShape = at.createTransformedShape(textShape);

        // activate anti aliasing for text rendering (if you want it to look nice)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.setColor(outlineColor);
        g.draw(textShape); // draw outline

        g.setColor(textColor);
        g.fill(textShape); // fill the shape

        // reset to original settings after painting
        g.setColor(originalColor);
        g.setStroke(originalStroke);
        g.setRenderingHints(originalHints);
    }

    public enum ImageEffect {
        NONE, FLIP_HORIZONTAL, FLIP_VERTICAL
    }
}
