package Game;

import Engine.GameWindow;
import Engine.ScreenManager;

public class Game {
    public static void main(String[] args) {
        new Game();
    }

    private GameWindow gameWindow;

    public Game() {
        gameWindow = new GameWindow();
        gameWindow.startGame();
        ScreenManager screenManager = gameWindow.getScreenManager();
        screenManager.setCurrentGameScreen(new GameScene());
    }
}
