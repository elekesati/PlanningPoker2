package com.example.planningpoker2;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Database {
    private static final String TAG = "PlanningPokerDatabase";
    DatabaseReference myRef;
    FirebaseDatabase mDatabase;
    List<String> groupNames = new ArrayList<>();
    List<String> tasks = new ArrayList<>();

    public void addNewTaskToAGroup(String groupName, String taskName){
        Task task = new Task(taskName);
        FirebaseDatabase.getInstance().getReference("Groups")
                .child(groupName)
                .child(taskName)
                .setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if (!task.isSuccessful()){
                    Log.d(TAG, "Doesn't add task");
                }
            }
        });
    }

    public void removeTaskIfClickBackButton(String groupName){
        FirebaseDatabase.getInstance().getReference("Groups")
                .child(groupName)
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if (!task.isSuccessful()){
                    Log.d(TAG, "Doesn't add task");
                }
            }
        });
    }

    public void getListGroups(final OnGetDataListener onGetDataListener){
        mDatabase =FirebaseDatabase.getInstance();
        myRef =mDatabase.getReference("Groups");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    groupNames.add(ds.getKey().toString());
                }
                onGetDataListener.onSuccess(groupNames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
