package SpriteFont;

import Engine.GraphicsHandler;

import java.awt.*;

// This class represents a sprite font, which is graphic text (text drawn to the screen as if it were an image)
public class SpriteFont {
	protected String text;
	protected Font font;
	protected float x;
	protected float y;
	protected Color color;
	protected Color outlineColor;
	protected float outlineThickness = 1f;

	public SpriteFont(String text, float x, float y, String fontName, int fontSize, Color color) {
		this.text = text;
		font = new Font(fontName, Font.PLAIN, fontSize);
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public SpriteFont(String text, float x, float y, Font font, Color color) {
		this.text = text;
		this.font = font;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public Font getFont() { return font; }

	public void setText(String text) {
		this.text = text;
	}

	public void setFontName(String fontName) {
		this.font = new Font(fontName, this.font.getStyle(), this.font.getSize());
	}

	public void setFontStyle(int fontStyle) {
		this.font = new Font(font.getFontName(), fontStyle, this.font.getSize());
	}

	public void setFontSize(int size) {
		this.font = new Font(font.getFontName(), this.font.getStyle(), size);
	}

	public void setFont(Font font) { this.font = font; }

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}
	
	public void setOutlineThickness(float outlineThickness) {
		this.outlineThickness = outlineThickness;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void moveX(float dx) {
		x += dx;
	}

	public void moveY(float dy) {
		y += dy;
	}

	public void moveRight(float dx) {
		x += dx;
	}
	
	public void moveLeft(float dx) {
		x -= dx;
	}
	
	public void moveDown(float dy) {
		y += dy;
	}
	
	public void moveUp(float dy) {
		y -= dy;
	}

	private int getAscent(Graphics2D graphics) {
		FontMetrics fm = graphics.getFontMetrics(font);
		return fm.getAscent();
	}

	public void draw(GraphicsHandler graphicsHandler) {
		int ascent = getAscent(graphicsHandler.getGraphics());
		if (outlineColor != null && !outlineColor.equals(color)) {
			graphicsHandler.drawStringWithOutline(text, Math.round(x), Math.round(y) + ascent, font, color, outlineColor, outlineThickness);
		} else {
			graphicsHandler.drawString(text, Math.round(x), Math.round(y) + ascent, font, color);
		}
	}

	// this can be called instead of regular draw to have the text drop to the next line in graphics space on a new line character
	public void drawWithParsedNewLines(GraphicsHandler graphicsHandler, int gapBetweenLines) {
		int ascent = getAscent(graphicsHandler.getGraphics());
		int drawLocationY = Math.round(this.y) + ascent;
		for (String line: text.split("\n")) {
			if (outlineColor != null && !outlineColor.equals(color)) {
				graphicsHandler.drawStringWithOutline(line, Math.round(x), drawLocationY, font, color, outlineColor, outlineThickness);
			} else {
				graphicsHandler.drawString(line, Math.round(x), drawLocationY, font, color);
			}
			drawLocationY += font.getSize() + gapBetweenLines;
		}
	}
}
