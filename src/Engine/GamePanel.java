package Engine;

import GameObject.Rectangle;
import GameObject.SpriteFont;

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
	private boolean canChangePauseStatus = true;
	private SpriteFont pauseLabel;
	private Graphics graphics;

	public static final int FPS = 100;

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

		timer = new Timer(1000 / FPS, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(keyboard);
				repaint();
			}
		});
		timer.setRepeats(true);
	}

	public void setupGame() {
		windowBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
		setBackground(new Color(100, 149, 237));
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
		if (keyboard.isKeyDown(Key.P) && canChangePauseStatus) {
			isGamePaused = !isGamePaused;
			canChangePauseStatus = false;
		}
		
		if (keyboard.isKeyUp(Key.P)) {
			canChangePauseStatus = true;
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
