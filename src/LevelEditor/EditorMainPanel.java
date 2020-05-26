package LevelEditor;

import javax.swing.*;
import java.awt.*;

public class EditorMainPanel extends JPanel {
    private EditorControlPanel editorControlPanel;
    private MapBuilder mapBuilder;

    public EditorMainPanel(JFrame parent) {
        setLayout(null);
        setBackground(Color.BLACK);
        SelectedTileIndexHolder selectedTileIndexHolder = new SelectedTileIndexHolder();
        mapBuilder = new MapBuilder(selectedTileIndexHolder);
        add(mapBuilder);
        editorControlPanel = new EditorControlPanel(selectedTileIndexHolder, mapBuilder, parent);
        mapBuilder.setMap(editorControlPanel.getSelectedMap());
        add(editorControlPanel);
    }
}
