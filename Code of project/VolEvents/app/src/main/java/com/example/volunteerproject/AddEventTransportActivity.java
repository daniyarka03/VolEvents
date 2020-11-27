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

public class AddEventTransportActivity extends AppCompatActivity {


    /** Declaring variables */

    String transportEvent, foodEvent, geolocationEvent, nameEvent, dateEvent, timeEvent;
    Button yesBtn, noBtn, prevBtn;
    Animation topAnim, bottomAnim;
    TextView transportTitle, transportSubtitle;
    ImageView transportImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_transport);

        /** Finding a unique identifier for each variable  */


        yesBtn = findViewById(R.id.event_transport__button_yes);
        noBtn = findViewById(R.id.event_transport__button_no);
        prevBtn = findViewById(R.id.event_transport__button_prev);
        transportTitle = findViewById(R.id.event_transport__title);
        transportSubtitle = findViewById(R.id.event_transport__field_name);
        transportImage = findViewById(R.id.event_transport__img);



        foodEvent = getIntent().getStringExtra("foodEvent");
        nameEvent = getIntent().getStringExtra("nameEvent");
        dateEvent = getIntent().getStringExtra("dateEvent");
        timeEvent = getIntent().getStringExtra("timeEvent");
        geolocationEvent = getIntent().getStringExtra("geolocationEvent");

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        /** Setting animation for each variable */

        transportTitle.setAnimation(topAnim);
        transportSubtitle.setAnimation(topAnim);
        transportImage.setAnimation(topAnim);


        yesBtn.setAnimation(bottomAnim);
        noBtn.setAnimation(bottomAnim);
        prevBtn.setAnimation(bottomAnim);



        /** Create an event for the "Yes" button  */

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventTransportActivity.this, AddEventEquipmentActivity.class);
                intent.putExtra("transportEvent", "Yes");
                intent.putExtra("foodEvent", foodEvent);
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
                Intent intent = new Intent(AddEventTransportActivity.this, AddEventEquipmentActivity.class);
                intent.putExtra("transportEvent", "No" + "");
                intent.putExtra("foodEvent", foodEvent);
                intent.putExtra("geolocationEvent", geolocationEvent);
                intent.putExtra("timeEvent", timeEvent);
                intent.putExtra("dateEvent", dateEvent);
                intent.putExtra("nameEvent", nameEvent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        /** Create a back button event  */

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventTransportActivity.this, AddEventFoodActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
