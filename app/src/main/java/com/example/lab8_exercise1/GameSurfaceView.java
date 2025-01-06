package com.example.lab8_exercise1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

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

    private String elementToDisplay = "BALL"; // Domyślnie piłki
    private List<Ball> balls = new ArrayList<>();
    private List<RotatingSquare> squares = new ArrayList<>();

    private List<Platform> platforms = new ArrayList<>();
    private WaterZone waterZone = null;
    private AirZone airZone = null;
    private String environmentType = "NONE";



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

        // Tworzenie obiektów
        for (int i = 0; i < 10; i++) {
            balls.add(new Ball(100 + i * 50, 100 + i * 50, 15, 17, Color.RED, 20));
            squares.add(new RotatingSquare(100 + i * 50, 100 + i * 50, 10, 12, Color.GREEN, 50, 5));
        }
        // Tworzenie platformy powyżej dołu ekranu
        int platformHeight = 20; // Wysokość platformy
        int platformWidth = screenWidth; // Szerokość platformy na całą szerokość ekranu
        int platformY = screenHeight - platformHeight - 100; // Ustawienie pozycji Y platformy

        // Dodanie platformy do listy platform
        platforms.add(new Platform(0, platformY, platformWidth, platformHeight, Color.GREEN));
    }

    public void setElementToDisplay(String elementToDisplay) {
        this.elementToDisplay = elementToDisplay;
    }
    public void setEnvironmentType(String environmentType) {
        this.environmentType = environmentType;
    }
    public void setObjectCount(int count) {
        balls.clear();
        squares.clear();

        for (int i = 0; i < count; i++) {
            balls.add(new Ball(100 + i * 50, 100 + i * 50, 15, 17, Color.RED, 20));
            squares.add(new RotatingSquare(100 + i * 50, 100 + i * 50, 10, 12, Color.GREEN, 50, 5));
        }
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
            // Aktualizacja logiki gry (ruch, kolizje)
            for (Ball ball : balls) {
                ball.update(screenWidth, screenHeight, waterZone, airZone);
                for (Platform platform : platforms) {
                    ball.checkCollisionWithPlatform(platform); // Sprawdzenie kolizji piłki z platformą
                }
            }
            for (RotatingSquare square : squares) {
                square.update(screenWidth, screenHeight, waterZone, airZone);
                for (Platform platform : platforms) {
                    square.checkCollisionWithPlatform(platform); // Sprawdzenie kolizji kwadratu z platformą
                }
            }

            tickTime = currentTime;
        }
    }



    public void renderGame() {
        if (!surfaceHolder.getSurface().isValid()) return;

        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);

        if ("WATER".equals(environmentType)) {
            waterZone.draw(canvas, paint);
        } else if ("AIR".equals(environmentType)) {
            airZone.draw(canvas,paint);// Clear the canvas
        }else if ("BOTH".equals(environmentType)){
            waterZone.draw(canvas, paint);
            airZone.draw(canvas,paint);
        }



        if ("BALL".equals(elementToDisplay)) {
            for (Ball ball : balls) {
                ball.draw(canvas, paint);
            }
        } else if ("SQUARE".equals(elementToDisplay)) {
            for (RotatingSquare square : squares) {
                square.draw(canvas, paint);
            }
        }
        if ("BOTH".equals(elementToDisplay)) {
            for (Ball ball : balls) {
                ball.draw(canvas, paint);
            }
            for (RotatingSquare square : squares) {
                square.draw(canvas, paint);
            }
        }


        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if ("WATER".equals(environmentType)) {
            waterZone = new WaterZone(0, 400, screenWidth, 200, Color.BLUE);
        } else if ("AIR".equals(environmentType)) {
            airZone = new AirZone(0,700, screenWidth, 200, Color.GRAY);
        }else if ("BOTH".equals(environmentType)){
            waterZone = new WaterZone(0, 400, screenWidth, 200, Color.BLUE);
            airZone = new AirZone(0,700, screenWidth, 200, Color.GRAY);
        }

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
    public Bundle saveState() {
        Bundle bundle = new Bundle();
        bundle.putString("ELEMENT", elementToDisplay);
        bundle.putString("ENVIRONMENT", environmentType);
        bundle.putInt("COUNT", balls.size()); // Przykład: zapisanie liczby obiektów
        return bundle;
    }

    public void restoreState(Bundle bundle) {
        if (bundle != null) {
            setElementToDisplay(bundle.getString("ELEMENT"));
            setEnvironmentType(bundle.getString("ENVIRONMENT"));
            setObjectCount(bundle.getInt("COUNT"));
        }
    }
    public void pauseGameLoop() {
        Choreographer.getInstance().removeFrameCallback(this);
    }

    public void resumeGameLoop() {
        Choreographer.getInstance().postFrameCallback(this);
    }
    public String getElementToDisplay() {
        return elementToDisplay;
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public int getObjectCount() {
        return balls.size(); // Zwracamy liczbę obiektów (piłek), zakładając, że piłki i kwadraty są zawsze równe liczebnie
    }



}
