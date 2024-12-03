package com.example.lab8_exercise1;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball implements Collidable {
    private float x, y;
    private float speedX, speedY;
    private int color;
    private float radius;
    private static final float gravity = 0.8f;
    private static final float air_resistance = 0.99f;
    private static final float deceleration = 0.8f; // lub 0.9f

    public Ball(float x, float y, float speedX, float speedY, int color, float radius) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
        this.radius = radius;
    }

    public void update(int width, int height, WaterZone waterZone, AirZone airZone) {
        speedY += gravity; // Dodaj grawitację do prędkości pionowej

        // Sprawdzenie, czy obiekt jest w wodzie
        if (waterZone != null && waterZone.isInWater(x, y, radius)) {
            speedY *= 0.8f; // Redukcja prędkości w osi Y przez opór wody
        }
        // Sprawdzenie, czy obiekt jest w wodzie
        if (airZone != null && airZone.isInAir(x, y, radius)) {
            speedX *= 1.2f; // Redukcja prędkości w osi Y przez opór wody
        }
        x += speedX;
        y += speedY;

        // Zmniejsz prędkości o opór powietrza
        speedX *= air_resistance;
        speedY *= air_resistance;

        // Odbicie od dolnej i górnej krawędzi
        if (y + radius >= height) {
            speedY = -speedY * deceleration;
            y = height - radius; // Zapobiega "wpadnięciu" w podłogę
        } else if (y - radius <= 0) {
            speedY = -speedY * deceleration;
            y = radius;
        }

        // Odbicie od bocznych ścian z uwzględnieniem strat
        if (x - radius <= 0) {
            speedX = -speedX * deceleration;
            x = radius;
        } else if (x + radius >= width) {
            speedX = -speedX * deceleration;
            x = width - radius;
        }
    }
    public void checkCollisionWithPlatform(Platform platform) {
        // Sprawdzamy, czy piłka znajduje się nad platformą i czy się z nią zderza
        if (y + radius >= platform.getY() && y - radius <= platform.getY() + platform.getHeight()) {
            if (x + radius >= platform.getX() && x - radius <= platform.getX() + platform.getWidth()) {
                // Kolizja - piłka odbija się
                speedY = -speedY * deceleration;  // Odwrócenie prędkości Y i zmniejszenie jej
                y = platform.getY() - radius; // Ustawiamy piłkę tuż nad platformą
            }
        }
    }


    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public float getX() {
        return x; // Środek piłki
    }

    @Override
    public float getY() {
        return y; // Środek piłki
    }

    @Override
    public float getCollisionSize() {
        return radius; // Promień piłki
    }

    @Override
    public void reverseY() {
        speedY = -speedY; // Odwracamy kierunek ruchu w osi Y
    }
}
