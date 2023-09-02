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

    // POWER_SAVER does not hog CPU as much, but can potentially stutter/lag on lower end computers if they cannot handle reaching the target FPS
    // MAX_PERFORMANCE will have the game do whatever it takes to reach the target FPS, even if that means hogging the CPU
    public static final GameLoopType GAME_LOOP_TYPE = GameLoopType.POWER_SAVER;

    // prevents Config from being instantiated
    private Config() { }
}
