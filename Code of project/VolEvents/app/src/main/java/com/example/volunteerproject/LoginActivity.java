package com.example.volunteerproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    /** Declaring variables  */

    EditText mEmail, mPassword;
    Button mLoginBtn;
    Animation topAnim, bottomAnim;
    ImageView door, wave;
    TextView forgotPassword, login__button_reg, loginTitle;
    FirebaseAuth fAuth;
    ProgressDialog pd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);
        /** Finding a unique identifier for each variable  */


        login__button_reg = findViewById(R.id.login__button_reg);
        loginTitle = findViewById(R.id.login__title);
        door = findViewById(R.id.door);
        wave = findViewById(R.id.wave);
        mEmail = findViewById(R.id.login__email);
        mPassword = findViewById(R.id.login__password);
        mLoginBtn = findViewById(R.id.login__button);

        /**Finding a unique identifier for animation*/

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        forgotPassword = findViewById(R.id.login__forgotPassword);

        /** Setting animation for each variable */

        wave.setAnimation(topAnim);
        door.setAnimation(topAnim);
        loginTitle.setAnimation(topAnim);
        mEmail.setAnimation(topAnim);
        mPassword.setAnimation(topAnim);
        mLoginBtn.setAnimation(bottomAnim);
        forgotPassword.setAnimation(bottomAnim);
        login__button_reg.setAnimation(bottomAnim);

        fAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);



        if(fAuth.getCurrentUser() != null) {
            /** If an authorized user entered the application, then he went to the "Menu"   */

            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            finish();
        }
        /** Create an event for the "Login" button */

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                /** If the "email" field is empty, let it give the error "Email required!" */



                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("E-mail required!");
                    return;
                }
                /** If the "password" field is empty, let the error "Password is required!" */

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password required!");
                    return;
                }
                /** If the password is less than 6 characters, let the error "Password must be at least 6 characters!"  */

                if (password.length() < 6) {
                    mPassword.setError("Password must be at least 6 characters!");
                    return;
                }

                pd.setTitle("Login to your account");
                pd.show();

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /** If the user successfully logged in, then went to the next activity  */

                        if(task.isSuccessful()) {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            /** If the user logged in with an error, it would return "Error!" */

                        } else {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        /**Create an event for the "Register" button */

        login__button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActvity.class);
                startActivity(intent);
            }
        });


        /** Create an event for the "Forgot your password?"*/

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());

                passwordResetDialog.setTitle("Forgot your password?");
                passwordResetDialog.setMessage("Enter your e-mail to recover your password!");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Send!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                /** If the recovery was successful, the message "Password recovery letter has been sent to your Email."*/

                                Toast.makeText(LoginActivity.this, "A password recovery letter has been sent to your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                /** If recovery failed, the message "Error! Password recovery email was not sent!"  */

                                Toast.makeText(LoginActivity.this, "Error! Password recovery email was not sent! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();


            }
        });



    }
}
