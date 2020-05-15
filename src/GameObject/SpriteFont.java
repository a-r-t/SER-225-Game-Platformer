package GameObject;

import Engine.Keyboard;
import Engine.Painter;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

public class SpriteFont implements GameObject {
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

	public void setColor(Color color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

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

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}
	
	public void setOutlineThickness(float outlineThickness) {
		this.outlineThickness = outlineThickness;
	}

	public int getX() {
		return Math.round(x);
	}

	public void setX(float x) {
		this.x = x;
	}

	public int getY() {
		return Math.round(y);
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
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
	
	@Override
	public void update(Keyboard keyboard) {

	}

	@Override
	public void draw(Painter painter) {
		if (outlineColor != null && !outlineColor.equals(color)) {
			painter.paintStringWithOutline(text, getX(), getY(), font, color, outlineColor, outlineThickness);
		} else {
			painter.paintString(text, getX(), getY(), font, color);
		}
	}
}
