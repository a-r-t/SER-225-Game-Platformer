package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Scene.EnhancedMapTile;
import Scene.Map;
import Scene.Player;
import Scene.TileType;
import Utils.Point;
import java.util.HashMap;

public class EndLevelBox extends EnhancedMapTile {
    public EndLevelBox(Point location, Map map) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("GoldBox.png"), 16, 16), "DEFAULT", TileType.PASSABLE, map);
    }

    @Override
    public void update(Keyboard keyboard, Player player) {
        super.update(keyboard, player);
        if (intersects(player)) {
            //map.setIsCompleted(true);
        }
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 500)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 500)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 500)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build()
            });
        }};
    }
}
