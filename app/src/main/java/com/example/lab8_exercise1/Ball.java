package com.example.lab8_exercise1;

import android.graphics.Canvas;
import android.graphics.Paint;
public class Ball {
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
    public void update(int width, int height) {
        speedY += gravity; // Dodaj grawitację do prędkości pionowej
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

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }
}

