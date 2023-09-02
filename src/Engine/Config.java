package Engine;

import Utils.Colors;

import java.awt.*;

/*
 * This class holds some constants like window width/height and resource folder locations
 * Tweak these as needed prior to running the application
 */
public class Config {
    public static final int TARGET_FPS = 60;
    public static final String RESOURCES_PATH = "Resources/";
    public static final String MAP_FILES_PATH = "MapFiles/";
    public static final int GAME_WINDOW_WIDTH = 800;
    public static final int GAME_WINDOW_HEIGHT = 605;
    public static final Color TRANSPARENT_COLOR = Colors.MAGENTA;

    // prevents Config from being instantiated
    private Config() { }
}
