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

public class AddEventNameActivity extends AppCompatActivity {

    /** Declaring variables  */
    Button nextBtn, cancelBtn;
    EditText NameEventEt;
    ImageView name_img;
    TextView event_name_title, event_name_subtitle;
    Animation topAnim, bottomAnim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_name);


        /** Finding a unique identifier for each variable */

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);



        nextBtn = findViewById(R.id.event_name__button_next);
        name_img = findViewById(R.id.event_name__img);
        event_name_title = findViewById(R.id.event_name__title);
        event_name_subtitle = findViewById(R.id.event_name__field_name);
        cancelBtn = findViewById(R.id.event_name__button_cancel);
        NameEventEt = findViewById(R.id.event_name__field);



        /** Setting animation for each variable */

        event_name_title.setAnimation(topAnim);
        name_img.setAnimation(topAnim);
        event_name_subtitle.setAnimation(topAnim);



        NameEventEt.setAnimation(bottomAnim);
        nextBtn.setAnimation(bottomAnim);
        cancelBtn.setAnimation(bottomAnim);


        /** Create a button to click "Next"  */


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View view) {
                if (NameEventEt.getText().toString().isEmpty()) {
                    Toast.makeText(AddEventNameActivity.this, "Fill in all the fields!", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(AddEventNameActivity.this, AddEventDateActivity.class);
                    intent.putExtra("nameEvent", NameEventEt.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
              }
        });

        /** Create a Cancel button  */

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent intent = new Intent(AddEventNameActivity.this, WelcomeScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
