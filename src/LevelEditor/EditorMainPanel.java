package LevelEditor;

import javax.swing.*;
import java.awt.*;

public class EditorMainPanel extends JPanel {
    private EditorControlPanel editorControlPanel;
    private MapBuilder mapBuilder;

    public EditorMainPanel() {
        setLayout(null);
        setBackground(Color.BLACK);
        ControlPanelHolder controlPanelHolder = new ControlPanelHolder();
        editorControlPanel = new EditorControlPanel(controlPanelHolder);
        add(editorControlPanel);
        mapBuilder = new MapBuilder(editorControlPanel.getSelectedMap(), controlPanelHolder);
        add(mapBuilder);
    }
}
