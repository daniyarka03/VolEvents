package com.example.volunteerproject;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EventInfoActivity extends AppCompatActivity {
    /**
     * Declaring variables
     */


    TextView name, date, time, geolocation, food, transport, equipment, description, contact, titleDescription, other, infoTitle;
    String pId, emailId;
    FirebaseFirestore fStore;
    Animation topAnim, bottomAnim;
    ImageView festivalImage;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        /** Finding a unique identifier for each variable  */

        name = findViewById(R.id.info__name);
        date = findViewById(R.id.info__date);
        time = findViewById(R.id.info__time);
        titleDescription = findViewById(R.id.info__title_description);
        festivalImage = findViewById(R.id.festival_img);
        other = findViewById(R.id.info__other);
        contact = findViewById(R.id.info_contact);
        geolocation = findViewById(R.id.info__geolocation);
        food = findViewById(R.id.info__food);
        transport = findViewById(R.id.info__transport);
        equipment = findViewById(R.id.info__equipment);
        description = findViewById(R.id.info__description);
        infoTitle = findViewById(R.id.event_info__title);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        /** Setting animation for each variable */

        festivalImage.setAnimation(topAnim);
        name.setAnimation(topAnim);
        titleDescription.setAnimation(topAnim);
        other.setAnimation(bottomAnim);
        description.setAnimation(topAnim);
        infoTitle.setAnimation(topAnim);
        contact.setAnimation(bottomAnim);

        food.setAnimation(bottomAnim);
        transport.setAnimation(bottomAnim);
        equipment.setAnimation(bottomAnim);
        geolocation.setAnimation(topAnim);
        date.setAnimation(topAnim);
        time.setAnimation(topAnim);


        pId = getIntent().getStringExtra("pId");

        fStore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String emailId = user.getUid();

        DocumentReference documentReference = fStore.collection("EventList").document(pId);


        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                /** Substitution of data value into ready-made fields */

                name.setText(documentSnapshot.getString("name"));
                date.setText("Event date: " + documentSnapshot.getString("date"));
                time.setText("Event time: " + documentSnapshot.getString("time"));
                geolocation.setText("Location: " + documentSnapshot.getString("geolocation"));
                food.setText("Free food: " + documentSnapshot.getString("food"));
                transport.setText("Free transport: " + documentSnapshot.getString("transport"));
                equipment.setText("Free equipment: " + documentSnapshot.getString("equipment"));
                description.setText(documentSnapshot.getString("description"));
                contact.setText("Contact with: " + documentSnapshot.getString("email"));

            }
        });
    }
}
