package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.HashMap;

public class Keyboard {
	private HashMap<Integer, Boolean> keyDown = new HashMap<>();
	private HashMap<Integer, Boolean> keyUp = new HashMap<>();
	private EnumMap<Key, Integer> keyMap;
	
	public Keyboard() {
		buildKeyMap();
	}
	
	private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            keyDown.put(keyCode, true);
            keyUp.put(keyCode, false);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            keyDown.put(keyCode, false);
            keyUp.put(keyCode, true);
        }
    };
    
    public KeyListener getKeyListener() {
    	return keyListener;
    }
    
    public boolean isKeyDown(Key key) {
    	return keyDown.getOrDefault(keyMap.get(key), false);
    }
    
    public boolean isKeyUp(Key key) {
    	return keyUp.getOrDefault(keyMap.get(key), true);
    }
    
    public boolean areKeysDown(Key[] keys) {
    	for (Key key : keys) {
    		if (!keyDown.getOrDefault(keyMap.get(key), false)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public boolean areKeysUp(Key[] keys) {
    	for (Key key : keys) {
    		if (!keyUp.getOrDefault(keyMap.get(key), false)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    private void buildKeyMap() {
    	keyMap = new EnumMap<Key, Integer>(Key.class)
 		{{
 		     put(Key.UP, 38);
 		     put(Key.DOWN, 40);
 		     put(Key.RIGHT, 39);
 		     put(Key.LEFT, 37);
 		     put(Key.ENTER, 13);
 		     put(Key.LSHIFT, 16);
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
 		}};
    }
}
