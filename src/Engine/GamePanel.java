package Engine;

import GameObject.Rectangle;
import GameObject.SpriteFont;
import Utils.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
	private ScreenManager screenManager;
	private Timer timer;
	private Keyboard keyboard;
	private boolean doPaint = false;
	private boolean isGamePaused = false;
	private SpriteFont pauseLabel;
	private GraphicsHandler graphicsHandler;
	private KeyLocker keyLocker = new KeyLocker();
	private final Key pauseKey = Key.P;

	public GamePanel() {
		super();
		this.setDoubleBuffered(true);

		keyboard = new Keyboard();
		this.addKeyListener(keyboard.getKeyListener());

		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();
		
		pauseLabel = new SpriteFont("PAUSE", 365, 280, "Comic Sans", 24, Color.white);
		pauseLabel.setOutlineColor(Color.black);
		pauseLabel.setOutlineThickness(2.0f);

		timer = new Timer(1000 / Config.FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(keyboard);
				repaint();
			}
		});
		timer.setRepeats(true);
	}

	public void setupGame() {
		setBackground(Colors.CORNFLOWER_BLUE);
		screenManager.initialize(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		doPaint = true;
	}

	public void startGame() {
		timer.start();
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void update(Keyboard keyboard) {
		if (keyboard.isKeyDown(pauseKey) && !keyLocker.isKeyLocked(pauseKey)) {
			isGamePaused = !isGamePaused;
			keyLocker.lockKey(pauseKey);
		}
		
		if (keyboard.isKeyUp(pauseKey)) {
			keyLocker.unlockKey(pauseKey);
		}
		
		if (!isGamePaused) {
			screenManager.update(keyboard);
		}
	}

	public void draw() {
		screenManager.draw(graphicsHandler);
		if (isGamePaused) {
			pauseLabel.draw(graphicsHandler);
			graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), new Color(0, 0, 0, 100));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		graphicsHandler.setGraphics((Graphics2D) g);
		if (doPaint) {
			draw();
		}
	}
}
