package Game;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Maps.TestMap;
import Scene.Map;
import Scene.Player;
import Utils.Timer;

public class PlayLevelScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected LevelState levelState;
    protected Timer screenTimer = new Timer();
    protected LevelClearedScreen levelClearedScreen;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        this.map = new TestMap();
        map.reset();
        this.player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
        this.player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.levelState = LevelState.RUNNING;
    }

    public void update(Keyboard keyboard) {
        if (levelState == LevelState.RUNNING) {
            map.update(keyboard, player);
            player.update(keyboard, map);
            if (map.isCompleted()) {
                levelState = LevelState.LEVEL_COMPLETED;
            }
        } else if (levelState == LevelState.LEVEL_COMPLETED) {
            if (!player.isOnMapCompletedFinished()) {
                map.setAdjustCamera(false);
                map.update(keyboard, player);
                player.onMapCompleted(map);
            } else {
                levelState = LevelState.LEVEL_WIN_MESSAGE;
                levelClearedScreen = new LevelClearedScreen();
                levelClearedScreen.initialize();
                screenTimer.setWaitTime(3000);
            }
        } else if (levelState == LevelState.LEVEL_WIN_MESSAGE) {
            if (screenTimer.isTimeUp()) {
                screenCoordinator.setGameState(GameState.MENU);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        if (levelState == LevelState.RUNNING || levelState == LevelState.LEVEL_COMPLETED || levelState == LevelState.PLAYER_DEAD) {
            map.draw(graphicsHandler);
            player.draw(graphicsHandler);
        } else if (levelState == LevelState.LEVEL_WIN_MESSAGE) {
            levelClearedScreen.draw(graphicsHandler);
        }
    }

    public LevelState getLevelState() {
        return levelState;
    }
}
