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
    JScrollPane tileBuilderScroll;
    private TileBuilder tileBuilder;

    public MapBuilder(SelectedTileIndexHolder controlPanelHolder) {
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(205, 5);
        setSize(585, 562);
        setLayout(null);

        tileBuilder = new TileBuilder(controlPanelHolder);
        tileBuilderScroll = new JScrollPane();
        tileBuilderScroll.setViewportView(tileBuilder);
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());
        tileBuilderScroll.setLocation(0, 0);
        tileBuilderScroll.setSize(585, 562);
        add(tileBuilderScroll);
    }

    public void refreshTileBuilder() {
        tileBuilder.setMap(map);
        tileBuilderScroll.setViewportView(tileBuilder);
    }

    public void setMap(Map map) {
        this.map = map;
        tileBuilder.setMap(map);
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());
    }
}
