package LevelEditor;

import Utils.Colors;

import javax.swing.*;

public class EditorWindow {
    private JFrame editorWindow;
    private EditorMainPanel editorMainPanel;

    public EditorWindow() {
        editorWindow = new JFrame("Level Editor");
        editorMainPanel = new EditorMainPanel(editorWindow);
        editorWindow.setContentPane(editorMainPanel);
        editorWindow.setResizable(false);
        editorWindow.setSize(800, 600);
        editorWindow.setLocationRelativeTo(null);
        editorWindow.setVisible(true);
        editorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // it'd be nice if this actually worked more than 1/3rd of the time
    }

}
