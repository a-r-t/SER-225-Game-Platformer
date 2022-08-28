package MapEditor;

import Level.Map;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;

public class MapBuilder extends JPanel {
    private Map map;
    private JScrollPane tileBuilderScroll;
    private TileBuilder tileBuilder;
    private JLabel mapWidthLabel;
    private JLabel mapHeightLabel;
    private JLabel hoveredTileIndexLabel;
    private JLabel hoveredTileTypeLabel;

    public MapBuilder(SelectedTileIndexHolder controlPanelHolder) {
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(205, 5);
        // setSize(585, 562);
        setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(null);
        labelPanel.setPreferredSize(new Dimension(200, 30));
        labelPanel.setBackground(Colors.CORNFLOWER_BLUE);
        mapWidthLabel = new JLabel("Width: ");
        mapWidthLabel.setSize(70, 20);
        mapWidthLabel.setLocation(2, 5);
        labelPanel.add(mapWidthLabel);
        mapHeightLabel = new JLabel("Height: ");
        mapHeightLabel.setSize(70, 20);
        mapHeightLabel.setLocation(76, 5);
        labelPanel.add(mapHeightLabel);
        hoveredTileIndexLabel = new JLabel("X: , Y:");
        hoveredTileIndexLabel.setSize(140, 20);
        hoveredTileIndexLabel.setLocation(152, 5);
        labelPanel.add(hoveredTileIndexLabel);
        add(labelPanel, BorderLayout.SOUTH);

        tileBuilder = new TileBuilder(controlPanelHolder, hoveredTileIndexLabel);
        tileBuilderScroll = new JScrollPane();
        tileBuilderScroll.setViewportView(tileBuilder);
        scrollToMaxY();
        tileBuilderScroll.setLocation(0, 0);
        tileBuilderScroll.setSize(585, 546);
        add(tileBuilderScroll, BorderLayout.CENTER);
    }

    public void setMap(Map map) {
        this.map = map;
        refreshTileBuilder();
    }

    public void refreshTileBuilder() {
        tileBuilder.setMap(map);
        tileBuilderScroll.setViewportView(tileBuilder);
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());
        mapWidthLabel.setText("Width: " + map.getWidth());
        mapHeightLabel.setText("Height: " + map.getHeight());
    }

    public void scrollToMaxY() {
        tileBuilderScroll.getVerticalScrollBar().setValue(tileBuilderScroll.getVerticalScrollBar().getMaximum());
    }

    public TileBuilder getTileBuilder() { return tileBuilder; }
}
