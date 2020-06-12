package Utils;

public class Timer {
    private long beforeTime = System.currentTimeMillis();
    private int millisecondsToWait = 0;

    public void setWaitTime(int millisecondsToWait) {
        this.millisecondsToWait = millisecondsToWait;
        beforeTime = System.currentTimeMillis();
    }

    public boolean isTimeUp() {
        return System.currentTimeMillis() - beforeTime > millisecondsToWait;
    }

    public void reset() {
        setWaitTime(millisecondsToWait);
    }
}
