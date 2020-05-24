package LevelEditor;

import Map.Map;
import Utils.Colors;

import javax.swing.*;

public class MapBuilder extends JPanel {
    private Map map;
    private int[] tiles;
    private int[] movementPermissions;

    public MapBuilder() {
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(205, 5);
        setSize(585, 562);
        setLayout(null);

        JTabbedPane mapBuilderTabs = new JTabbedPane();
        mapBuilderTabs.setLocation(0, 0);
        mapBuilderTabs.setSize(585, 562);
        mapBuilderTabs.addTab("Tiles", new TileBuilder());
        mapBuilderTabs.addTab("Movement Permissions", new MovementPermissionBuilder());
        add(mapBuilderTabs);
    }

    public void setMap(Map map) {
        this.map = map;
        tiles = map.getMap();
        movementPermissions = map.getMovementPermissions();
    }
}
