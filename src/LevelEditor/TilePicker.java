package LevelEditor;

import Map.Tileset;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import Engine.Graphics;
import Utils.Colors;

public class TilePicker extends JPanel {

    private Tileset tileset;
    private Graphics graphics = new Graphics();

    public TilePicker() {
        setBackground(new Color(32, 101, 230));
        setLocation(5, 78);
        setSize(190, 440);
        setAutoscrolls(true);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    public void setTileset(Tileset tileset) {
        this.tileset = tileset;
        repaint();
    }

    public void draw() {

    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        graphics.setGraphics((Graphics2D) g);
        draw();
    }
}
