package org.deafop.deafopreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.deafop.deafopreport.Activities.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    private ImageView splash_image;
    private TextView splash_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splash_image = findViewById(R.id.image_splash);
        splash_textview = findViewById(R.id.splash_text);

        Animation splash_animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        splash_image.startAnimation(splash_animation);
        splash_textview.startAnimation(splash_animation);

        final Intent toLogInActivity = new Intent(this, LoginActivity.class);
        Thread mythread = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(toLogInActivity);
                    finish();
                }
            }
        };
        mythread.start();
    }
}
