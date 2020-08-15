package Engine;

import java.util.HashSet;

// This class can be used to keep track of "locked" and "unlocked" keys based on the class
// For example, it's often useful to "lock" a key if pressed down until its been released, since holding down a key will continually count as a "key press".
// This way, that "key press" will only be counted once per press.
// This class does NOT do anything to the keyboard class to prevent a key from actually being detected -- that is not advisable as multiple classes may be detecting key presses separately
public class KeyLocker {
    private HashSet<Key> lockedKeys = new HashSet<>();

    // lock a key
    public void lockKey(Key key) {
        lockedKeys.add(key);
    }

    // unlock a key
    public void unlockKey(Key key) {
        lockedKeys.remove(key);
    }

    // check if a key is currently locked
    public boolean isKeyLocked(Key key) {
        return lockedKeys.contains(key);
    }
}
