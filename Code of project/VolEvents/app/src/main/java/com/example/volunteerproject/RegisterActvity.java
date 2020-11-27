package com.example.volunteerproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActvity extends AppCompatActivity {

    /**Declaring variables  */

    EditText mName, mLastname, mEmail, mPassword;
    TextView mLoginBtn, registerTitle, already_account;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    ImageView register__img;
    Animation topAnim, bottomAnim;
    FirebaseFirestore fStore;
    String userID;
    String TAG;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actvity);
        /** Finding a unique identifier for each variable  */


        mName = findViewById(R.id.register__name);
        mLastname = findViewById(R.id.register__lastname);
        mEmail = findViewById(R.id.register__email);
        mPassword = findViewById(R.id.register__password);
        mRegisterBtn = findViewById(R.id.register__button);
        mLoginBtn = findViewById(R.id.register__button_login);
        register__img = findViewById(R.id.register__img);
        registerTitle = findViewById(R.id.register__title);
        already_account = findViewById(R.id.already_account);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        /** Setting animation for each variable */

        register__img.setAnimation(topAnim);
        registerTitle.setAnimation(topAnim);
        mName.setAnimation(topAnim);
        mLastname.setAnimation(topAnim);
        mEmail.setAnimation(topAnim);
        mPassword.setAnimation(topAnim);
        register__img.setAnimation(topAnim);

        mRegisterBtn.setAnimation(bottomAnim);
        mLoginBtn.setAnimation(bottomAnim);
        already_account.setAnimation(bottomAnim);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        pd = new ProgressDialog(this);


        /** Create an event of the button click "Register"  */

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String name = mName.getText().toString();
                final String lastname = mLastname.getText().toString();


                /** If the "email" field is empty, let it give the error "Email required!"  */

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email required!");
                    return;
                }
                /** If the "password" field is empty, let the error "Password is required!"  */

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password required!");
                    return;
                }
                /** If the password is less than 6 characters, let the error "Password must be at least 6 characters!"  */

                if (password.length() < 6) {
                    mPassword.setError("Password must be at least 6 characters!");
                    return;
                }
                /** If the "Name" field is empty, the error "Fill in the field!"  */

                if (name.isEmpty()) {
                    mName.setError("Fill in the field!");
                    return;
                }
                /** If the "Last name" field is empty, the error "Fill in the field!"  */

                if (lastname.isEmpty()) {
                    mLastname.setError("Fill in the field!");
                    return;
                }

        if(fAuth.getCurrentUser() != null) {
            /** If a registered user entered the application, then he went to the "Menu" activity   */

            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish();
        }

                pd.setTitle("Account registration");
                pd.show();

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActvity.this, "User created!", Toast.LENGTH_SHORT).show();

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", name);
                            user.put("fLastName", lastname);
                            user.put("email", email);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>(){
                               @Override
                               public void onSuccess(Void aVoid) {
                                   /** If authorization was successful, I went to the next activity  */
                                   pd.dismiss();
                               }
                            });



                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));



                        } else {
                            /** If the authorization failed, it displayed "Error!"   */

                            pd.dismiss();
                            Toast.makeText(RegisterActvity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        /** Create an event for the "Login" button  */

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActvity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}
