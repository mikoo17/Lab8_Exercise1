package com.example.lab8_exercise1;

import android.os.Handler;
import android.os.Looper;
public class GameLoop {
    private final GameSurfaceView gameSurfaceView;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean running = false;
    private int frameCount = 0;
    private int tickCount = 0;
    private long lastTime = System.currentTimeMillis();
    private final int FRAMES_PER_SECOND = 60;
    private final int TICKS_PER_SECOND = 30;
    private long tickTime;
    public GameLoop(GameSurfaceView gameSurfaceView) {
        this.gameSurfaceView = gameSurfaceView;
    }
    public void start() {
        running = true;
        tickTime = System.currentTimeMillis();
        handler.post(gameLoop);
    }
    public void stop() {
        running = false;
        handler.removeCallbacks(gameLoop);
    }
    private final Runnable gameLoop = new Runnable() {
        @Override
        public void run() {
            if (!running) return;
            long currentTime = System.currentTimeMillis();
            gameSurfaceView.updateGameLogic();
            frameCount++;
            if (currentTime - tickTime >= 1000 / TICKS_PER_SECOND) {
                tickCount++;
                tickTime = currentTime;
            }
            if (currentTime - lastTime >= 1000) {
                if (gameSurfaceView.callbacks != null) {
                    gameSurfaceView.callbacks.onFrameRendered(frameCount);
                    gameSurfaceView.callbacks.onGameUpdated(tickCount);
                }
                frameCount = 0;
                tickCount = 0;
                lastTime = currentTime;
            }
            handler.postDelayed(gameLoop, 1000 / FRAMES_PER_SECOND);
        }
    };
}
