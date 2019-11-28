package com.example.planningpoker2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private static final String TAG = "PlanningPokerDatabase";
    DatabaseReference myRef;
    FirebaseDatabase mDatabase;

    public void addNewTaskToAGroup(String groupName, String taskName){
        Task task = new Task(taskName);
        FirebaseDatabase.getInstance().getReference("Groups")
                .child(groupName)
                .child(groupName)
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
}
