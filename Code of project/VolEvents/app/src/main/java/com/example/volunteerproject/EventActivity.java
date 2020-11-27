package com.example.volunteerproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {
/** Declaring variables */

    private static final String TAG = "EventActivity";
    TimePickerDialog picker;
    EditText mName, mGeolocation, mFood, mTransport, mEquipment, mDescription;
    TextView TitleActivity, EventName, EventDate, EventTime, EventLocation, EventDetails, mDate, mTime;
    Button ButtonAdd;
    ProgressDialog pd;
    FirebaseFirestore fStore;
    Animation topAnim, bottomAnim;
    FirebaseUser user;
    DatePickerDialog.OnDateSetListener mDateSetListener;


    FirebaseAuth fAuth;
    String pId, pIdUser, pName, pDate, pTime, pGeolocation, pFood, pTransport, pEquipment, pDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        /** Finding a unique identifier for each variable  */



        mName = findViewById(R.id.event__name_input);
        mDate = findViewById(R.id.event__date_input);
        mTime = findViewById(R.id.event__time_input);
        mGeolocation = findViewById(R.id.event__geolocation_input);
        mFood = findViewById(R.id.event__food_input);
        mTransport = findViewById(R.id.event__transport_input);
        mEquipment = findViewById(R.id.event__equipment_input);
        mDescription = findViewById(R.id.event__description_input);
        ButtonAdd = findViewById(R.id.event__button);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        EventName = findViewById(R.id.event__name);
        EventDate = findViewById(R.id.event__date);
        EventTime = findViewById(R.id.event__time);
        EventLocation = findViewById(R.id.event__geolocation);
        EventDetails = findViewById(R.id.event__details);


        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);



        TitleActivity = findViewById(R.id.event__title);
        /** Setting animation for each variable */

        TitleActivity.setAnimation(topAnim);
        EventName.setAnimation(topAnim);
        mName.setAnimation(topAnim);
        TitleActivity.setAnimation(topAnim);
        TitleActivity.setAnimation(topAnim);
        EventDate.setAnimation(topAnim);
        mDate.setAnimation(topAnim);
        EventTime.setAnimation(topAnim);
        mTime.setAnimation(topAnim);

        EventLocation.setAnimation(bottomAnim);
        mGeolocation.setAnimation(bottomAnim);
        EventDetails.setAnimation(bottomAnim);
        mFood.setAnimation(bottomAnim);
        mEquipment.setAnimation(bottomAnim);
        mTransport.setAnimation(bottomAnim);
        mDescription.setAnimation(bottomAnim);

        mDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /**Create variables for the calendar */
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                /** Create a calendar */
                DatePickerDialog dialog = new DatePickerDialog(
                        EventActivity.this,
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
                mDate.setText(date);
            }
        };

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);



                // time picker dialog
                picker = new TimePickerDialog(EventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
//                                timeText.setText(sHour + ":" + sMinute);
                                if(sMinute == 0 ) {
                                    mTime.setText(sHour + ":00");
                                }
                                else if(sMinute < 10){
                                    mTime.setText(sHour + ":0" + sMinute);
                                }
                                else {
                                    mTime.setText(sHour + ":" + sMinute);
                                }

                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            /** When changing the event data, the data when creating the previous event was inserted into this activity*/

            TitleActivity.setText("Update information!");
            ButtonAdd.setText("Change");
            pId = bundle.getString("pId");


            fStore = FirebaseFirestore.getInstance();

            fAuth = FirebaseAuth.getInstance();

            DocumentReference documentReference = fStore.collection("EventList").document(pId);

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    /** Substituting data into the database */

                    mName.setText(documentSnapshot.getString("name"));
                    mDate.setText(documentSnapshot.getString("date"));
                    mTime.setText(documentSnapshot.getString("time"));
                    mGeolocation.setText(documentSnapshot.getString("geolocation"));
                    mFood.setText(documentSnapshot.getString("food"));
                    mTransport.setText(documentSnapshot.getString("transport"));
                    mEquipment.setText(documentSnapshot.getString("equipment"));
                    mDescription.setText(documentSnapshot.getString("description"));

                }
            });



        } else {

        }

        pd = new ProgressDialog(this);



        /** Create an Add button event */

        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Create string variables and assign them the data value from the input field */


                Bundle bundle1 = getIntent().getExtras();
                if (bundle != null) {
                    String id = pId;
                    String name = mName.getText().toString().trim();
                    String date = mDate.getText().toString().trim();
                    String time = mTime.getText().toString().trim();
                    String geolocation = mGeolocation.getText().toString().trim();
                    String food = mFood.getText().toString().trim();
                    String transport = mTransport.getText().toString().trim();
                    String equipment = mEquipment.getText().toString().trim();
                    String description = mDescription.getText().toString().trim();

                    /** Uploading the updated data to the database */

                    updateData(id, name, date, time, geolocation, food, transport, equipment, description, userEmail);


                    /** After completing the process, go to the "Schedule" */

                    startActivity(new Intent(EventActivity.this, WelcomeScreenActivity.class));

                } else {

                }
            }
        });
    }

    private void updateData(String id, String name, String date, String time, String geolocation, String food, String transport, String equipment, String description, String userEmail) {
        /** Loading data */

        pd.setTitle("Updating information");
        pd.show();

        fStore.collection("EventList").document(id).update("name", name, "date", date, "time", time, "geolocation", geolocation, "food", food, "transport", transport, "equipment", equipment, "description", description, "email", userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(EventActivity.this, "Updating ...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(EventActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
