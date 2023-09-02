package Engine;

public class GameLoop {
    private GamePanel gamePanel;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Runnable getGameLoopProcess() {
        if (Config.GAME_LOOP_TYPE == GameLoopType.MAX_PERFORMANCE) {
            return getMaxPerformanceModeGameLoop();
        }
        else if (Config.GAME_LOOP_TYPE == GameLoopType.POWER_SAVER){
            return getPowerSaverGameLoop();
        }
        else {
            throw new IllegalStateException("Invalid Game Loop Type");
        }
    }

    // a slightly modified version of the Minecraft game loop
    // designed to SEND IT so that the game runs as best as it can
    private Runnable getMaxPerformanceModeGameLoop() {
        return new Runnable() {
            @Override
            public void run() {
                long previousTime = System.nanoTime();
                double targetTickRate = 1000000000 / (float) Config.TARGET_FPS;
                double delta = 0;
                int frames = 0;
                double lastCycleTime = System.currentTimeMillis();
                boolean running = true;

                while (running) {
                    long currentTime = System.nanoTime();
                    delta += (currentTime - previousTime) / targetTickRate;
                    previousTime = currentTime;

                    if (delta >= 1) {
                        gamePanel.update();
                        gamePanel.repaint();
                        frames++;
                        delta--;

                        if (System.currentTimeMillis() - lastCycleTime >= 1000) {
                            gamePanel.setCurrentFPS(frames);
                            lastCycleTime += 1000;
                            frames = 0;
                        }
                    }
                }
            }
        };
    }

    // a slightly modified version of the game loop found here: https://gist.github.com/keychera/c88794e953b8fb3f3334df93be7a99a6
    // designed to be less CPU intensive
    private Runnable getPowerSaverGameLoop() {
        return new Runnable() {
            @Override
            public void run() {
                final int MAX_UPDATES_BEFORE_RENDER = 5;
                double lastUpdateTime = System.nanoTime();
                double lastRenderTime = System.nanoTime();

                final double TIME_BETWEEN_UPDATES = 1000000000 / (double)Config.TARGET_UPS;;
                final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / (double)Config.TARGET_FPS;

                int lastSecondTime = (int)(lastUpdateTime / 1000000000);

                boolean running = true;
                int frames = 0;

                while (running)
                {
                    double now = System.nanoTime();
                    int updateCount = 0;

                    //Do as many game updates as we need to, potentially playing catchup.
                    while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                        gamePanel.update();
                        lastUpdateTime += TIME_BETWEEN_UPDATES;
                        updateCount++;
                    }

                    // If for some reason an update takes forever, this prevents it from running insane number of catch-ups.
                    if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                        lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                    }

                    frames++;
                    gamePanel.repaint();
                    lastRenderTime = now;

                    int thisSecond = (int) (lastUpdateTime / 1000000000);
                    if (thisSecond > lastSecondTime) {
                        gamePanel.setCurrentFPS(frames);
                        frames = 0;
                        lastSecondTime = thisSecond;
                    }

                    // Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                    while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                        Thread.yield();

                        // This stops the app from consuming a ton of CPU/using a ton of power.
                        // Can potentially cause stuttering if the game is trying to do too many things for the computer running this app to handle
                        try {
                            Thread.sleep(1);
                        } catch(Exception e) {}

                        now = System.nanoTime();
                    }
                }
            }
        };
    }
}
