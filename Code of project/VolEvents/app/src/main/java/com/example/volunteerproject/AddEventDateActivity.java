package com.example.volunteerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class AddEventDateActivity extends AppCompatActivity {
    /** Declaring variables */
    private static final String TAG = "AddEventDateActivity";
    String nameEvent;
    Button nextBtn, prevBtn, chooseDate;
    TextView dateText, dateTitle, dateSubtitle;
    ImageView dateImage;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_date);
        /** Finding a unique identifier for each variable */

        dateText = findViewById(R.id.event_date__text_date);
        chooseDate = findViewById(R.id.event_date__button_date);
        nextBtn = findViewById(R.id.event_date__button_next);
        prevBtn = findViewById(R.id.event_date__button_prev);
        dateTitle = findViewById(R.id.event_date__title);
        dateSubtitle = findViewById(R.id.event_date__field_name);
        dateImage = findViewById(R.id.event_date__img);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        /** Setting animation for each variable */

        dateTitle.setAnimation(topAnim);
        dateImage.setAnimation(topAnim);
        dateSubtitle.setAnimation(topAnim);



        dateText.setAnimation(bottomAnim);
        nextBtn.setAnimation(bottomAnim);
        chooseDate.setAnimation(bottomAnim);
        prevBtn.setAnimation(bottomAnim);




        nameEvent = getIntent().getStringExtra("nameEvent");

        /** Create an event for the click button "Select"  */
        chooseDate.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {
                /**Create variables for the calendar */
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                /** Create a calendar */
                DatePickerDialog dialog = new DatePickerDialog(
                        AddEventDateActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        /** Displaying data on the calendar  */

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                dateText.setText(date);
            }
        };

        /** Create the next button event */
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** The data validation condition in the field is met */
                if (dateText.getText().toString().isEmpty()) {
                    Toast.makeText(AddEventDateActivity.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    /** If the field is filled, then go to and transfer the data to the next activity */

                    Intent intent = new Intent(AddEventDateActivity.this, AddEventTimeActivity.class);
                    intent.putExtra("dateEvent", dateText.getText().toString());
                    intent.putExtra("nameEvent", nameEvent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

        /** Create Back Button Event  */
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Go to the previous activity  */
                Intent intent = new Intent(AddEventDateActivity.this, AddEventNameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}