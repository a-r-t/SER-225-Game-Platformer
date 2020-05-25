package LevelEditor;

import Map.Map;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;
import Engine.Graphics;

public class MovementPermissionBuilder extends JPanel {
    private int[] tiles;
    private int[] movementPermissions;
    private ControlPanelHolder controlPanelHolder;
    private Graphics graphics = new Graphics();

    public MovementPermissionBuilder(ControlPanelHolder controlPanelHolder) {
        setBackground(Colors.MAGENTA);
        setLocation(0, 0);
        setSize(585, 562);
        setAutoscrolls(true);
        this.controlPanelHolder = controlPanelHolder;
    }

    public void draw() {

    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        graphics.setGraphics((Graphics2D) g);
        draw();
    }
}
