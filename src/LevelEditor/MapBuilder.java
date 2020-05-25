package LevelEditor;

import Engine.Graphics;
import Map.Map;
import Utils.Colors;

import javax.swing.*;
import java.util.HashMap;
import Map.MapTile;
import Map.Tileset;

public class MapBuilder extends JPanel {
    private Map map;
    private Tileset tileset;
    private int[] tiles;
    private int[] movementPermissions;
    private Graphics graphics = new Graphics();
    private HashMap<Integer, MapTile> mapTiles = new HashMap<>();
    JScrollPane tileBuilderScroll;
    private TileBuilder tileBuilder;
    JScrollPane movementPermissionBuilderScroll;
    private MovementPermissionBuilder movementPermissionBuilder;

    public MapBuilder(SelectedTileIndexHolder controlPanelHolder) {
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(205, 5);
        setSize(585, 562);
        setLayout(null);

        tileBuilder = new TileBuilder(controlPanelHolder);
        tileBuilderScroll = new JScrollPane();
        tileBuilderScroll.setViewportView(tileBuilder);
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());

        movementPermissionBuilder = new MovementPermissionBuilder(controlPanelHolder);
        movementPermissionBuilderScroll = new JScrollPane();
        movementPermissionBuilderScroll.setViewportView(movementPermissionBuilder);

        JTabbedPane mapBuilderTabs = new JTabbedPane();
        mapBuilderTabs.setLocation(0, 0);
        mapBuilderTabs.setSize(585, 562);
        mapBuilderTabs.addTab("Tiles", tileBuilderScroll);
        mapBuilderTabs.addTab("Movement Permissions", movementPermissionBuilderScroll);
        add(mapBuilderTabs);
    }

    public void refreshBuilders() {
        tileBuilder.setMap(map);
        tileBuilderScroll.setViewportView(tileBuilder);
        //movementPermissionBuilder.setMap(map);
    }

    public void setMap(Map map) {
        this.map = map;
        tiles = map.getMapTileIndexes();
        movementPermissions = map.getMovementPermissions();
        tileset = map.getTileset();
        tileBuilder.setMap(map);
        //movementPermissionBuilder.setMap(map);
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());
        movementPermissionBuilderScroll.getVerticalScrollBar().setValue(movementPermissionBuilderScroll.getVerticalScrollBar().getMaximum());
    }
}
