package com.example.volunteerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEventGeolocationActivity extends AppCompatActivity {
    /** Declaring variables  */

    String geolocationEvent, nameEvent, dateEvent, timeEvent;
    EditText geolocationEventEt;
    Animation topAnim, bottomAnim;
    TextView geolocationTitle, geolocationSubtitle;
    ImageView geolocationImage;
    Button nextBtn, prevBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_geolocation);

        /** Finding a unique identifier for each variable */

        geolocationEventEt = findViewById(R.id.event_geolocation__field);
        nextBtn = findViewById(R.id.event_geolocation__button_next);
        prevBtn = findViewById(R.id.event_geolocation__button_prev);
        nameEvent = getIntent().getStringExtra("nameEvent");
        dateEvent = getIntent().getStringExtra("dateEvent");
        timeEvent = getIntent().getStringExtra("timeEvent");

        geolocationImage = findViewById(R.id.event_geolocation__img);
        geolocationTitle = findViewById(R.id.event_geolocation__title);
        geolocationSubtitle = findViewById(R.id.event_geolocation__field_name);


        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        /** Setting animation for each variable */

        geolocationTitle.setAnimation(topAnim);
        geolocationImage.setAnimation(topAnim);
        geolocationSubtitle.setAnimation(topAnim);

        nextBtn.setAnimation(bottomAnim);
        prevBtn.setAnimation(bottomAnim);

        /** Create an event for the "Next" button  */

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (geolocationEventEt.getText().toString().isEmpty()) {
                    Toast.makeText(AddEventGeolocationActivity.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(AddEventGeolocationActivity.this, AddEventFoodActivity.class);
                    intent.putExtra("geolocationEvent", geolocationEventEt.getText().toString());
                    intent.putExtra("timeEvent", timeEvent);
                    intent.putExtra("dateEvent", dateEvent);
                    intent.putExtra("nameEvent", nameEvent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

        /** Create a back button event  */

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventGeolocationActivity.this, AddEventTimeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }
}
