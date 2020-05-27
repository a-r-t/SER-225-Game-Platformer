package MapEditor;

import Map.Map;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import Engine.Graphics;
import Map.MapTile;

public class TileBuilder extends JPanel {
    private Map map;
    private MapTile hoveredMapTile;
    private SelectedTileIndexHolder controlPanelHolder;
    private Graphics graphics = new Graphics();

    public TileBuilder(SelectedTileIndexHolder controlPanelHolder) {
        setBackground(Colors.MAGENTA);
        setLocation(0, 0);
        setPreferredSize(new Dimension(585, 562));
        this.controlPanelHolder = controlPanelHolder;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredMapTile = null;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                tileSelected(e.getPoint());
            }
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                tileHovered(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                tileHovered(e.getPoint());
                tileSelected(e.getPoint());
            }
        });
    }

    public void setMap(Map map) {
        this.map = map;
        setPreferredSize(new Dimension(map.getWidthPixels(), map.getHeightPixels()));
        repaint();
    }

    public void draw() {
        for (MapTile tile : map.getMapTiles()) {
            tile.draw(graphics);
        }

        if (hoveredMapTile != null) {
            graphics.drawRectangle(
                    hoveredMapTile.getX() + 2,
                    hoveredMapTile.getY() + 2,
                    hoveredMapTile.getScaledWidth() - 5,
                    hoveredMapTile.getScaledHeight() - 5,
                    Color.YELLOW,
                    5
            );
        }
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        graphics.setGraphics((Graphics2D) g);
        draw();
    }

    public void tileSelected(Point selectedPoint) {
        int selectedTileIndex = getSelectedTileIndex(selectedPoint);
        if (selectedTileIndex != -1) {
            MapTile oldMapTile = map.getMapTiles()[selectedTileIndex];
            map.getMapTiles()[selectedTileIndex] = map.getTileset().getTile(controlPanelHolder.getSelectedTileIndex()).build(oldMapTile.getX(), oldMapTile.getY());
        }
        repaint();
    }

    public void tileHovered(Point hoveredPoint) {
        this.hoveredMapTile = getHoveredTile(hoveredPoint);
        repaint();
    }

    protected MapTile getHoveredTile(Point mousePoint) {
        for (MapTile mapTile : map.getMapTiles()) {
            if (isPointInTile(mousePoint, mapTile)) {
                return mapTile;
            }
        }
        return null;
    }

    protected int getSelectedTileIndex(Point mousePoint) {
        MapTile[] mapTiles = map.getMapTiles();
        for (int i = 0; i < mapTiles.length; i++) {
            if (isPointInTile(mousePoint, mapTiles[i])) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isPointInTile(Point point, MapTile tile) {
        return (point.x >= tile.getX() && point.x <= tile.getX() + tile.getScaledWidth() &&
                point.y >= tile.getY() && point.y <= tile.getY() + tile.getScaledHeight());
    }
}
