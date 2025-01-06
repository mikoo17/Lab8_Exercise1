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

        if (savedInstanceState != null) {
            gameSurfaceView.restoreState(savedInstanceState);
        } else {
            Intent intent = getIntent();
            gameSurfaceView.setElementToDisplay(intent.getStringExtra("ELEMENT"));
            gameSurfaceView.setObjectCount(intent.getIntExtra("COUNT", 10));
            gameSurfaceView.setEnvironmentType(intent.getStringExtra("ENVIRONMENT"));
        }

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ELEMENT", gameSurfaceView.getElementToDisplay());
        outState.putString("ENVIRONMENT", gameSurfaceView.getEnvironmentType());
        outState.putInt("COUNT", gameSurfaceView.getObjectCount());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String elementToDisplay = savedInstanceState.getString("ELEMENT");
        String environmentType = savedInstanceState.getString("ENVIRONMENT");
        int objectCount = savedInstanceState.getInt("COUNT");

        gameSurfaceView.setElementToDisplay(elementToDisplay);
        gameSurfaceView.setEnvironmentType(environmentType);
        gameSurfaceView.setObjectCount(objectCount);
    }
    @Override
    protected void onPause() {
        super.onPause();
        gameSurfaceView.pauseGameLoop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameSurfaceView.resumeGameLoop();
    }


}

