package com.example.lab8_exercise1;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

public class RotatingSquare {
    private float x, y;
    private float speedX, speedY;
    private int color;
    private float sideLength;
    private float angle; // Kąt obrotu w stopniach
    private float rotationSpeed; // Szybkość obrotu
    private static final float gravity = 0.8f;
    private static final float air_resistance = 0.99f;
    private static final float deceleration = 0.8f;

    public RotatingSquare(float x, float y, float speedX, float speedY, int color, float sideLength, float rotationSpeed) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
        this.sideLength = sideLength;
        this.rotationSpeed = rotationSpeed;
        this.angle = 0; // Początkowy kąt obrotu
    }

    public void update(int width, int height) {
        speedY += gravity;
        x += speedX;
        y += speedY;
        angle += rotationSpeed; // Aktualizacja kąta obrotu

        // Zmniejsz prędkości
        speedX *= air_resistance;
        speedY *= air_resistance;

        // Odbicia od ścian
        if (y + sideLength >= height) {
            speedY = -speedY * deceleration;
            y = height - sideLength;
        } else if (y <= 0) {
            speedY = -speedY * deceleration;
            y = 0;
        }

        if (x + sideLength >= width) {
            speedX = -speedX * deceleration;
            x = width - sideLength;
        } else if (x <= 0) {
            speedX = -speedX * deceleration;
            x = 0;
        }

        // Minimalna prędkość
        if (Math.abs(speedX) < 0.1f) speedX = 0;
        if (Math.abs(speedY) < 0.1f) speedY = 0;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);

        canvas.save();
        // Ustaw punkt obrotu na środek kwadratu
        canvas.rotate(angle, x + sideLength / 2, y + sideLength / 2);

        // Rysuj kwadrat
        RectF rect = new RectF(x, y, x + sideLength, y + sideLength);
        canvas.drawRect(rect, paint);

        canvas.restore(); // Przywróć pierwotny układ współrzędnych
    }
}
