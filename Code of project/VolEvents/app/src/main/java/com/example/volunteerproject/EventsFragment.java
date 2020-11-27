package com.example.volunteerproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

	/** Declaring variables */



	RecyclerView mRecyclerView;
	List<Model> modelList = new ArrayList<>();
	RecyclerView.LayoutManager layoutManager;


	FloatingActionButton mAddBtn;
	TextView listTitle;
	FirebaseFirestore fStore;

	CustomAdapter adapter;
	Animation topAnim, bottomAnim;


	ProgressDialog pd;
	private long backPressedTime;
	private Toast backToast;


	public EventsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/** Finding a unique identifier for each variable */

		View v =  inflater.inflate(R.layout.fragment_events, container, false);
		fStore = FirebaseFirestore.getInstance();
		mRecyclerView = v.findViewById(R.id.reccycler_view);
		mAddBtn = v.findViewById(R.id.addBtn);
		listTitle = v.findViewById(R.id.list__title);

		/**Finding a unique identifier for animation*/

		topAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.top_animation);
		bottomAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_animation);
		/** Setting animation for each variable */

		listTitle.setAnimation(topAnim);
		mRecyclerView.setAnimation(topAnim);



		mAddBtn.setAnimation(bottomAnim);

		mAddBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), AddEventNameActivity.class));

			}
		});


		mRecyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(layoutManager);

		pd = new ProgressDialog(getActivity());

		showData();

		return v;

	}

	private void showData() {

		pd.setTitle("Loading data ...");
		pd.show();

		fStore.collection("EventList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task) {

				modelList.clear();
				pd.dismiss();
				for (DocumentSnapshot doc : task.getResult()) {
					/**Data output from the database*/

					Model model = new Model(doc.getString("id"),
							doc.getString("name"),
							doc.getString("date"),
							doc.getString("time"),
							doc.getString("userId")
					);
					modelList.add(model);
				}

				adapter = new CustomAdapter(EventsFragment.this, modelList);
				mRecyclerView.setAdapter(adapter);
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				pd.dismiss();
				/** Error message output */

				Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void deleteData(int index) {
		/** Data is being deleted */

		pd.setTitle("Deleting data ...\n");
		pd.show();

		fStore.collection("EventList").document(modelList.get(index).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				/** Deleting data from the database */

				Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
				showData();
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				/** Error message output */

				pd.dismiss();
				Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});


	}
}

