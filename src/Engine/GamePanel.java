package Engine;

import GameObject.Rectangle;
import SpriteFont.SpriteFont;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;

/*
 * This is where the game loop starts
 * The JPanel uses a timer to continually call cycles of update and draw
 */
public class GamePanel extends JPanel {
	// loads Screens on to the JPanel
	// each screen has its own update and draw methods defined to handle a "section" of the game.
	private ScreenManager screenManager;

	// used to draw graphics to the panel
	private GraphicsHandler graphicsHandler;

	private boolean doPaint = false;
	private boolean isGamePaused = false;
	private SpriteFont pauseLabel;
	private KeyLocker keyLocker = new KeyLocker();
	private final Key pauseKey = Key.P;
	private Thread gameLoop;

	// if true, the game's actual FPS will be printed to the console every so often
	private Key showFPSKey = Key.G;
	private SpriteFont fpsDisplayLabel;
	private boolean showFPS = false;
	private int currentFPS;
	private int frameCount = 0;

	// The JPanel and various important class instances are setup here
	public GamePanel() {
		super();
		this.setDoubleBuffered(true);

		// attaches Keyboard class's keyListener to this JPanel
		this.addKeyListener(Keyboard.getKeyListener());

		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();

		pauseLabel = new SpriteFont("PAUSE", 365, 280, "Comic Sans", 24, Color.white);
		pauseLabel.setOutlineColor(Color.black);
		pauseLabel.setOutlineThickness(2.0f);

		fpsDisplayLabel = new SpriteFont("FPS", 4, 3, "Comic Sans", 12, Color.black);

		currentFPS = Config.TARGET_FPS;

		// this game loop code will run in a separate thread from the rest of the program
		// will continually update the game's logic and repaint the game's graphics
		gameLoop = new Thread(new Runnable() {
			@Override
			public void run() {
				//This value would probably be stored elsewhere.
				final double GAME_HERTZ = 60;
				//Calculate how many ns each frame should take for our target game hertz.
				final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
				//At the very most we will update the game this many times before a new render.
				//If you're worried about visual hitches more than perfect timing, set this to 1.
				final int MAX_UPDATES_BEFORE_RENDER = 5;
				//We will need the last update time.
				double lastUpdateTime = System.nanoTime();
				//Store the last time we rendered.
				double lastRenderTime = System.nanoTime();

				//If we are able to get as high as this FPS, don't render again.
				final double TARGET_FPS = 60;
				final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

				//Simple way of finding FPS.
				int lastSecondTime = (int) (lastUpdateTime / 1000000000);

				while (true) {
					double now = System.nanoTime();
					int updateCount = 0;


					//Do as many game updates as we need to, potentially playing catchup.
					while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
						update();
						lastUpdateTime += TIME_BETWEEN_UPDATES;
						updateCount++;
					}

					//If for some reason an update takes forever, we don't want to do an insane number of catchups.
					//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
					if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {

						lastUpdateTime = now - TIME_BETWEEN_UPDATES;
					}

					//Render. To do so, we need to calculate interpolation for a smooth render.
					float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
					repaint();
					lastRenderTime = now;

					//Update the frames we got.
					int thisSecond = (int) (lastUpdateTime / 1000000000);
					if (thisSecond > lastSecondTime) {
						currentFPS = frameCount;
						frameCount = 0;
						lastSecondTime = thisSecond;
					}

					//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
					while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
						Thread.yield();

						//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
						//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
						//FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
						try {
							Thread.sleep(1);
						} catch (Exception e) {
						}

						now = System.nanoTime();
					}
				}
			}
		});
	}

	// this is called later after instantiation, and will initialize screenManager
	// this had to be done outside of the constructor because it needed to know the JPanel's width and height, which aren't available in the constructor
	public void setupGame() {
		setBackground(Colors.CORNFLOWER_BLUE);
		screenManager.initialize(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		doPaint = true;
	}

	// this starts the timer (the game loop is started here
	public void startGame() {
		gameLoop.start();
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void update() {
		updatePauseState();
		updateShowFPSState();

		if (!isGamePaused) {
			screenManager.update();
		}
	}

	private void updatePauseState() {
		if (Keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {
			isGamePaused = !isGamePaused;
			keyLocker.lockKey(pauseKey);
		}

		if (Keyboard.isKeyUp(pauseKey)) {
			keyLocker.unlockKey(pauseKey);
		}
	}

	private void updateShowFPSState() {
		if (Keyboard.isKeyDown(showFPSKey) && !keyLocker.isKeyLocked(showFPSKey)) {
			showFPS = !showFPS;
			keyLocker.lockKey(showFPSKey);
		}

		if (Keyboard.isKeyUp(showFPSKey)) {
			keyLocker.unlockKey(showFPSKey);
		}

		fpsDisplayLabel.setText("FPS: " + currentFPS);
	}

	public void draw() {
		screenManager.draw(graphicsHandler);

		// if game is paused, draw pause gfx over Screen gfx
		if (isGamePaused) {
			pauseLabel.draw(graphicsHandler);
			graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), new Color(0, 0, 0, 100));
		}

		if (showFPS) {
			fpsDisplayLabel.draw(graphicsHandler);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// every repaint call will schedule this method to be called
		// when called, it will setup the graphics handler and then call this class's draw method
		graphicsHandler.setGraphics((Graphics2D) g);
		if (doPaint) {
			frameCount++;
			draw();
		}
	}
}
