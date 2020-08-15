package Game;

import Engine.GameWindow;
import Engine.ScreenManager;

/*
 * The game starts here
 * This class just starts up a GameWindow and attaches the ScreenCoordinator to the Engine's ScreenManager
 * From this point on the ScreenCoordinator class will dictate what the game does
 */
public class Game {

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.startGame();
        ScreenManager screenManager = gameWindow.getScreenManager();
        screenManager.setCurrentScreen(new ScreenCoordinator());
    }
}
