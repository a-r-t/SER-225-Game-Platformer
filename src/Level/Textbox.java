package Level;

import java.awt.*;

import Engine.GraphicsHandler;
import SpriteFont.SpriteFont;

public class Textbox {
    private int x;
    private int y;
    private Color fillColor = Color.white;
    private Color borderColor = Color.black;
    private int borderThickness = 1;
    private SpriteFont spriteFont;
    private int padding = 10;

    public Textbox(String text) {
        Font font = new Font("Arial", Font.PLAIN, 12);
        Point textLocation = new Point(x + font.getSize() + padding, y + font.getSize() + padding);
        spriteFont = new SpriteFont(text, textLocation.x, textLocation.y,  font, Color.black);
    }

    private Dimension getCalculatedBoxSize(GraphicsHandler graphicsHandler) {
        int textWidth = graphicsHandler.getGraphics().getFontMetrics().stringWidth(spriteFont.getText());
        int textLines = spriteFont.getText().split("\n").length;
        int textHeight = spriteFont.getFont().getSize() * textLines;
        int width = textWidth + (padding * 2) + (borderThickness * 2);
        int height = textHeight + (padding * 2) + (borderThickness * 2) + ((textLines - 1) * spriteFont.getGap());
        return new Dimension(width, height);
    }

    public void setX(int x) {
        this.x = x;
        spriteFont.setX(x + padding);
    }

    public void setY(int y) {
        this.y = y;
        spriteFont.setY(y + padding);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        spriteFont.setLocation(x + padding, y + padding);
    }

    public void setText(String text) {
        spriteFont.setText(text);
    }

    public void setTextColor(Color textColor) {
        spriteFont.setColor(textColor);
    }

    public void setTextFont(Font textFont) {
        spriteFont.setFont(textFont);
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setFont(Font font) {
        spriteFont.setFont(font);
    }

    public void setFontSize(int size) {
        spriteFont.setFontSize(size);
    }

    public void setFontStyle(int style) {
        spriteFont.setFontStyle(style);
    }

    public void setFontName(String name) {
        spriteFont.setFontName(name);
    }

    public void setGap(int gap) {
        spriteFont.setGap(gap);
    }

    public void draw(GraphicsHandler graphicsHandler) {
        Dimension boxSize = getCalculatedBoxSize(graphicsHandler);
        graphicsHandler.drawFilledRectangle(x - borderThickness, y - borderThickness, boxSize.width, boxSize.height, fillColor);
        if (borderColor != null && borderThickness > 0) {
            graphicsHandler.drawRectangle(x - borderThickness, y - borderThickness, boxSize.width, boxSize.height, borderColor, borderThickness);
        }
        spriteFont.drawWithParsedNewLines(graphicsHandler);
    }
}
