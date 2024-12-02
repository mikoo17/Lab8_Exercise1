package com.example.lab8_exercise1;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Platform {
    private float x, y, width, height;
    private int color;

    public Platform(float x, float y, float width, float height, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawRect(x, y, x + width, y + height, paint);
    }
}

