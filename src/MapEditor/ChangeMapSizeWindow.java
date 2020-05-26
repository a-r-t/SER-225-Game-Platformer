package MapEditor;

import Map.Map;
import Utils.Colors;
import Utils.Direction;

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
        errorMessage.setSize(100, 20);
        errorMessage.setLocation(10, 260);
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
                map.setWidth(newWidth, Direction.LEFT);
            } else if (widthRightRadio.isSelected()) {
                map.setWidth(newWidth, Direction.RIGHT);
            }

            if (heightTopRadio.isSelected()) {
                map.setHeight(newHeight, Direction.UP);
            } else if (heightBottomRadio.isSelected()) {
                map.setHeight(newHeight, Direction.DOWN);
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
}
