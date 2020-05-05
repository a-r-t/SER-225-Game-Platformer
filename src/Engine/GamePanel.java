package Engine;

import GameObject.Rectangle;
import GameObject.SpriteFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
	private GameScene gameScene;
	private Timer timer;
	private Rectangle sceneBounds;
	private Keyboard keyboard;
	private boolean doPaint = false;
	private boolean isGamePaused = false;
	private boolean canChangePauseStatus = true;
	private SpriteFont pauseLabel;

	public static final int FPS = 100;

	public GamePanel() {
		super();
		this.setDoubleBuffered(true);

		keyboard = new Keyboard();
		this.addKeyListener(keyboard.getKeyListener());

		gameScene = new GameScene();
		
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
		sceneBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
		setBackground(new Color(100, 149, 237));
		gameScene.initialize(sceneBounds);
		doPaint = true;
	}

	public void startGame() {
		timer.start();
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
			gameScene.update(keyboard);
		}
	}

	public void draw(Graphics2D g) {
		gameScene.draw(g);
		if (isGamePaused) {
			pauseLabel.draw(g);
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(Math.round(sceneBounds.getX1()), Math.round(sceneBounds.getY1()), Math.round(sceneBounds.getWidth()), Math.round(sceneBounds.getHeight()));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (doPaint) {
			draw(g2);
		}
	}
}
