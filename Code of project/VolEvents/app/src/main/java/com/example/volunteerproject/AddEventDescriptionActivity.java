package com.example.volunteerproject;

import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddEventDescriptionActivity extends AppCompatActivity {

    /** Declaring variables  */

    String descriptionEvent, equipmentEvent, transportEvent, foodEvent, geolocationEvent, nameEvent, dateEvent, timeEvent, userEmail;
    EditText DescriptionEventEt;
    Button nextBtn, prevBtn;
    TextView descriptionTitle, descriptionSubtitle;
    ImageView descriptionImage;
    FirebaseFirestore fStore;
    Animation topAnim, bottomAnim;
    ProgressDialog pd;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_description);
        /** Find a unique identifier for each variable  */
        DescriptionEventEt = findViewById(R.id.event_description__field);

        prevBtn = findViewById(R.id.event_description__button_prev);
        descriptionTitle = findViewById(R.id.event_description__title);
        descriptionSubtitle = findViewById(R.id.event_description__field_name);
        descriptionImage = findViewById(R.id.event_description__img);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail  = user.getEmail();


        nextBtn = findViewById(R.id.event_description__button_next);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        /** Setting animation for each variable */

        descriptionTitle.setAnimation(topAnim);
        descriptionSubtitle.setAnimation(topAnim);
        descriptionImage.setAnimation(topAnim);

        DescriptionEventEt.setAnimation(bottomAnim);
        nextBtn.setAnimation(bottomAnim);
        prevBtn.setAnimation(bottomAnim);


        equipmentEvent = getIntent().getStringExtra("equipmentEvent");
        transportEvent = getIntent().getStringExtra("transportEvent");
        foodEvent = getIntent().getStringExtra("foodEvent");
        nameEvent = getIntent().getStringExtra("nameEvent");
        dateEvent = getIntent().getStringExtra("dateEvent");
        timeEvent = getIntent().getStringExtra("timeEvent");
        geolocationEvent = getIntent().getStringExtra("geolocationEvent");

        /** Initializing the database */
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        /** Create the next button event  */

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** The data validation condition in the field is met */

                if (DescriptionEventEt.getText().toString().isEmpty()) {
                    Toast.makeText(AddEventDescriptionActivity.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    /** If the field is filled, then go to and transfer the data to the next activity */
                    descriptionEvent = DescriptionEventEt.getText().toString();
                    String userId = fAuth.getCurrentUser().getUid();
                    uploadData(userId, nameEvent, dateEvent, timeEvent, geolocationEvent, foodEvent, transportEvent, equipmentEvent, descriptionEvent, userEmail);
                    startActivity(new Intent(AddEventDescriptionActivity.this, SuccessAddedEventActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
            }
            }
        });

        /** Create a back button event  */

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** We return to the previous activity */
                Intent intent = new Intent(AddEventDescriptionActivity.this, AddEventEquipmentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    /** Loading all data into the database */
    private void uploadData(String userId, String name, String date, String time, String geolocationEvent, String foodEvent, String transportEvent, String equipmentEvent, String descriptionEvent, String userEmail) {
        pd.setTitle("Adding ads");
        pd.show();

        /** Generation of a random identifier for events*/
        String id = UUID.randomUUID().toString();


        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("userId", userId);
        doc.put("name", name);
        doc.put("date", date);
        doc.put("time", time);
        doc.put("geolocation", geolocationEvent);
        doc.put("food", foodEvent);
        doc.put("transport", transportEvent);
        doc.put("equipment", equipmentEvent);
        doc.put("description", descriptionEvent);
        doc.put("email", userEmail );

        /** Sending data to the database  */

        pd.dismiss();

        fStore.collection("EventList").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                /** If the data has been successfully sent, the message "Published" is displayed */


                Toast.makeText(AddEventDescriptionActivity.this, "Published ...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                /** If the data was not sent by mistake, then the message "Error" is displayed */
                pd.dismiss();
                Toast.makeText(AddEventDescriptionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
