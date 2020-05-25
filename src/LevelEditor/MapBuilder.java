package LevelEditor;

import Engine.Graphics;
import Map.Map;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import Map.MapTile;
import Map.MapTileBuilder;
import Map.Tileset;

public class MapBuilder extends JPanel {
    private Map map;
    private Tileset tileset;
    private int[] tiles;
    private int[] movementPermissions;
    private Graphics graphics = new Graphics();
    private HashMap<Integer, MapTile> mapTiles = new HashMap<>();
    private TileBuilder tileBuilder;
    private MovementPermissionBuilder movementPermissionBuilder;

    public MapBuilder(Map map, ControlPanelHolder controlPanelHolder) {
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(205, 5);
        setSize(585, 562);
        setLayout(null);
        this.map = map;

        tileBuilder = new TileBuilder(controlPanelHolder);
        JScrollPane tileBuilderScroll = new JScrollPane();
        tileBuilderScroll.setViewportView(tileBuilder);

        movementPermissionBuilder = new MovementPermissionBuilder(controlPanelHolder);
        JScrollPane movementPermissionBuilderScroll = new JScrollPane();
        movementPermissionBuilderScroll.setViewportView(movementPermissionBuilder);

        JTabbedPane mapBuilderTabs = new JTabbedPane();
        mapBuilderTabs.setLocation(0, 0);
        mapBuilderTabs.setSize(585, 562);
        mapBuilderTabs.addTab("Tiles", tileBuilderScroll);
        mapBuilderTabs.addTab("Movement Permissions", movementPermissionBuilderScroll);

        tileBuilder.setMap(map);

        add(mapBuilderTabs);
    }

    public void setMap(Map map) {
        this.map = map;
        tiles = map.getMapTileIndexes();
        movementPermissions = map.getMovementPermissions();
        tileset = map.getTileset();
        tileBuilder.setMap(map);
        //movementPermissionBuilder.setMap(map);
    }
}
