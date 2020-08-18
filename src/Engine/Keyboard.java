package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.HashMap;

/*
 * This class is used throughout the engine for detecting keyboard state
 * This includes if a key is pressed, if a key is not pressed, and if multiple keys are pressed/not pressed at the same time
 */
public class Keyboard {

	// hashmaps keep track of if a key is currently down or up
	private static final HashMap<Integer, Boolean> keyDown = new HashMap<>();
	private static final HashMap<Integer, Boolean> keyUp = new HashMap<>();

	// maps a Key enum type to its key code
	private static final EnumMap<Key, Integer> keyMap = buildKeyMap();

	private static final KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
        	// when key is pressed, set its keyDown state to true and its keyUp state to false
            int keyCode = e.getKeyCode();
            keyDown.put(keyCode, true);
            keyUp.put(keyCode, false);
        }

        @Override
        public void keyReleased(KeyEvent e) {
			// when key is released, set its keyDown state to false and its keyUp state to true
			int keyCode = e.getKeyCode();
            keyDown.put(keyCode, false);
            keyUp.put(keyCode, true);
        }
    };
    
    public static KeyListener getKeyListener() {
    	return keyListener;
    }

    // returns if a key is currently being pressed
    public static boolean isKeyDown(Key key) {
    	return keyDown.getOrDefault(keyMap.get(key), false);
    }

    // returns if a key is currently not being pressed
    public static boolean isKeyUp(Key key) {
    	return keyUp.getOrDefault(keyMap.get(key), true);
    }

    // checks if multiple keys are being pressed at the same time
    public static boolean areKeysDown(Key[] keys) {
    	for (Key key : keys) {
    		if (!keyDown.getOrDefault(keyMap.get(key), false)) {
    			return false;
    		}
    	}
    	return true;
    }

	// checks if multiple keys are not being pressed at the same time
	public static boolean areKeysUp(Key[] keys) {
    	for (Key key : keys) {
    		if (!keyUp.getOrDefault(keyMap.get(key), false)) {
    			return false;
    		}
    	}
    	return true;
    }

    // maps a Key enum type to its keycode
	// Java keycodes were found here: https://stackoverflow.com/a/31637206
    private static EnumMap<Key, Integer> buildKeyMap() {
    	return new EnumMap<Key, Integer>(Key.class)
			{{
				 put(Key.UP, 38);
				 put(Key.DOWN, 40);
				 put(Key.RIGHT, 39);
				 put(Key.LEFT, 37);
				 put(Key.ENTER, 10);
				 put(Key.SHIFT, 16);
				 put(Key.A, 65);
				 put(Key.B, 66);
				 put(Key.C, 67);
				 put(Key.D, 68);
				 put(Key.E, 69);
				 put(Key.F, 70);
				 put(Key.G, 71);
				 put(Key.H, 72);
				 put(Key.I, 73);
				 put(Key.J, 74);
				 put(Key.K, 75);
				 put(Key.L, 76);
				 put(Key.M, 77);
				 put(Key.N, 78);
				 put(Key.O, 79);
				 put(Key.P, 80);
				 put(Key.Q, 81);
				 put(Key.R, 82);
				 put(Key.S, 83);
				 put(Key.T, 84);
				 put(Key.U, 85);
				 put(Key.V, 86);
				 put(Key.W, 87);
				 put(Key.X, 88);
				 put(Key.Y, 89);
				 put(Key.Z, 90);
				 put(Key.ONE, 49);
				 put(Key.TWO, 50);
				 put(Key.THREE, 51);
				 put(Key.FOUR, 52);
				 put(Key.FIVE, 53);
				 put(Key.SIX, 54);
				 put(Key.SEVEN, 55);
				 put(Key.EIGHT, 56);
				 put(Key.NINE, 57);
				 put(Key.ZERO, 48);
				 put(Key.SPACE, 32);
				 put(Key.ESC, 27);
			}};
    }
}
