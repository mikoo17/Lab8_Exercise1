package com.example.lab8_exercise1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class AirZone {
    private float x, y, width, height;
    private int color;

    public AirZone(float x, float y, float width, float height, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public boolean isInAir(float objX, float objY, float objRadius) {
        // Sprawdzamy, czy środek obiektu znajduje się wewnątrz obszaru powietrza.
        return objX + objRadius >= x && objX - objRadius <= x + width &&
                objY + objRadius >= y && objY - objRadius <= y + height;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        RectF rect = new RectF(x, y, x + width, y + height);
        canvas.drawRect(rect, paint);
    }
}

