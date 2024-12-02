package com.example.lab8_exercise1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private TextView fpsTextView;
    private TextView ticksTextView;
    private GameSurfaceView gameSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fpsTextView = findViewById(R.id.fpsTextView);
        ticksTextView = findViewById(R.id.ticksTextView);
        gameSurfaceView = findViewById(R.id.gameSurfaceView);
        gameSurfaceView.setCallbacks(new GameSurfaceView.Callbacks() {
            @Override
            public void onFrameRendered(int fps) {
                fpsTextView.setText("Frames Per Second: " + fps);
            }
            @Override
            public void onGameUpdated(int ticks) {
                ticksTextView.setText("Ticks: " + ticks);
            }
        });
    }
}
