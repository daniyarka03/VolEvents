package com.example.volunteerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

	TextView profile__name, profile__last_name, changeBasicInfo, changeEmail, changePassword, profile_menu, name, lastname, profile__email;
	FirebaseAuth fAuth;
	private CircleImageView profileAvatar;
	ImageView profile_info;
	private DatabaseReference databaseReference;
	Animation topAnim, bottomAnim;
	FirebaseFirestore fStore;
	String userId, nameString, lastnameString, fullnameString, emailString;
	RelativeLayout profile__edit_basic_info, profile_edit_email, profile_edit_password, profile_edit_avatar, profile__show;
	private Button btnLogout;

	public ProfileFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_profile, container, false);


		databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
		/** Finding a unique identifier for each variable */

		btnLogout = v.findViewById(R.id.profile__logout);
		profile__edit_basic_info = v.findViewById(R.id.profile__edit_basic_info);
		profile_info = v.findViewById(R.id.profile_info);
		profile_edit_email = v.findViewById(R.id.profile__edit_email);
		profile_edit_password = v.findViewById(R.id.profile__edit_password);
		profile_menu = v.findViewById(R.id.profile_menu);
		changeBasicInfo = v.findViewById(R.id.change_basic_info);
		changeEmail = v.findViewById(R.id.change_email);
		changePassword = v.findViewById(R.id.change_password);


		profileAvatar = v.findViewById(R.id.profile_avatar);
		profile__name = v.findViewById(R.id.profile__name_user);
		profile__last_name = v.findViewById(R.id.profile__last_name_user);

		fAuth = FirebaseAuth.getInstance();
		fStore = FirebaseFirestore.getInstance();
		userId = fAuth.getCurrentUser().getUid();
		/**Finding a unique identifier for animation*/

		topAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.top_animation);
		bottomAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_animation);

		/** Setting animation for each variable */

		profile_menu.setAnimation(topAnim);
		profile_info.setAnimation(topAnim);
		profileAvatar.setAnimation(topAnim);
		profile__name.setAnimation(topAnim);
		profile__last_name.setAnimation(topAnim);

		changeBasicInfo.setAnimation(bottomAnim);
		changeEmail.setAnimation(bottomAnim);
		changePassword.setAnimation(bottomAnim);

		btnLogout.setAnimation(bottomAnim);

		DocumentReference documentReference = fStore.collection("users").document(userId);

		documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
			@Override
			public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
				profile__name.setText(documentSnapshot.getString("fName"));
				profile__last_name.setText(documentSnapshot.getString("fLastName"));

			}
		});


		profile__edit_basic_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileEditBasicInfo.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		profile_edit_email.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileEditEmail.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		profile_edit_password.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ProfileEditPassword.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});







		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/** When you click "Exit", go to the main activity */

				Intent intent = new Intent(getActivity(), ProfileExit.class);
				startActivity(intent);

			}
		});
		return v;
	}

}




