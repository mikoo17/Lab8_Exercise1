package com.example.lab8_exercise1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PreGameActivity extends AppCompatActivity {
    private Button ballButton, squareButton;// Domyślnie piłki

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        // Inicjalizacja przycisków
        ballButton = findViewById(R.id.ballButton);
        squareButton = findViewById(R.id.squareButton);

        // Obsługa wyboru elementów do wyświetlania
        ballButton.setOnClickListener(v -> startGame("BALL"));
        squareButton.setOnClickListener(v -> startGame("SQUARE"));
    }

    private void startGame(String elementToDisplay) {
        Intent intent = new Intent(PreGameActivity.this, GameActivity.class);
        intent.putExtra("ELEMENT", elementToDisplay); // Przekazanie wyboru elementu
        startActivity(intent);
    }
}

