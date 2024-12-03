package com.example.lab8_exercise1;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
public class GameActivity extends AppCompatActivity {
    private TextView fpsTextView, ticksTextView;
    private GameSurfaceView gameSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fpsTextView = findViewById(R.id.fpsTextView);
        ticksTextView = findViewById(R.id.ticksTextView);
        gameSurfaceView = findViewById(R.id.gameSurfaceView);

        // Pobierz informacje z intencji
        Intent intent = getIntent();
        String elementToDisplay = intent.getStringExtra("ELEMENT");
        int objectCount = intent.getIntExtra("COUNT", 10);
        String environmentType = intent.getStringExtra("ENVIRONMENT");

        // Przekazanie informacji do GameSurfaceView
        gameSurfaceView.setElementToDisplay(elementToDisplay);
        gameSurfaceView.setObjectCount(objectCount);
        gameSurfaceView.setEnvironmentType(environmentType);

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

