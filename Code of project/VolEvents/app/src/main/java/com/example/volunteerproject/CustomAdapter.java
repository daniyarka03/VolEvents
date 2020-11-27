package com.example.volunteerproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    /** Declaring variables */

    String userId, itemId, mUserId, uid;
    EventsFragment listEvent;
    List<Model> modelList;
    Context context;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser user;



    public CustomAdapter(EventsFragment listEvent, List<Model> modelList) {

        this.listEvent = listEvent;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_layout, viewGroup, false);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        DocumentReference documentReference = fStore.collection("users").document(user.getUid());


        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                /** When you click on an event, it switches to another activity*/

                String id = modelList.get(position).getId();
                String name = modelList.get(position).getName();
                String date = modelList.get(position).getDate();
                String time = modelList.get(position).getTime();

                Intent intent = new Intent(listEvent.getActivity(), EventInfoActivity.class);
                intent.putExtra("pId", id);
                listEvent.startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {


                /** When clicking on an event, a context menu appeared with two options */

                AlertDialog.Builder builder = new AlertDialog.Builder(listEvent.getActivity());
                String[] options = {"Change", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        /** Condition is met when clicking refresh */

                        if (which == 0) {
                            String id = modelList.get(position).getId();
                            String name = modelList.get(position).getName();
                            String date = modelList.get(position).getDate();
                            String time = modelList.get(position).getTime();

                            Intent intent = new Intent(listEvent.getActivity(), EventActivity.class);
                            intent.putExtra("pId", id);
                            intent.putExtra("pName", name);
                            intent.putExtra("pDate", date);
                            intent.putExtra("pTime", time);

                            listEvent.startActivity(intent);
                        }

                        /** If the user selected the option to delete, then the event is deleted. */

                        if (which == 1) {
                            listEvent.deleteData(position);
                        }
                    }
                }).create().show();
            }

        });
        return viewHolder;
    }








    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        /** When starting this activity, all the relevant information is entered */

        viewHolder.mNameTv.setText(modelList.get(i).getName());
        viewHolder.mDateTv.setText(modelList.get(i).getDate());
        viewHolder.mTimeTv.setText(modelList.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
