package com.example.volunteerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessAddedEventActivity extends AppCompatActivity {

    /** Declaring a variable  */

    Button button;
    ImageView successImage;
    TextView successText, successTitle;
    Animation topAnim, bottomAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Finding a unique identifier for a variable */

        setContentView(R.layout.activity_success_added_event);

        button = findViewById(R.id.success_event__button);
        successImage = findViewById(R.id.success_event__img);
        successText = findViewById(R.id.success_event__text);
        successTitle = findViewById(R.id.success_event__title);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        /** Setting animation for each variable */

        successImage.setAnimation(topAnim);
        successText.setAnimation(topAnim);
        successTitle.setAnimation(topAnim);

        button.setAnimation(bottomAnim);



        /** Creating a Next Button Event */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(SuccessAddedEventActivity.this, WelcomeScreenActivity.class));
                finish();

            }
        });

    }
}
