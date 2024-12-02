package com.example.lab8_exercise1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class RotatingSquare implements Collidable {
    private float x, y;
    private float speedX, speedY;
    private int color;
    private float sideLength;
    private float angle; // Kąt obrotu w stopniach
    private float rotationSpeed; // Szybkość obrotu
    private static final float gravity = 0.8f;

    private static final float air_resistance = 0.98f;

    private static final float deceleration = 0.8f;
    private static final float rotationDeceleration = 0.01f; // Deceleracja rotacji

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
        // Dodanie grawitacji
        speedY += gravity;

        // Aktualizacja pozycji
        x += speedX;
        y += speedY;

        // Logowanie prędkości X i Y
        Log.d("RotatingSquare", "speedX: " + speedX + " speedY: " + speedY);

        // Zmniejszanie prędkości o opór powietrza
        speedX *= air_resistance;
        speedY *= air_resistance;

        // Logowanie prędkości po uwzględnieniu oporu powietrza
        Log.d("RotatingSquare", "After air resistance - speedX: " + speedX + " speedY: " + speedY);

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

        // Logowanie wartości kąta i prędkości rotacji
        Log.d("RotatingSquare", "angle: " + angle + " rotationSpeed: " + rotationSpeed);

        // Zatrzymywanie rotacji, ale w naturalnym położeniu (boki)
        if (Math.abs(speedX) < 0.2f && Math.abs(speedY) < 0.2f) {
            speedX = 0; // Ustawienie prędkości X na 0
            speedY = 0; // Ustawienie prędkości Y na 0

            // Sprawdzanie, czy kąt jest blisko jakiegoś z naturalnych położeń
            if (Math.abs(rotationSpeed) < 0.9f) {
                // Zatrzymanie rotacji w naturalnej pozycji (blisko 0°, 90°, 180°, 270°)
                if (Math.abs(angle % 90) < 45) {
                    angle = (float) (Math.round(angle / 90) * 90);  // Ustawienie kąta na najbliższą wielokrotność 90°
                    rotationSpeed = 0;  // Zatrzymanie rotacji
                }
            } else {
                rotationSpeed *= rotationDeceleration; // Stopniowe spowolnienie rotacji
            }
        } else {
            // Rotacja wpływa na prędkość obrotu
            rotationSpeed += (speedY + Math.abs(speedX)) * 0.02f;
            rotationSpeed = Math.max(-3, Math.min(rotationSpeed, 3));
            // Ograniczenie prędkości rotacji
        }

        // Logowanie momentu rotacji
        //Log.d("RotatingSquare", "momentBezwladnosci: " + momentBezwladnosci + " rotacyjnaSila: " + rotacyjnaSila);

        // Moment bezwładności dla kwadratu
        float momentBezwladnosci = (sideLength * sideLength) / 6;  // Moment bezwładności dla kwadratu
        float rotacyjnaSila = (speedX + speedY) * 0.01f;
        rotationSpeed += rotacyjnaSila / momentBezwladnosci;

        // Stopniowe spowolnianie rotacji
        rotationSpeed *= air_resistance * 0.98f; // Opór powietrza wpływający na rotację

        // Ograniczenie kąta rotacji do 360 stopni
        angle += rotationSpeed;
        angle = angle % 360; // Utrzymywanie kąta w zakresie 0-360 stopni

        // Logowanie końcowego kąta
        Log.d("RotatingSquare", "Final angle: " + angle);
    }



    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);

        canvas.save();
        // Ustawienie punktu obrotu na środek kwadratu
        canvas.rotate(angle, x + sideLength / 2, y + sideLength / 2);

        // Rysowanie kwadratu
        RectF rect = new RectF(x, y, x + sideLength, y + sideLength);
        canvas.drawRect(rect, paint);

        canvas.restore(); // Przywrócenie pierwotnego układu współrzędnych
    }

    public void checkCollisionWithPlatform(Platform platform) {
        // Sprawdzamy, czy kwadrat znajduje się nad platformą i czy się z nią zderza
        if (y + sideLength >= platform.getY() && y <= platform.getY() + platform.getHeight()) {
            if (x + sideLength >= platform.getX() && x <= platform.getX() + platform.getWidth()) {
                // Kolizja - kwadrat odbija się
                speedY = -speedY * deceleration; // Odwracamy prędkość Y
                y = platform.getY() - sideLength; // Ustawiamy kwadrat tuż nad platformą
            }
        }
    }

    // Implementacja interfejsu Collidable
    @Override
    public float getX() {
        return x + sideLength / 2; // Środek kwadratu
    }

    @Override
    public float getY() {
        return y + sideLength / 2; // Środek kwadratu
    }

    @Override
    public float getCollisionSize() {
        return sideLength / 2; // Promień kolizji oparty na połowie długości boku
    }

    @Override
    public void reverseY() {
        speedY = -speedY; // Odwrócenie prędkości pionowej
    }
}
