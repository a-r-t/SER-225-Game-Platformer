package MapEditor;

import Map.Map;
import Utils.Colors;
import Map.MapTile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeMapSizeWindow {
    private JDialog changeMapSizeWindow;
    private JTextField widthTextField;
    private JTextField heightTextField;
    private JRadioButton widthLeftRadio;
    private JRadioButton widthRightRadio;
    private JRadioButton heightTopRadio;
    private JRadioButton heightBottomRadio;
    private JLabel errorMessage;
    private Map map;

    public ChangeMapSizeWindow(Map map, JFrame parent) {
        this.map = map;

        changeMapSizeWindow = new JDialog();
        changeMapSizeWindow.setResizable(false);
        changeMapSizeWindow.setSize(300, 310);
        changeMapSizeWindow.setTitle("Change Map Size");
        changeMapSizeWindow.setModal(true);
        changeMapSizeWindow.setLocationRelativeTo(parent);
        JPanel mainPanel = new JPanel(null);
        changeMapSizeWindow.setContentPane(mainPanel);
        mainPanel.setBackground(Colors.CORNFLOWER_BLUE);

        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setLocation(10, 10);
        widthLabel.setSize(40, 20);
        widthTextField = new JTextField();
        widthTextField.setLocation(55, 10);
        widthTextField.setSize(50, 20);
        widthTextField.setText(String.valueOf(map.getWidth()));
        mainPanel.add(widthLabel);
        mainPanel.add(widthTextField);

        JLabel widthLeftOrRightLabel = new JLabel("Width change from left or right side of map?");
        widthLeftOrRightLabel.setLocation(10, 40);
        widthLeftOrRightLabel.setSize(300, 20);
        ButtonGroup widthLeftOrRightRadioGroup = new ButtonGroup();
        widthLeftRadio = new JRadioButton("Left");
        widthLeftRadio.setLocation(10, 70);
        widthLeftRadio.setSize(60, 20);
        widthLeftRadio.setBackground(Colors.CORNFLOWER_BLUE);
        widthRightRadio = new JRadioButton("Right");
        widthRightRadio.setLocation(80, 70);
        widthRightRadio.setSize(60, 20);
        widthRightRadio.setBackground(Colors.CORNFLOWER_BLUE);
        widthRightRadio.setSelected(true);

        widthLeftOrRightRadioGroup.add(widthLeftRadio);
        widthLeftOrRightRadioGroup.add(widthRightRadio);
        mainPanel.add(widthLeftOrRightLabel);
        mainPanel.add(widthLeftRadio);
        mainPanel.add(widthRightRadio);

        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setLocation(10, 110);
        heightLabel.setSize(40, 20);
        heightTextField = new JTextField();
        heightTextField.setLocation(55, 110);
        heightTextField.setSize(50, 20);
        heightTextField.setText(String.valueOf(map.getHeight()));
        mainPanel.add(heightLabel);
        mainPanel.add(heightTextField);


        JLabel heightTopOrBottomLabel = new JLabel("Height change from top or bottom side of map?");
        heightTopOrBottomLabel.setLocation(10, 140);
        heightTopOrBottomLabel.setSize(300, 20);
        ButtonGroup heightTopOrBottomRadioGroup = new ButtonGroup();
        heightTopRadio = new JRadioButton("Top");
        heightTopRadio.setLocation(10, 170);
        heightTopRadio.setSize(60, 20);
        heightTopRadio.setBackground(Colors.CORNFLOWER_BLUE);
        heightBottomRadio = new JRadioButton("Bottom");
        heightBottomRadio.setLocation(80, 170);
        heightBottomRadio.setSize(70, 20);
        heightBottomRadio.setBackground(Colors.CORNFLOWER_BLUE);
        heightBottomRadio.setSelected(true);
        heightTopOrBottomRadioGroup.add(heightTopRadio);
        heightTopOrBottomRadioGroup.add(heightBottomRadio);
        mainPanel.add(heightTopOrBottomLabel);
        mainPanel.add(heightTopRadio);
        mainPanel.add(heightBottomRadio);

        JButton okButton = new JButton();
        okButton.setSize(120, 40);
        okButton.setLocation(25, 220);
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMapSize();
            }
        });
        mainPanel.add(okButton);

        JButton cancelButton = new JButton();
        cancelButton.setSize(120, 40);
        cancelButton.setLocation(150, 220);
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        mainPanel.add(cancelButton);

        errorMessage = new JLabel("");
        errorMessage.setSize(300, 20);
        errorMessage.setLocation(26, 260);
        errorMessage.setForeground(new Color(154, 0, 0));
        mainPanel.add(errorMessage);

    }

    public void changeMapSize() {
        if (!isWidthInputValid() || !isHeightInputValid()) {
            errorMessage.setText("Inputs must be numbers and not negative!");
        } else {
            errorMessage.setText("");
            int newWidth = Integer.parseInt(widthTextField.getText());
            int newHeight = Integer.parseInt(heightTextField.getText());

            if (widthLeftRadio.isSelected()) {
                resizeMapWidth(newWidth, MapWidthDirection.LEFT);
            } else if (widthRightRadio.isSelected()) {
                resizeMapWidth(newWidth, MapWidthDirection.RIGHT);
            }

            if (heightTopRadio.isSelected()) {
                resizeMapHeight(newHeight, MapHeightDirection.TOP);
            } else if (heightBottomRadio.isSelected()) {
                resizeMapHeight(newHeight, MapHeightDirection.BOTTOM);
            }

            this.close();
        }
    }

    public boolean isWidthInputValid() {
        return isStringAPositiveNumber(widthTextField.getText());
    }

    public boolean isHeightInputValid() {
        return isStringAPositiveNumber(heightTextField.getText());
    }

    public boolean isStringAPositiveNumber(String s) {
        try {
            int number = Integer.parseInt(s);
            return number >= 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void show() {
        changeMapSizeWindow.setVisible(true);
    }

    public void close() {
        changeMapSizeWindow.setVisible(false);
        changeMapSizeWindow.dispose();
    }

    public enum MapWidthDirection {
        LEFT, RIGHT
    }

    public enum MapHeightDirection {
        TOP, BOTTOM
    }

    public void resizeMapWidth(int newWidth, MapWidthDirection mapWidthDirection) {
        int oldWidth = map.getWidth();
        MapTile[] mapTilesSizeChange = new MapTile[map.getHeight() * newWidth];

        if (mapWidthDirection == MapWidthDirection.RIGHT) {
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < oldWidth; j++) {
                    if (j < newWidth) {
                        mapTilesSizeChange[j + newWidth * i] = map.getMapTiles()[j + oldWidth * i];
                    }
                }
            }
        } else /* if (mapHeightDirection == MapHeightDirection.BOTTOM) */ {
            int difference = newWidth - oldWidth;
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = oldWidth - 1; j >= 0; j--) {
                    if (j + difference >= 0) {
                        MapTile tile = map.getMapTiles()[j + oldWidth * i];
                        mapTilesSizeChange[j + difference + newWidth * i] = tile;
                        tile.moveX(map.getTileset().getScaledSpriteWidth() * difference);
                    }
                }
            }
        }

        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < newWidth; j++) {
                if (mapTilesSizeChange[j + newWidth * i] == null) {
                    mapTilesSizeChange[j + newWidth * i] = map.getTileset().getDefaultTile()
                            .build(j * map.getTileset().getScaledSpriteWidth(), i * map.getTileset().getScaledSpriteHeight());
                }
            }
        }

        map.setMapTiles(mapTilesSizeChange);
        map.setWidth(newWidth);
    }

    public void resizeMapHeight(int newHeight, MapHeightDirection mapHeightDirection) {
        int oldHeight = map.getHeight();
        MapTile[] mapTilesSizeChange = new MapTile[newHeight * map.getWidth()];

        if (mapHeightDirection == MapHeightDirection.BOTTOM) {
            for (int i = 0; i < oldHeight; i++) {
                if (i < newHeight) {
                    for (int j = 0; j < map.getWidth(); j++) {
                        mapTilesSizeChange[j + map.getWidth() * i] = map.getMapTiles()[j + map.getWidth() * i];
                    }
                }
            }
        } else /* if (mapHeightDirection == MapHeightDirection.TOP) */ {
            int difference = newHeight - oldHeight;
            for (int i = oldHeight - 1; i >= 0; i--) {
                if (i + difference >= 0) {
                    for (int j = 0; j < map.getWidth(); j++) {
                        MapTile tile = map.getMapTiles()[j + map.getWidth() * i];
                        mapTilesSizeChange[j + map.getWidth() * (i + difference)] = tile;
                        tile.moveY(map.getTileset().getScaledSpriteHeight() * difference);
                    }
                }
            }
        }

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                if (mapTilesSizeChange[j + map.getWidth() * i] == null) {
                    mapTilesSizeChange[j + map.getWidth() * i] = map.getTileset().getDefaultTile()
                            .build(j * map.getTileset().getScaledSpriteWidth(), i * map.getTileset().getScaledSpriteHeight());
                }
            }
        }

        map.setMapTiles(mapTilesSizeChange);
        map.setHeight(newHeight);
    }
}
