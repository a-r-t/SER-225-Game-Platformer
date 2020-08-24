package Utils;

// Class is designed to be told how many milliseconds to wait, and then will tell you when time is up
public class Stopwatch {
    private long beforeTime = System.currentTimeMillis();
    private int millisecondsToWait = 0;

    // tell stopwatch how many milliseconds to "time"
    public void setWaitTime(int millisecondsToWait) {
        this.millisecondsToWait = millisecondsToWait;
        beforeTime = System.currentTimeMillis();
    }

    // will return true or false based on if the "time" is up (a specified number of milliseconds have passed)
    public boolean isTimeUp() {
        return System.currentTimeMillis() - beforeTime > millisecondsToWait;
    }

    // reset timer to wait again for specified number of milliseconds
    public void reset() {
        setWaitTime(millisecondsToWait);
    }
}
