package LevelEditor;

import GameObject.Rectangle;
import Map.Map;
import Map.Tileset;
import Maps.TestMap;
import Tilesets.CommonTileset;
import Utils.Colors;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;

public class EditorControlPanel extends JPanel {

    private HashMap<String, Map> maps;

    public EditorControlPanel() {
        setLayout(null);
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(0, 0);
        setSize(200, 600);
        maps = loadMaps();

        JLabel mapLabel = new JLabel();
        mapLabel.setLocation(5, 0);
        mapLabel.setText("Choose a Map:");
        mapLabel.setSize(100, 40);
        add(mapLabel);

        JComboBox mapNamesComboBox = new JComboBox();
        mapNamesComboBox.setSize(190, 40);
        mapNamesComboBox.setLocation(5, 30);
        maps.keySet().stream().forEach(tilesetName -> mapNamesComboBox.addItem(tilesetName));
        add(mapNamesComboBox);

        TilePicker tilePicker = new TilePicker();
        add(tilePicker);

        JButton saveMapButton = new JButton();
        saveMapButton.setSize(190, 40);
        saveMapButton.setLocation(5, 525);
        saveMapButton.setText("Save Map");
        add(saveMapButton);

        JCheckBox autoSave = new JCheckBox();
    }

    public HashMap<String, Map> loadMaps() {
        HashMap<String, Map> maps = new HashMap<>();
        maps.put("TestMap", new TestMap(new Rectangle(0,0,0,0)));
        return maps;
    }
}
