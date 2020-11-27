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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileEditEmail extends AppCompatActivity {

    /** Declaring variables  */

    EditText email;
    Button editBtn, cancelBtn;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    TextView emailTitle;
    ImageView emailImage;
    FirebaseUser user;
    Animation topAnim, bottomAnim;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_email);
        /** Finding a unique identifier for each variable
         */


        email = findViewById(R.id.email__new_email_field);
        editBtn = findViewById(R.id.email__change_button);
        cancelBtn = findViewById(R.id.email__cancel_button);
        emailTitle = findViewById(R.id.email__title);
        emailImage = findViewById(R.id.email__img);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        /** Setting animation for each variable */

        emailTitle.setAnimation(topAnim);
        emailImage.setAnimation(topAnim);
        email.setAnimation(topAnim);

        editBtn.setAnimation(bottomAnim);
        cancelBtn.setAnimation(bottomAnim);

        emailTitle.setAnimation(topAnim);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        user = fAuth.getCurrentUser();


        DocumentReference documentReference = fStore.collection("users").document(user.getUid());

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                email.setText(documentSnapshot.getString("email"));
            }
        });

        /** Create an Edit Button */

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileEditEmail.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                pd.setTitle("Changing data ...");
                pd.show();

                String emailStr = email.getText().toString();


                /** Updating email */

                user.updateEmail(emailStr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = fStore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email", emailStr);

                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pd.dismiss();
                                Toast.makeText(ProfileEditEmail.this, "The profile has been changed!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProfileEditEmail.this, WelcomeScreenActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(ProfileEditEmail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        /** Create a Cancel button */
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileEditEmail.this, WelcomeScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

    }
}
