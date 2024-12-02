package com.example.lab8_exercise1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    private static final long ANIMATION_DURATION = 2000; // Czas animacji w milisekundach

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView=findViewById(R.id.imageView);

        // Tworzenie animacji Alpha
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(ANIMATION_DURATION);

        // Dodawanie Listenera do animacji, aby obsłużyć zakończenie
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Początek animacji
                imageView.setImageResource(R.drawable.amiga);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Koniec animacji
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Powtórzenie animacji (nie jest używane)
            }
        });
        imageView.startAnimation(alphaAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menuIntent = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(menuIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
