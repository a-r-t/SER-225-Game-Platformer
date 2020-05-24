package Engine;

import java.util.HashSet;

public class KeyLocker {
    private HashSet<Key> lockedKeys = new HashSet<>();

    public void lockKey(Key key) {
        lockedKeys.add(key);
    }

    public void unlockKey(Key key) {
        lockedKeys.remove(key);
    }

    public boolean isKeyLocked(Key key) {
        return lockedKeys.contains(key);
    }
}
