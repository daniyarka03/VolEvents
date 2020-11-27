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

public class AddEventFoodActivity extends AppCompatActivity {

    /** Declaring variables  */

    String foodEvent, geolocationEvent, nameEvent, dateEvent, timeEvent;
    Button yesBtn, noBtn, prevBtn;
    TextView foodTitle, foodSubtitle;
    ImageView foodImage;
    Animation topAnim, bottomAnim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_food);

        /** Finding a unique identifier for each variable  */


        yesBtn = findViewById(R.id.event_food__button_yes);
        noBtn = findViewById(R.id.event_food__button_no);
        prevBtn = findViewById(R.id.event_food__button_prev);

        foodTitle = findViewById(R.id.event_food__title);
        foodSubtitle = findViewById(R.id.event_food__field_name);
        foodImage = findViewById(R.id.event_food__img);
        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        /** Setting animation for each variable */

        foodTitle.setAnimation(topAnim);
        foodSubtitle.setAnimation(topAnim);
        foodImage.setAnimation(topAnim);

        yesBtn.setAnimation(bottomAnim);
        noBtn.setAnimation(bottomAnim);
        prevBtn.setAnimation(bottomAnim);

        nameEvent = getIntent().getStringExtra("nameEvent");
        dateEvent = getIntent().getStringExtra("dateEvent");
        timeEvent = getIntent().getStringExtra("timeEvent");
        geolocationEvent = getIntent().getStringExtra("geolocationEvent");

        /** Create an event for the "Yes" button  */

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Transition with sending data to the next activity */
                Intent intent = new Intent(AddEventFoodActivity.this, AddEventTransportActivity.class);
                intent.putExtra("foodEvent", "Yes");
                intent.putExtra("geolocationEvent", geolocationEvent);
                intent.putExtra("timeEvent", timeEvent);
                intent.putExtra("dateEvent", dateEvent);
                intent.putExtra("nameEvent", nameEvent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        /** Create a button event for clicking "No"  */

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Transition with sending data to the next activity */

                Intent intent = new Intent(AddEventFoodActivity.this, AddEventTransportActivity.class);
                intent.putExtra("foodEvent", "No");
                intent.putExtra("geolocationEvent", geolocationEvent);
                intent.putExtra("timeEvent", timeEvent);
                intent.putExtra("dateEvent", dateEvent);
                intent.putExtra("nameEvent", nameEvent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        /**Create a back button event */

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Go to the previous activity */

                Intent intent = new Intent(AddEventFoodActivity.this, AddEventGeolocationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
    }
}
