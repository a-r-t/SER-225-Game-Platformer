package LevelEditor;

import Map.Map;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;
import Engine.Graphics;
import Map.MapTile;

public class TileBuilder extends JPanel {
    private Map map;
    private int[] tiles;
    private ControlPanelHolder controlPanelHolder;
    private Graphics graphics = new Graphics();

    public TileBuilder(ControlPanelHolder controlPanelHolder) {
        setBackground(Colors.MAGENTA);
        setLocation(0, 0);
        setSize(585, 562);
        setAutoscrolls(true);
        this.controlPanelHolder = controlPanelHolder;
    }

    public void setMap(Map map) {
        this.map = map;
        tiles = map.getMap();
        setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        repaint();
    }

    public void draw() {
        for (MapTile tile : map.getTiles()) {
            tile.draw(graphics);
        }
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        graphics.setGraphics((Graphics2D) g);
        draw();
    }
}
