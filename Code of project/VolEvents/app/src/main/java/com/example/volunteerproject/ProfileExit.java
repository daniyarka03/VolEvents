package com.example.volunteerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileExit extends AppCompatActivity {
	TextView exit_title;
	ImageView exit_img;
	Button signOutBtn, cancelBtn;
	Animation topAnim, bottomAnim;
	FirebaseAuth fAuth;
	FirebaseFirestore fStore;
	String userId, nameString, lastnameString, fullnameString, emailString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_exit);
		exit_title = findViewById(R.id.exit__title);
		exit_img = findViewById(R.id.exit_img);
		signOutBtn = findViewById(R.id.sign_out_btn);
		cancelBtn = findViewById(R.id.exit_cancel_btn);

		topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
		bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

		fAuth = FirebaseAuth.getInstance();
		fStore = FirebaseFirestore.getInstance();
		userId = fAuth.getCurrentUser().getUid();

		exit_title.setAnimation(topAnim);
		exit_img.setAnimation(topAnim);
		signOutBtn.setAnimation(topAnim);
		cancelBtn.setAnimation(topAnim);

		signOutBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/** When you click "Exit", go to the main activity */
				FirebaseAuth.getInstance().signOut();

				Intent intent = new Intent(ProfileExit.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);

			}
		});

		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProfileExit.this, WelcomeScreenActivity.class);
				startActivity(intent);
			}
		});

	}
}