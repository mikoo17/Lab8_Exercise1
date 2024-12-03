package com.example.lab8_exercise1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PreGameActivity extends AppCompatActivity {
    private Button ballButton, squareButton;// Domyślnie piłki
    private RadioGroup objectTypeGroup;
    private SeekBar objectCountSeekBar;
    private TextView objectCountTextView;
    private Button startSimulationButton;
    private int objectCount = 10;
    private RadioGroup environmentTypeGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        objectTypeGroup = findViewById(R.id.objectTypeGroup);
        objectCountSeekBar = findViewById(R.id.objectCountSeekBar);
        objectCountTextView = findViewById(R.id.objectCountTextView);
        startSimulationButton = findViewById(R.id.startSimulationButton);
        environmentTypeGroup = findViewById(R.id.environmentTypeGroup);

// Obsługa suwaka ilości obiektów
        objectCountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                objectCount = progress;
                objectCountTextView.setText("Liczba obiektów: " + objectCount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

// Obsługa przycisku "Rozpocznij Symulację"
        startSimulationButton.setOnClickListener(v -> {
            String elementToDisplay = "BALL"; // Domyślnie piłki
            int selectedElementId = objectTypeGroup.getCheckedRadioButtonId();
            if (selectedElementId == R.id.squareOption) {
                elementToDisplay = "SQUARE";
            } else if (selectedElementId == R.id.bothOption) {
                elementToDisplay = "BOTH";
            }

            // Pobranie wybranej strefy
            String environmentType = "NONE"; // Domyślna wartość (brak strefy)
            int selectedEnvironmentId = environmentTypeGroup.getCheckedRadioButtonId();
            if (selectedEnvironmentId == R.id.waterZoneOption) {
                environmentType = "WATER";
            } else if (selectedEnvironmentId == R.id.airZoneOption) {
                environmentType = "AIR";
            }else if (selectedEnvironmentId ==R.id.bothZoneOption){
                environmentType = "BOTH";
            }

            Intent intent = new Intent(PreGameActivity.this, GameActivity.class);
            intent.putExtra("ELEMENT", elementToDisplay);
            intent.putExtra("COUNT", objectCount);
            intent.putExtra("ENVIRONMENT", environmentType); // Przekazanie strefy
            startActivity(intent);
        });

    }

    private void startGame(String elementToDisplay) {
        Intent intent = new Intent(PreGameActivity.this, GameActivity.class);
        intent.putExtra("ELEMENT", elementToDisplay); // Przekazanie wyboru elementu
        startActivity(intent);
    }
}

