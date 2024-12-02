package com.example.lab8_exercise1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Choreographer.FrameCallback {
    public interface Callbacks {
        void onFrameRendered(int fps);
        void onGameUpdated(int ticks);
    }

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Ball ball;
    private RotatingSquare square; // Dodany kwadrat
    private int screenWidth, screenHeight;
    private final int TICKS_PER_SECOND = 30;
    private int frameCount = 0;
    private int tickCount = 0;
    private long lastTime = System.currentTimeMillis();
    private long tickTime;
    public Callbacks callbacks;

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        ball = new Ball(100, 100, 15, 17, Color.RED, 20);
        square = new RotatingSquare(200, 200, 10, 12, Color.BLUE, 50, 5); // Inicjalizacja kwadratu
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public Callbacks getCallbacks() {
        return callbacks;
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        updateGameLogic();
        renderGame();
        frameCount++;
        tickCount++;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= 1000) {
            if (getCallbacks() != null) {
                getCallbacks().onFrameRendered(frameCount);
                getCallbacks().onGameUpdated(tickCount);
            }
            frameCount = 0;
            tickCount = 0;
            lastTime = currentTime;
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    public void startGameLoop() {
        tickTime = System.currentTimeMillis();
        Choreographer.getInstance().postFrameCallback(this);
    }

    public void updateGameLogic() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - tickTime >= 1000 / TICKS_PER_SECOND) {
            ball.update(screenWidth, screenHeight);
            square.update(screenWidth, screenHeight); // Aktualizacja kwadratu
            tickTime = currentTime;
        }
    }

    public void renderGame() {
        if (!surfaceHolder.getSurface().isValid()) {
            return;
        }
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE); // Clear the canvas
        ball.draw(canvas, paint);
        square.draw(canvas, paint); // Rysowanie kwadratu
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startGameLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Ignored
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Choreographer.getInstance().removeFrameCallback(this);
    }
}
