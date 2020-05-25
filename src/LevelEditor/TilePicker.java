package LevelEditor;

import Map.MapTile;
import Map.Tileset;

import javax.swing.*;
import Map.MapTileBuilder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.Map.Entry;

import Engine.Graphics;
import Utils.Colors;

public class TilePicker extends JPanel {

    private Tileset tileset;
    private Graphics graphics = new Graphics();
    private HashMap<Integer, MapTile> mapTiles = new HashMap<>();
    private int selectedTileIndex = 0;
    private SelectedTileIndexHolder selectedTileIndexHolder;

    public TilePicker(SelectedTileIndexHolder selectedTileIndexHolder) {
        setBackground(Colors.MAGENTA);
        setLocation(0, 0);
        setPreferredSize(new Dimension(187, 387));
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
        setLayout(null);
        this.selectedTileIndexHolder = selectedTileIndexHolder;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tileSelected(e.getPoint());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                tileHovered(e.getPoint());
            }
        });
    }

    public void setTileset(Tileset tileset) {
        this.tileset = tileset;
        HashMap<Integer, MapTileBuilder> mapTileBuilders = this.tileset.createTiles();

        int width = (int)this.getPreferredSize().getWidth() / this.tileset.getScaledSpriteWidth();
        int height = (int)Math.ceil(mapTileBuilders.keySet().size() / (double)width);
        setPreferredSize(new Dimension(144, Math.max(387, height * tileset.getScaledSpriteHeight())));

        Integer[] tileKeys = mapTileBuilders.keySet().toArray(new Integer[mapTileBuilders.keySet().size()]);
        Arrays.sort(tileKeys);
        int currentKeyIndex = 0;
        outerLoop: for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (currentKeyIndex >= tileKeys.length) {
                    break outerLoop;
                }

                int x = j * tileset.getScaledSpriteWidth() + ((j * 5) + 5);
                int y = i * tileset.getScaledSpriteHeight() + ((i * 5) + 5);
                MapTile tile = mapTileBuilders.get(tileKeys[currentKeyIndex]).build(x, y);
                mapTiles.put(currentKeyIndex, tile);
                currentKeyIndex++;
            }
        }
        repaint();
    }

    public void draw() {
        for (MapTile mapTile : mapTiles.values()) {
            mapTile.draw(graphics);
        }

        MapTile selectedTile = mapTiles.get(selectedTileIndex);
        graphics.drawRectangle(
                selectedTile.getX() - 2,
                selectedTile.getY() - 2,
                selectedTile.getScaledWidth() + 4,
                selectedTile.getScaledHeight() + 4,
                Color.YELLOW,
                4
        );
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        graphics.setGraphics((Graphics2D) g);
        draw();
    }

    protected void tileSelected(Point clickedPoint) {
        int selectedTileIndex = getClickedTileIndex(clickedPoint);
        if (selectedTileIndex >= 0) {
            this.selectedTileIndex = selectedTileIndex;
            selectedTileIndexHolder.setSelectedTileIndex(selectedTileIndex);
            repaint();
        }
    }

    protected void tileHovered(Point hoveredPoint) {
        if (isMouseInTileBounds(hoveredPoint)) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    protected boolean isMouseInTileBounds(Point mousePoint) {
        for (MapTile mapTile : mapTiles.values()) {
            if (isPointInTile(mousePoint, mapTile)) {
                return true;
            }
        }
        return false;
    }

    protected int getClickedTileIndex(Point mousePoint) {
        for (Entry<Integer, MapTile> entry : mapTiles.entrySet()) {
            if (isPointInTile(mousePoint, entry.getValue())) {
                return entry.getKey();
            }
        }
        return -1;
    }

    protected boolean isPointInTile(Point point, MapTile tile) {
        return (point.x >= tile.getX() && point.x <= tile.getX() + tile.getScaledWidth() &&
                point.y >= tile.getY() && point.y <= tile.getY() + tile.getScaledHeight());
    }
}
