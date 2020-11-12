package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Screen;
import Level.Map;
import Maps.TutorialMap;
import Screens.CreditsScreen;
import Screens.MenuScreen;
import Screens.PlayLevelScreen;
import Screens.SelectScreen;
import SpriteFont.SpriteFont;

import java.awt.*;

/*
 * Based on the current game state, this class determines which Screen should be shown
 * There can only be one "currentScreen", although screens can have "nested" screens
 */
public class ScreenCoordinator extends Screen {
	// currently shown Screen
	protected Screen currentScreen = new DefaultScreen();

	//LIVES
	private SpriteFont lives = new SpriteFont("3", 34, 38, "Comic Sans", 30, new Color(238, 232, 170));
	int numLives = 0;
	public void setNumLives(int in) {
		numLives = in;
	}
	public int getNumLives() {
		return numLives;
	}
	public void updateLivesText() {
		lives.setText(String.valueOf(numLives));
	}


	//END LIVES

	// keep track of gameState so ScreenCoordinator knows which Screen to show
	protected GameState gameState;
	protected GameState previousGameState;
	public Map curMap = new TutorialMap();

	public void setMap(Map in) {
		curMap = in;
	}
	public Map getMap() {
		return curMap;
	}

	public GameState getGameState() {
		return gameState;
	}

	// Other Screens can set the gameState of this class to force it to change the currentScreen
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void initialize() {
		// start game off with Menu Screen
		gameState = GameState.MENU;
	}

	@Override
	public void update() {
		do {
			// if previousGameState does not equal gameState, it means there was a change in gameState
			// this triggers ScreenCoordinator to bring up a new Screen based on what the gameState is
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
//					case TUTORIAL:
//						currentScreen = new TutorialMap(this);
//						break;
					case SELECT:
						currentScreen = new SelectScreen(this);
						break;
				}
				currentScreen.initialize();
			}
			previousGameState = gameState;

			// call the update method for the currentScreen
			currentScreen.update();
		} while (previousGameState != gameState);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		// call the draw method for the currentScreen
		currentScreen.draw(graphicsHandler);


		if(gameState == gameState.LEVEL) {
			graphicsHandler.drawImage(ImageLoader.load("heart.png"), 20, 2, 45, 45);
			lives.draw(graphicsHandler);
		}
	}
}
