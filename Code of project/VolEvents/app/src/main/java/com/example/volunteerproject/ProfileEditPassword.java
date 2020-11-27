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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileEditPassword extends AppCompatActivity {

    /** Declaring variables */

    TextView email;
    EditText current_password, new_password;
    Button editBtn, cancelBtn;
    FirebaseFirestore fStore;
    TextView passwordTitle;
    ImageView passwordImage;
    Animation topAnim, bottomAnim;
    FirebaseAuth fAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_password);
        /** Finding a unique identifier for each variable  */


        current_password = findViewById(R.id.password__current_field);
        new_password = findViewById(R.id.password__new_field);
        editBtn = findViewById(R.id.password__change_button);
        cancelBtn = findViewById(R.id.password__cancel_button);
        passwordTitle = findViewById(R.id.password__title);
        passwordImage = findViewById(R.id.password__img);

        /** Finding a unique identifier for each variable  */

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        /** Setting animation for each variable */

        passwordTitle.setAnimation(topAnim);
        passwordImage.setAnimation(topAnim);
        current_password.setAnimation(topAnim);

        new_password.setAnimation(bottomAnim);
        editBtn.setAnimation(bottomAnim);
        cancelBtn.setAnimation(bottomAnim);


        email = findViewById(R.id.password__email);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        String userId = fAuth.getCurrentUser().getUid();
        FirebaseUser user = fAuth.getInstance().getCurrentUser();

        DocumentReference documentReference = fStore.collection("users").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                email.setText(documentSnapshot.getString("email"));
            }
        });


        /** Create an Edit button*/
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_password.getText().toString().isEmpty() || new_password.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileEditPassword.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (new_password.length() < 6) {
                    Toast.makeText(ProfileEditPassword.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pd.setTitle("Changing data ...");
                pd.show();

                AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(), current_password.getText().toString());
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            /** Changing Password  */

                            user.updatePassword(new_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        /** If the password was successfully changed, then the message "Password changed" was displayed, and went to the activation Profile  */

                                        Toast.makeText(ProfileEditPassword.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProfileEditPassword.this, WelcomeScreenActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        pd.dismiss();

                                    } else {
                                        /** If the password was successfully changed, then the message "Password changed" was displayed, and went to the "Profile" activation  */

                                        Toast.makeText(ProfileEditPassword.this, "Error", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();

                                    }
                                }
                            });
                        } else {
                            /** If the password was not changed with an error, then the message "The current password is incorrect. Check for correctness!" Was displayed, and went to the "Profile" activation  */

                            Toast.makeText(ProfileEditPassword.this, "The current password is invalid. Check for correctness!", Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        }
                    }
                });









            }
        });
        /** Create a Cancel button*/

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(ProfileEditPassword.this, WelcomeScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

    }
}
