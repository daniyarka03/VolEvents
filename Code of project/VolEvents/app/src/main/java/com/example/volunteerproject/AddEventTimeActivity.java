package com.example.volunteerproject;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddEventTimeActivity extends AppCompatActivity {

    /** Declaring variables  */

    TimePickerDialog picker;
    String nameEvent, dateEvent;
    TextView timeText, timeTitle, timeSubtitle;
    ImageView timeImage;
    Animation topAnim, bottomAnim;
    Button nextBtn, prevBtn, chooseTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_time);

        /** Finding a unique identifier for each variable  */

        nextBtn = findViewById(R.id.event_time__button_next);
        prevBtn = findViewById(R.id.event_time__button_prev);
        chooseTime = findViewById(R.id.event_time__button_time);
        timeText = findViewById(R.id.event_time__text_time);
        nameEvent = getIntent().getStringExtra("nameEvent");
        timeImage = findViewById(R.id.event_time__img);
        timeTitle = findViewById(R.id.event_time__title);
        timeSubtitle = findViewById(R.id.event_time__field_name);
        dateEvent = getIntent().getStringExtra("dateEvent");

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        /** Setting animation for each variable */

        timeTitle.setAnimation(topAnim);
        timeImage.setAnimation(topAnim);
        timeSubtitle.setAnimation(topAnim);

        timeText.setAnimation(bottomAnim);
        chooseTime.setAnimation(bottomAnim);
        nextBtn.setAnimation(bottomAnim);
        prevBtn.setAnimation(bottomAnim);


        /** Create a button to click "Select" */

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);




                // time picker dialog
                picker = new TimePickerDialog(AddEventTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
//                                timeText.setText(sHour + ":" + sMinute);
                                if(sMinute == 0 ) {
                                    timeText.setText(sHour + ":00");
                                }
                                else if(sMinute < 10){
                                    timeText.setText(sHour + ":0" + sMinute);
                                }
                                else {
                                    timeText.setText(sHour + ":" + sMinute);
                                }

                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        /** Create a button to click "Next"  */

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeText.getText().toString().isEmpty()) {
                    Toast.makeText(AddEventTimeActivity.this, "Fill in all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(AddEventTimeActivity.this, AddEventGeolocationActivity.class);
                    intent.putExtra("timeEvent", timeText.getText().toString());
                    intent.putExtra("dateEvent", dateEvent);
                    intent.putExtra("nameEvent", nameEvent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        });

        /** Create a Back Button  */

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventTimeActivity.this, AddEventDateActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
    }
}
