package EnhancedMapTiles;

import Engine.GraphicsHandler;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.awt.image.BufferedImage;

// This class is for a horizontal moving platform
// the platform will move back and forth between its start location and end location
// if the player is standing on top of it, the player will be moved the same amount as the platform is moving (so the platform will not slide out from under the player)
public class Coin extends EnhancedMapTile {
    private Point Location;
    private boolean collected = false;


    public Coin(BufferedImage image, Point Location, TileType tileType, float scale) {
        super(image, Location.x, Location.y, tileType, scale, ImageEffect.NONE);
        this.Location = Location;

        this.initialize();
    }


    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void update(Player player) {
        if (intersects(player)) {
            if(collected) {
                //do nothing if already collected
            } else {
                collected = true;
                player.addScore();
                System.out.println(player.getScore());

                //ADD TO SCORE VARIABLE
            }
        }

    }


    public void draw(GraphicsHandler graphicsHandler) {
        if(collected == false) {
            super.draw(graphicsHandler);
        }
    }

}
