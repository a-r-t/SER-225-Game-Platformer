package LevelEditor;

import GameObject.Rectangle;
import Map.Map;
import Maps.TestMap;
import Utils.Colors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class EditorControlPanel extends JPanel {

    private HashMap<String, Map> maps;
    private SelectedTileIndexHolder selectedTileIndexHolder;
    private JComboBox mapNamesComboBox;

    public EditorControlPanel(SelectedTileIndexHolder selectedTileIndexHolder, MapBuilder mapBuilder, JFrame parent) {
        setLayout(null);
        setBackground(Colors.CORNFLOWER_BLUE);
        setLocation(0, 0);
        setSize(200, 600);
        maps = loadMaps();

        this.selectedTileIndexHolder = selectedTileIndexHolder;

        JLabel mapLabel = new JLabel();
        mapLabel.setLocation(5, 0);
        mapLabel.setText("Choose a Map:");
        mapLabel.setSize(100, 40);
        add(mapLabel);

        mapNamesComboBox = new JComboBox();
        mapNamesComboBox.setSize(190, 40);
        mapNamesComboBox.setLocation(5, 30);
        maps.keySet().stream().forEach(tilesetName -> mapNamesComboBox.addItem(tilesetName));
        add(mapNamesComboBox);

        TilePicker tilePicker = new TilePicker(selectedTileIndexHolder);
        JScrollPane tilePickerScroll = new JScrollPane();
        tilePickerScroll.setViewportView(tilePicker);
        tilePickerScroll.setLocation(5, 78);
        tilePickerScroll.setSize(190, 394);
        add(tilePickerScroll);
        tilePicker.setTileset(getSelectedMap().getTileset());

        JButton setMapDimensionsButton = new JButton();
        setMapDimensionsButton.setSize(190, 40);
        setMapDimensionsButton.setLocation(5, 480);
        setMapDimensionsButton.setText("Set Map Dimensions");
        setMapDimensionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangeMapSizeWindow(getSelectedMap(), parent).show();
                mapBuilder.refreshBuilders();
            }
        });
        add(setMapDimensionsButton);

        JButton saveMapButton = new JButton();
        saveMapButton.setSize(190, 40);
        saveMapButton.setLocation(5, 525);
        saveMapButton.setText("Save Map");
        add(saveMapButton);
    }

    public HashMap<String, Map> loadMaps() {
        HashMap<String, Map> maps = new HashMap<>();
        maps.put("TestMap", new TestMap());
        return maps;
    }

    public Map getSelectedMap() {
        return maps.get(mapNamesComboBox.getSelectedItem());
    }
}
