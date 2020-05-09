package Game;

import Engine.GameWindow;
import Engine.ScreenManager;

public class Game {
    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.startGame();
        ScreenManager screenManager = gameWindow.getScreenManager();
        screenManager.setCurrentScreen(new Scene());
    }
}
