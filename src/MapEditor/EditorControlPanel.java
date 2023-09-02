package MapEditor;

import Engine.Config;
import Level.Map;
import Level.MapTile;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EditorControlPanel extends JPanel {

    private ArrayList<String> mapNames;
    private JComboBox<String> mapNamesComboBox;
    private TilePicker tilePicker;
    private MapBuilder mapBuilder;
    private Map selectedMap;

    public EditorControlPanel(SelectedTileIndexHolder selectedTileIndexHolder, MapBuilder mapBuilder, JFrame parent) {
        setLayout(new BorderLayout());
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(0, 0);
        setPreferredSize(new Dimension(200, 600));

        mapNames = EditorMaps.getMapNames();

        this.mapBuilder = mapBuilder;

        JPanel mapChoosePanel = new JPanel();
        mapChoosePanel.setLayout(null);
        mapChoosePanel.setPreferredSize(new Dimension(200, 80));
        mapChoosePanel.setBackground(Colors.CORNFLOWER_BLUE);

        JLabel mapLabel = new JLabel();
        mapLabel.setLocation(5, 0);
        mapLabel.setText("Choose a Map:");
        mapLabel.setSize(100, 40);
        mapChoosePanel.add(mapLabel);

        mapNamesComboBox = new JComboBox<String>();
        mapNamesComboBox.setSize(190, 40);
        mapNamesComboBox.setLocation(5, 30);
        mapNames.sort(String::compareToIgnoreCase);
        for (String mapName : mapNames) {
            mapNamesComboBox.addItem(mapName);
        }
        mapNamesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMap();
            }
        });
        mapChoosePanel.add(mapNamesComboBox);
        selectedMap = EditorMaps.getMapByName(mapNamesComboBox.getSelectedItem().toString());

        add(mapChoosePanel, BorderLayout.NORTH);

        tilePicker = new TilePicker(selectedTileIndexHolder);
        JScrollPane tilePickerScroll = new JScrollPane();
        tilePickerScroll.setViewportView(tilePicker);
        tilePickerScroll.setLocation(5, 78);
        tilePickerScroll.setSize(190, 394);
        add(tilePickerScroll, BorderLayout.CENTER);
        tilePicker.setTileset(getSelectedMap(), getSelectedMap().getTileset());


        JPanel mapButtonsPanel = new JPanel();
        mapButtonsPanel.setLayout(null);
        mapButtonsPanel.setPreferredSize(new Dimension(200, 95));
        mapButtonsPanel.setBackground(Colors.CORNFLOWER_BLUE);

        JButton setMapDimensionsButton = new JButton();
        setMapDimensionsButton.setSize(190, 40);
        setMapDimensionsButton.setLocation(5, 5);
        setMapDimensionsButton.setText("Set Map Dimensions");
        setMapDimensionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangeMapSizeWindow(getSelectedMap(), parent).show();
                mapBuilder.refreshTileBuilder();
            }
        });
        mapButtonsPanel.add(setMapDimensionsButton);

        JButton saveMapButton = new JButton();
        saveMapButton.setSize(190, 40);
        saveMapButton.setLocation(5, 50);
        saveMapButton.setText("Save Map");
        saveMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeSelectedMapToFile();
            }
        });
        mapButtonsPanel.add(saveMapButton);

        add(mapButtonsPanel, BorderLayout.SOUTH);
    }

    public Map getSelectedMap() {
        return selectedMap;
    }

    public void writeSelectedMapToFile() {
        Map map = getSelectedMap();
        String fileName = getSelectedMap().getMapFileName();
        try {
            FileWriter fileWriter = new FileWriter(Config.MAP_FILES_PATH + fileName);
            fileWriter.write(map.getWidth() + " " + map.getHeight() + "\n");
            MapTile[] mapTiles = map.getMapTiles();
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {
                    fileWriter.write(String.valueOf(mapTiles[j + map.getWidth() * i].getTileIndex()));
                    if (j < map.getWidth() - 1) {
                        fileWriter.write(" ");
                    } else if (j >= map.getWidth() -1 && i < map.getHeight() - 1) {
                        fileWriter.write("\n");
                    }
                }
            }
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Unable to save map file! That's really not great!");
        }
    }
    public void setMap() {
        selectedMap = EditorMaps.getMapByName(mapNamesComboBox.getSelectedItem().toString());
        tilePicker.setTileset(selectedMap, selectedMap.getTileset());
        mapBuilder.setMap(selectedMap);
    }
}
