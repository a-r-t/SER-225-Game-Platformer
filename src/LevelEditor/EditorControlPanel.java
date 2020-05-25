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
    JTextField widthTextField;
    JTextField heightTextField;

    public EditorControlPanel(SelectedTileIndexHolder selectedTileIndexHolder, MapBuilder mapBuilder) {
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
        tilePickerScroll.setSize(190, 390);
        add(tilePickerScroll);
        tilePicker.setTileset(getSelectedMap().getTileset());

        JLabel widthLabel = new JLabel("Width: ");
        widthLabel.setSize(50, 20);
        widthLabel.setLocation(5, 472);
        add(widthLabel);

        widthTextField = new JTextField();
        widthTextField.setLocation(55, 472);
        widthTextField.setSize(50, 20);
        add(widthTextField);
        widthTextField.setText(String.valueOf(getSelectedMap().getWidth()));

        JButton changeWidth = new JButton();
        changeWidth.setText("Change");
        changeWidth.setLocation(110, 472);
        changeWidth.setSize(80, 20);
        add(changeWidth);
        changeWidth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newWidth = Integer.parseInt(widthTextField.getText());
                    if (newWidth > 0) {
                        getSelectedMap().setWidth(newWidth);
                        mapBuilder.refreshBuilders();
                    } else {
                        widthTextField.setText(String.valueOf(getSelectedMap().getWidth()));
                        System.out.println("Can't have a negative width value!");
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    System.out.println("Nothing is broken, don't let the red error text scare you, just put in a valid int next time please!");
                    widthTextField.setText(String.valueOf(getSelectedMap().getWidth()));
                }
            }
        });

        JLabel heightLabel = new JLabel("Height: ");
        heightLabel.setSize(50, 20);
        heightLabel.setLocation(5, 496);
        add(heightLabel);

        heightTextField = new JTextField();
        heightTextField.setLocation(55, 496);
        heightTextField.setSize(50, 20);
        add(heightTextField);
        heightTextField.setText(String.valueOf(getSelectedMap().getHeight()));

        JButton changeHeight = new JButton();
        changeHeight.setText("Change");
        changeHeight.setLocation(110, 496);
        changeHeight.setSize(80, 20);
        add(changeHeight);
        changeHeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newHeight = Integer.parseInt(heightTextField.getText());
                    if (newHeight > 0) {
                        getSelectedMap().setHeight(newHeight);
                        mapBuilder.refreshBuilders();
                    } else {
                        heightTextField.setText(String.valueOf(getSelectedMap().getHeight()));
                        System.out.println("Can't have a negative height value!");
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    System.out.println("Nothing is broken, don't let the red error text scare you, just put in a valid int next time please!");
                    heightTextField.setText(String.valueOf(getSelectedMap().getHeight()));
                }
            }
        });

        JButton saveMapButton = new JButton();
        saveMapButton.setSize(190, 40);
        saveMapButton.setLocation(5, 525);
        saveMapButton.setText("Save Map");
        add(saveMapButton);
    }

    public HashMap<String, Map> loadMaps() {
        HashMap<String, Map> maps = new HashMap<>();
        maps.put("TestMap", new TestMap(new Rectangle(0,0,0,0)));
        return maps;
    }

    public Map getSelectedMap() {
        return maps.get(mapNamesComboBox.getSelectedItem());
    }
}
