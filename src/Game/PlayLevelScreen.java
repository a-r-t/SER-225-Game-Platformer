package Game;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Maps.TestMap;
import Scene.Map;
import Scene.Player;
import Scene.PlayerListener;
import Utils.Timer;

public class PlayLevelScreen extends Screen implements PlayerListener {
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
        this.player.addListener(this);
        this.player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.levelState = LevelState.RUNNING;
    }

    public void update(Keyboard keyboard) {
        switch (levelState) {
            case RUNNING:
                map.update(keyboard, player);
                player.update(keyboard, map);
                break;
            case LEVEL_COMPLETED:
                levelClearedScreen = new LevelClearedScreen();
                levelClearedScreen.initialize();
                screenTimer.setWaitTime(3000);
                levelState = LevelState.LEVEL_WIN_MESSAGE;
                break;
            case LEVEL_WIN_MESSAGE:
                if (screenTimer.isTimeUp()) {
                    levelClearedScreen = null;
                    screenCoordinator.setGameState(GameState.MENU);
                }
                break;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        switch (levelState) {
            case RUNNING:
            case LEVEL_COMPLETED:
            case PLAYER_DEAD:
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                break;
            case LEVEL_WIN_MESSAGE:
                levelClearedScreen.draw(graphicsHandler);
                break;
        }
    }

    public LevelState getLevelState() {
        return levelState;
    }

    @Override
    public void onLevelCompleted() {
        levelState = LevelState.LEVEL_COMPLETED;
    }

    @Override
    public void onDeath() {

    }
}
