package Level;

import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import Enemies.FriendlyFire;

// This class is a base class for all enemies in the game -- all enemies should extend from it
public class Enemy extends MapEntity {

	protected MapEntityStatus status;

	public Enemy(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
		super(x, y, spriteSheet, startingAnimation);
		status = super.getMapEntityStatus();
	}

	public Enemy(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
		super(x, y, animations, startingAnimation);
		status = super.getMapEntityStatus();
	}

	public Enemy(BufferedImage image, float x, float y, String startingAnimation) {
		super(image, x, y, startingAnimation);
		status = super.getMapEntityStatus();
	}

	public Enemy(BufferedImage image, float x, float y) {
		super(image, x, y, null);
		status = super.getMapEntityStatus();
	}

	public Enemy(BufferedImage image, float x, float y, float scale) {
		super(image, x, y, scale, null);
		status = super.getMapEntityStatus();
	}

	public Enemy(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
		super(image, x, y, scale, imageEffect, null);
		status = super.getMapEntityStatus();
	}

	public Enemy(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
		super(image, x, y, scale, imageEffect, bounds);
		status = super.getMapEntityStatus();
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	public void update(Player player) {
		super.update();
		if (intersects(player)) {
			touchedPlayer(player);
		} else if (player.currentFireball != null) {
			if (intersects(player.getFire())) {
				this.setMapEntityStatus(MapEntityStatus.REMOVED);
			}
		}
	}

	// A subclass can override this method to specify what it does when it touches
	// the player
	public void touchedPlayer(Player player) {
		player.hurtPlayer(this);
	}

	public void hurtEnemy(MapEntity mapEntity) {
		if (mapEntity instanceof FriendlyFire) {
			mapEntity.setMapEntityStatus(MapEntityStatus.REMOVED);
		}
	}
}
