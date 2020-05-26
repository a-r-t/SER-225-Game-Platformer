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
	private Rectangle windowBounds;
	private Keyboard keyboard;
	private boolean doPaint = false;
	private boolean isGamePaused = false;
	private SpriteFont pauseLabel;
	private Graphics graphics;
	private KeyLocker keyLocker = new KeyLocker();
	private final Key pauseKey = Key.P;

	public GamePanel() {
		super();
		this.setDoubleBuffered(true);

		keyboard = new Keyboard();
		this.addKeyListener(keyboard.getKeyListener());

		graphics = new Graphics();

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
		windowBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
		setBackground(Colors.CORNFLOWER_BLUE);
		screenManager.initialize(windowBounds);
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
		screenManager.draw(graphics);
		if (isGamePaused) {
			pauseLabel.draw(graphics);
			graphics.drawFilledRectangle(windowBounds.getX(), windowBounds.getY(), windowBounds.getWidth(), windowBounds.getHeight(), new Color(0, 0, 0, 100));
		}
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		graphics.setGraphics((Graphics2D) g);
		if (doPaint) {
			draw();
		}
	}
}
