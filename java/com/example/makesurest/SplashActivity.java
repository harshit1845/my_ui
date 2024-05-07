package com.example.makesurest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makesurest.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 4000; // 4 seconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN

        );
        ImageView backgroundImage = findViewById(R.id.SplashScreenImage);
        TextView text = findViewById(R.id.nameText);
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide);
        backgroundImage.startAnimation(slideAnimation);
        text.startAnimation(slideAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 is the delayed time in milliseconds.


    }
}