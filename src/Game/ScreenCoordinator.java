package Game;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;

public class ScreenCoordinator extends Screen {
	protected Screen currentScreen;
	protected GameState gameState;
	protected GameState previousGameState;

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void initialize() {
		gameState = GameState.MENU;
	}

	@Override
	public void update(Keyboard keyboard) {
		do {
			if (previousGameState != gameState) {
				switch(gameState) {
					case MENU:
						currentScreen = new MenuScreen(this);
						break;
					case LEVEL:
						currentScreen = new PlayLevelScreen(this);
						break;
					case CREDITS:
						currentScreen = new CreditsScreen(this);
						break;
				}
				currentScreen.initialize();
			}
			previousGameState = gameState;

			currentScreen.update(keyboard);
		} while (previousGameState != gameState);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		currentScreen.draw(graphicsHandler);
	}
}
