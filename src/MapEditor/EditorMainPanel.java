package MapEditor;

import javax.swing.*;
import java.awt.*;

public class EditorMainPanel extends JPanel {
    private EditorControlPanel editorControlPanel;
    private MapBuilder mapBuilder;

    public EditorMainPanel(JFrame parent) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        SelectedTileIndexHolder selectedTileIndexHolder = new SelectedTileIndexHolder();
        mapBuilder = new MapBuilder(selectedTileIndexHolder);
        add(mapBuilder, BorderLayout.CENTER);
        editorControlPanel = new EditorControlPanel(selectedTileIndexHolder, mapBuilder, parent);
        mapBuilder.setMap(editorControlPanel.getSelectedMap());;
        add(editorControlPanel, BorderLayout.WEST);
    }

    public EditorControlPanel getEditorControlPanel() { return editorControlPanel; }
    public MapBuilder getMapBuilder() { return mapBuilder; }
}
