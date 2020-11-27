package com.example.volunteerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreenActivity extends AppCompatActivity {
    /** Declaring variables  */

    Animation topAnim, bottomAnim;
    Button btnNext;
    ImageView welcome__logo, welcome_dream;
    TextView welcome__title, welcome_slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);
        /** Finding a unique identifier for each variable  */

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        /** Setting animation for each variable */

        btnNext = findViewById(R.id.next__button);
        welcome__logo = findViewById(R.id.welcome__logo);
        welcome__title = findViewById(R.id.welcome__title);
        welcome_slogan = findViewById(R.id.welcome_slogan);
        welcome_dream = findViewById(R.id.welcome_dream);

        welcome__logo.setAnimation(topAnim);
        welcome__title.setAnimation(topAnim);
        welcome_dream.setAnimation(bottomAnim);
        welcome_slogan.setAnimation(bottomAnim);
        btnNext.setAnimation(bottomAnim);

        /** Create a button to click "Next"  */

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
