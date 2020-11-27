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

public class AddEventEquipmentActivity extends AppCompatActivity {

    /** Declaring variables  */

    String equipmentEvent, transportEvent, foodEvent, geolocationEvent, nameEvent, dateEvent, timeEvent;
    Button yesBtn, noBtn, prevBtn;
    Animation topAnim, bottomAnim;
    TextView equipmentTitle, equipmentSubtitle;
    ImageView equipmentImage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_equipment);

        /** Finding a unique identifier for each variable  */

        yesBtn = findViewById(R.id.event_equipment__button_yes);
        noBtn = findViewById(R.id.event_equipment__button_no);
        prevBtn = findViewById(R.id.event_equipment__button_prev);

        equipmentTitle = findViewById(R.id.event_equipment__title);
        equipmentSubtitle = findViewById(R.id.event_equipment__field_name);
        equipmentImage = findViewById(R.id.event_equipment__img);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        /** Setting animation for each variable */

        equipmentTitle.setAnimation(topAnim);
        equipmentSubtitle.setAnimation(topAnim);
        equipmentImage.setAnimation(topAnim);

        yesBtn.setAnimation(bottomAnim);
        noBtn.setAnimation(bottomAnim);
        prevBtn.setAnimation(bottomAnim);

        transportEvent = getIntent().getStringExtra("transportEvent");
        foodEvent = getIntent().getStringExtra("foodEvent");
        nameEvent = getIntent().getStringExtra("nameEvent");
        dateEvent = getIntent().getStringExtra("dateEvent");
        timeEvent = getIntent().getStringExtra("timeEvent");
        geolocationEvent = getIntent().getStringExtra("geolocationEvent");

        /** Create an event for the "Yes" button  */

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Transition with sending data to the next activity */

                Intent intent = new Intent(AddEventEquipmentActivity.this, AddEventDescriptionActivity.class);
                intent.putExtra("equipmentEvent", "Yes");
                intent.putExtra("transportEvent", transportEvent);
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
                /** Transition with sending data to the next activity */

                Intent intent = new Intent(AddEventEquipmentActivity.this, AddEventDescriptionActivity.class);
                intent.putExtra("equipmentEvent", "No");
                intent.putExtra("transportEvent", transportEvent);
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

        /** Create a back button event */

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Go to the previous activity */

                Intent intent = new Intent(AddEventEquipmentActivity.this, AddEventTransportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }
}
