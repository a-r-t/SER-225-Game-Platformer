package Level;

import java.awt.*;

import Engine.GraphicsHandler;
import SpriteFont.SpriteFont;

public class Textbox {
    protected int x;
    protected int y;
    protected SpriteFont spriteFont;
    protected Color fillColor = Color.white;
    protected Color borderColor = Color.black;
    protected int borderThickness = 1;
    protected int vPadding = 10; // spacing above and below text
    protected int hPadding = 10; // spacing to right and left of text
    protected int gap = 0; // horizontal space between lines of text -- only matters with multiline text

    public Textbox(String text) {
        Font font = new Font("Arial", Font.PLAIN, 12);
        Point textLocation = new Point(x + hPadding + borderThickness, y + vPadding + borderThickness);
        spriteFont = new SpriteFont(text, textLocation.x, textLocation.y,  font, Color.black);
    }

    public void setX(int x) {
        this.x = x;
        refreshSpriteFontLocation();
    }

    public void setY(int y) {
        this.y = y;
        refreshSpriteFontLocation();
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        refreshSpriteFontLocation();
    }

    public void setText(String text) {
        spriteFont.setText(text);
    }

    public void setTextColor(Color textColor) {
        spriteFont.setColor(textColor);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
        refreshSpriteFontLocation();
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

    public void setVPadding(int vPadding) {
        this.vPadding = vPadding;
        refreshSpriteFontLocation();
    }

    public void setHPadding(int hPadding) {
        this.hPadding = hPadding;
        refreshSpriteFontLocation();
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    private void refreshSpriteFontLocation() {
        spriteFont.setLocation(x + hPadding + borderThickness, y + vPadding + borderThickness);
    }

    private Dimension getCalculatedBoxSize(Graphics2D graphics) {
        String[] lines = spriteFont.getText().split("\n");
        int textWidth = getLongestTextWidth(lines, graphics);
        int textHeight = spriteFont.getFont().getSize() * lines.length;
        int boxWidth = textWidth + (hPadding * 2) + (borderThickness * 2);
        int boxHeight = textHeight + (vPadding * 2) + (borderThickness * 2) + ((lines.length - 1) * gap);
        return new Dimension(boxWidth, boxHeight);
    }

    private int getLongestTextWidth(String[] lines, Graphics2D graphics) {
        int longestTextWidth = 0;
        for (String line : lines) {
            int textWidth = graphics.getFontMetrics().stringWidth(line);
            if (textWidth > longestTextWidth) {
                longestTextWidth = textWidth;
            }
        }
        return longestTextWidth;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        Dimension boxSize = getCalculatedBoxSize(graphicsHandler.getGraphics());
        graphicsHandler.drawFilledRectangle(x, y, boxSize.width, boxSize.height, fillColor);
        if (borderColor != null && borderThickness > 0) {
            graphicsHandler.drawRectangle(x, y, boxSize.width, boxSize.height, borderColor, borderThickness);
        }
        spriteFont.drawWithParsedNewLines(graphicsHandler, gap);
    }
}