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
	protected CreditsScreen creditsScreen;
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
				}
				menuScreen.update(keyboard);
				if (menuScreen.getMenuItemSelected() != -1) {
					if (menuScreen.getMenuItemSelected() == 0) {
						gameState = GameState.LEVEL;
					} else if (menuScreen.getMenuItemSelected() == 1) {
						gameState = GameState.CREDITS;
					}
				}
			} else if (gameState == GameState.LEVEL) {
				if (didGameStateChange) {
					Map map = new TestMap();
					Player player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
					playLevelScreen = new PlayLevelScreen();
					playLevelScreen.initialize(map, player);
					gameState = GameState.LEVEL;
				}
				playLevelScreen.update(keyboard);
				if (playLevelScreen.getLevelState() == LevelState.DONE) {
					gameState = GameState.MENU;
				}
			} else if (gameState == GameState.CREDITS) {
				if (didGameStateChange) {
					creditsScreen = new CreditsScreen();
				}
				creditsScreen.update(keyboard);
				if (creditsScreen.isDone()) {
					gameState = GameState.MENU;
				}
			}
		} while (previousGameState != gameState);
		cleanUpReferences();
	}

	public void cleanUpReferences() {
		if (gameState != GameState.MENU) {
			menuScreen = null;
		}
		if (gameState != GameState.LEVEL) {
			playLevelScreen = null;
		}
		if (gameState != GameState.CREDITS) {
			creditsScreen = null;
		}
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		if (playLevelScreen != null && gameState == GameState.LEVEL) {
			playLevelScreen.draw(graphicsHandler);
		} else if (menuScreen != null && gameState == GameState.MENU) {
			menuScreen.draw(graphicsHandler);
		} else if (creditsScreen != null && gameState == GameState.CREDITS) {
			creditsScreen.draw(graphicsHandler);
		}
	}
	
}
