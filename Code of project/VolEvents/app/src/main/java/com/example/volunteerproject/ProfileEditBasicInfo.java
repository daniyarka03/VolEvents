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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileEditBasicInfo extends AppCompatActivity {
    /** Declaring variables  */

    EditText name, lastname;
    Button editBtn, cancelBtn;
    FirebaseFirestore fStore;
    ImageView basic_info;
    TextView basicInfoTitle;
    FirebaseAuth fAuth;
    ProgressDialog pd;
    Animation topAnim, bottomAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_basic_info);
        /** Finding a unique identifier for each variable  */


        name = findViewById(R.id.basic_info__name_field);
        lastname = findViewById(R.id.basic_info__lastname_field);
        editBtn = findViewById(R.id.basic_info__change_button);
        cancelBtn = findViewById(R.id.basic_info__cancel_button);
        basic_info = findViewById(R.id.basic_info__img);
        basicInfoTitle = findViewById(R.id.basic_info__title);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        /** Setting animation for each variable */

        basicInfoTitle.setAnimation(topAnim);
        basic_info.setAnimation(topAnim);
        name.setAnimation(topAnim);
        lastname.setAnimation(topAnim);

        editBtn.setAnimation(bottomAnim);
        cancelBtn.setAnimation(bottomAnim);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        String userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("fName"));
                lastname.setText(documentSnapshot.getString("fLastName"));
            }
        });
        /** Create an Edit button  */

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() || lastname.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileEditBasicInfo.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                pd.setTitle("Changing data ...");
                pd.show();

                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String, Object> edited = new HashMap<>();
                edited.put("fName", name.getText().toString());
                edited.put("fLastName", lastname.getText().toString());

                documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileEditBasicInfo.this, "The profile has been changed!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileEditBasicInfo.this, WelcomeScreenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        /** Create a Cancel button  */

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileEditBasicInfo.this, WelcomeScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }
}
