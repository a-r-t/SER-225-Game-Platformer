package Game;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Scene.Map;
import Scene.Player;
import Utils.Timer;

public class PlayLevelScreen {
    protected Map map;
    protected Player player;
    protected LevelState levelState;
    protected Timer screenTimer = new Timer();
    protected LevelClearedScreen levelClearedScreen;

    public void initialize(Map map, Player player) {
        this.map = map;
        map.reset();
        this.player = player;
        player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
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
                screenTimer.setWaitTime(3000);
            }
        } else if (levelState == LevelState.LEVEL_WIN_MESSAGE) {
            if (screenTimer.isTimeUp()) {
                levelState = LevelState.DONE;
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
