package com.example.lab8_exercise1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MenuActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(),"Zmiana aktywności",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mediaPlayer = new MediaPlayer();
        playSound(R.raw.enter);

        Button optionsButton = findViewById(R.id.buttonOptions);
        optionsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Tworzenie intencji, aby przejść do ekranu opcji
                Intent intent = new Intent(MenuActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });
        Button aboutButton = findViewById(R.id.buttonAbout);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });


        Button gameButton = findViewById(R.id.buttonGame);
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,PreGameActivity.class);
                startActivity(intent);
            }
        });

        Button exitButton = findViewById(R.id.buttonExit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAffinity(MenuActivity.this);
            }
        });
    }
    private void playSound(int soundResource){
        try {
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(this, soundResource);
            mediaPlayer.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
    }

}