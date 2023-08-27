package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TestMap;
import Players.Cat;
import SpriteFont.SpriteFont;
import Utils.Point;
import Utils.Stopwatch;

import java.awt.*;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected Stopwatch screenTimer = new Stopwatch();
    protected LevelClearedScreen levelClearedScreen;
    protected LevelLoseScreen levelLoseScreen;
    protected LoadingScreen loadingScreen;
    protected boolean levelCompletedStateChangeStart;
    protected SpriteFont loadingLabel;
    protected Thread initializer;
    protected boolean initializerFinished;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        this.playLevelScreenState = PlayLevelScreenState.LOADING;
        this.initializerFinished = false;

        loadingScreen = new LoadingScreen(this);

        // loads all assets/sets up game to be played
        // this is done in a separate thread so it does not block the rest of the program from running,
        // since this can take a variable amount of time based on which assets need to be loaded/cpu power
        this.initializer = new Thread(new Runnable() {
            @Override
            public void run() {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.setWaitTime(1000); // time when one second has passed

                // define/setup map
                map = new TestMap();

                // setup player
                player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
                player.setMap(map);
                player.addListener(PlayLevelScreen.this);
                Point playerStartPosition = map.getPlayerStartPosition();
                player.setLocation(playerStartPosition.x, playerStartPosition.y);

                // setup win and lose screens
                levelClearedScreen = new LevelClearedScreen();
                levelLoseScreen = new LevelLoseScreen(PlayLevelScreen.this);

                // establish player's position in map, adjust camera to point to player
                player.update();
                map.update(player);

                // if the one second waited for has not passed yet,
                // this ensures that the loading screen stays up for at least the full one second
                if (!stopwatch.isTimeUp()) {
                    try{
                        Thread.sleep(stopwatch.getTimeRemaining());
                    } catch(InterruptedException e){ }
                }

                // this flag is used to tell update method that initializing job has finished, so it knows it can proceed with running the actual game level
                initializerFinished = true;
            }
        });
        initializer.start();
    }

    public void update() {
        // based on screen state, perform specific actions
        switch (playLevelScreenState) {
            // if level is "loading" assets but initializerFinished flag is set to true, loading has been completed and game can be set to "running"
            case LOADING:
                if (initializerFinished) {
                    playLevelScreenState = PlayLevelScreenState.RUNNING;
                }
                break;
            // if level is "running" update player and map to keep game logic for the platformer level going
            case RUNNING:
                player.update();
                map.update(player);
                break;
            // if level has been completed, bring up level cleared screen
            case LEVEL_COMPLETED:
                if (levelCompletedStateChangeStart) {
                    screenTimer.setWaitTime(2500);
                    levelCompletedStateChangeStart = false;
                } else {
                    levelClearedScreen.update();
                    if (screenTimer.isTimeUp()) {
                        goBackToMenu();
                    }
                }
                break;
            // wait on level lose screen to make a decision (either resets level or sends player back to main menu)
            case LEVEL_LOSE:
                levelLoseScreen.update();
                break;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case LOADING:
                loadingScreen.draw(graphicsHandler);
                break;
            case RUNNING:
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                levelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE:
                levelLoseScreen.draw(graphicsHandler);
                break;
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    @Override
    public void onLevelCompleted() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_COMPLETED) {
            playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
            levelCompletedStateChangeStart = true;
        }
    }

    @Override
    public void onDeath() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_LOSE) {
            playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE;
        }
    }

    public void resetLevel() {
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        LOADING, RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
