package MapEditor;

import Engine.Config;
import Scene.Map;
import Scene.MapTile;
import Utils.Colors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EditorControlPanel extends JPanel {

    private ArrayList<String> mapNames;
    private SelectedTileIndexHolder selectedTileIndexHolder;
    private JComboBox mapNamesComboBox;
    private TilePicker tilePicker;
    private MapBuilder mapBuilder;
    private Map selectedMap;

    public EditorControlPanel(SelectedTileIndexHolder selectedTileIndexHolder, MapBuilder mapBuilder, JFrame parent) {
        setLayout(null);
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(0, 0);
        setSize(200, 600);

        mapNames = EditorMaps.getMapNames();

        this.selectedTileIndexHolder = selectedTileIndexHolder;
        this.mapBuilder = mapBuilder;

        JLabel mapLabel = new JLabel();
        mapLabel.setLocation(5, 0);
        mapLabel.setText("Choose a Map:");
        mapLabel.setSize(100, 40);
        add(mapLabel);

        mapNamesComboBox = new JComboBox();
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
        add(mapNamesComboBox);
        selectedMap = EditorMaps.getMapByName(mapNamesComboBox.getSelectedItem().toString());

        tilePicker = new TilePicker(selectedTileIndexHolder);
        JScrollPane tilePickerScroll = new JScrollPane();
        tilePickerScroll.setViewportView(tilePicker);
        tilePickerScroll.setLocation(5, 78);
        tilePickerScroll.setSize(190, 394);
        add(tilePickerScroll);
        tilePicker.setTileset(getSelectedMap(), getSelectedMap().getTileset());

        JButton setMapDimensionsButton = new JButton();
        setMapDimensionsButton.setSize(190, 40);
        setMapDimensionsButton.setLocation(5, 480);
        setMapDimensionsButton.setText("Set Map Dimensions");
        setMapDimensionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangeMapSizeWindow(getSelectedMap(), parent).show();
                mapBuilder.refreshTileBuilder();
            }
        });
        add(setMapDimensionsButton);

        JButton saveMapButton = new JButton();
        saveMapButton.setSize(190, 40);
        saveMapButton.setLocation(5, 525);
        saveMapButton.setText("Save Map");
        saveMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeSelectedMapToFile();
            }
        });
        add(saveMapButton);
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
