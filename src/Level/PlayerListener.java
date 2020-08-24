package Level;

// Other classes can use this interface to listen for events from the Player class
public interface PlayerListener {
    void onLevelCompleted();
    void onDeath();
}
