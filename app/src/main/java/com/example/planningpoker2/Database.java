package com.example.planningpoker2;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String TAG = "PlanningPokerDatabase";
    DatabaseReference myRef;
    FirebaseDatabase mDatabase;
    List<String> groupNames = new ArrayList<>();
    ArrayList<String> taskList = new ArrayList<>();

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

    /**
     * return list of the groups
     * @param onGetDataListener
     */
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

    /**
     * return elements of a special group from firebase
     * @param groupName - which group elements do we return
     */
    public void getTaskList(final String groupName, final OnGetDataListener onGetDataListener){
        mDatabase =FirebaseDatabase.getInstance();
        myRef =mDatabase.getReference("Groups");
        removeArrayList(taskList);
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if (ds.getKey().matches(groupName)){
                        for (DataSnapshot tasks : ds.getChildren()){
                            taskList.add(tasks.getKey());
                        }
                    }
                }
                onGetDataListener.onSuccess(taskList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * if arraylist is not empty, delete all elements
     * @param list - which list do we want to delete
     */
    void removeArrayList(ArrayList<String> list){
        if(!list.isEmpty()){
            list.removeAll(list);
        }
    }

    /**
     * add new score, if someone vote a task
     * @param mGroupName - which group has the task
     * @param task_name - which task
     * @param score - the value
     */
    public void addTaskScoreToDatabase(String mGroupName,String task_name, String score){
        FirebaseDatabase.getInstance().getReference("Groups")
                .child(mGroupName)
                .child(task_name)
                .child("Scores")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(score)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if (!task.isSuccessful()){
                            Log.d(TAG, "Doesn't add score");
                        }
                    }
                });
    }
}
