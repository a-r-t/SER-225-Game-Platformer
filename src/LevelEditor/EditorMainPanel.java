package LevelEditor;

import javax.swing.*;
import java.awt.*;

public class EditorMainPanel extends JPanel {
    private EditorControlPanel editorControlPanel;
    private MapBuilder mapBuilder;

    public EditorMainPanel() {
        setLayout(null);
        setBackground(Color.BLACK);
        editorControlPanel = new EditorControlPanel();
        add(editorControlPanel);
        mapBuilder = new MapBuilder();
        add(mapBuilder);
    }
}
