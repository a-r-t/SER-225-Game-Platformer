package Game;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Maps.TestMap;
import Scene.Map;
import Scene.Player;

import java.awt.*;

public class Scene extends Screen {
	protected MenuScreen menuScreen;
	protected PlayLevelScreen playLevelScreen;
	protected GameState gameState;
	protected GameState previousGameState;

	@Override
	public void initialize() {
		gameState = GameState.MENU;
	}

	@Override
	public void update(Keyboard keyboard) {
		do {
			boolean didGameStateChange = false;
			if (previousGameState != gameState) {
				didGameStateChange = true;
			}
			previousGameState = gameState;

			if (gameState == GameState.MENU) {
				if (didGameStateChange) {
					menuScreen = new MenuScreen();
				} else {
					menuScreen.update(keyboard);
					if (menuScreen.menuItemSelected != -1) {
						if (menuScreen.menuItemSelected == 0) {
							gameState = GameState.LEVEL;
						}
					}
				}
			} else if (gameState == GameState.LEVEL) {
				if (didGameStateChange) {
					Map map = new TestMap();
					Player player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
					playLevelScreen = new PlayLevelScreen();
					playLevelScreen.initialize(map, player);
					gameState = GameState.LEVEL;
				} else {
					playLevelScreen.update(keyboard);
					if (playLevelScreen.getLevelState() == LevelState.DONE) {
						gameState = GameState.MENU;
					}
				}
			}
		} while (previousGameState != gameState);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		if (playLevelScreen != null && gameState == GameState.LEVEL) {
			playLevelScreen.draw(graphicsHandler);
		} else if (menuScreen != null && gameState == GameState.MENU) {
			menuScreen.draw(graphicsHandler);
		}
	}
	
}
