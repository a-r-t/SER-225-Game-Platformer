package Engine;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import GameObject.ImageEffect;

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
        switch (imageEffect) {
            case NONE:
                paintImage(image, x, y, scale);
            case FLIP_HORIZONTAL:
                g.drawImage(image, x + (image.getWidth() * scale), y, -(image.getWidth() * scale), image.getHeight() * scale, null);
            case FLIP_VERTICAL:
                g.drawImage(image, x, y + (image.getHeight() * scale), image.getWidth() * scale, -(image.getHeight() * scale), null);
            case FLIP_H_AND_V:
                g.drawImage(image, x + (image.getWidth() * scale), y + (image.getHeight() * scale), -(image.getWidth() * scale), -(image.getHeight() * scale), null);
        }
    }

    public void paintImage(BufferedImage image, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void paintImage(BufferedImage image, int x, int y, int width, int height, int scale) {
        g.drawImage(image, x, y, width * scale, height * scale, null);
    }

    public void paintImage(BufferedImage image, int x, int y, int width, int height, ImageEffect imageEffect) {
        switch (imageEffect) {
            case NONE:
                paintImage(image, x, y, width, height);
            case FLIP_HORIZONTAL:
                g.drawImage(image, x + width, y, -width, height, null);
            case FLIP_VERTICAL:
                g.drawImage(image, x, y + height, width, -height, null);
            case FLIP_H_AND_V:
                g.drawImage(image, x + width, y + height, -width, -height, null);
        }
    }

    public void paintImage(BufferedImage image, int x, int y, int width, int height, int scale, ImageEffect imageEffect) {
        switch (imageEffect) {
            case NONE:
                paintImage(image, x, y, width, height, scale);
            case FLIP_HORIZONTAL:
                g.drawImage(image, x + (width * scale), y, -(width * scale), height * scale, null);
            case FLIP_VERTICAL:
                g.drawImage(image, x, y + (height * scale), width * scale, -(height * scale), null);
            case FLIP_H_AND_V:
                g.drawImage(image, x + (width * scale), y + (height * scale), -(width * scale), -(height * scale), null);
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
}
