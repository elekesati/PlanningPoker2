package com.example.planningpoker2;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
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
    ArrayList<String> taskList = new ArrayList<>();

    ArrayList<String> resultList = new ArrayList<>();
    ArrayList<String> visibilityList = new ArrayList<>();

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


    public void getTaskVisibilityByGroup(final String groupName, final OnGetDataListener onGetDataListener){
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("Groups" + "/" + groupName);

        removeArrayList(visibilityList);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    visibilityList.add(ds.child("mStatus").getValue().toString());
                }

                onGetDataListener.onSuccess(visibilityList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * returns the averages of the results by group
     * @param groupName - which group has the task
     */
    public void getTaskResultsByGroup(final String groupName, final OnGetDataListener onGetDataListener){
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("Groups" + "/" + groupName);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 0;
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Long result = 0L;
                    for (DataSnapshot ds2 : ds.child("Scores").getChildren()){
                        result += Long.parseLong(ds2.getValue().toString());
                    }
                    if(ds.child("Scores").getChildrenCount() != 0){
                        result /= ds.child("Scores").getChildrenCount();
                    }
                    resultList.add(index, result.toString());
                    ++index;
                }

                onGetDataListener.onSuccess(resultList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateVisibility(String group, String task, Boolean isVisible){
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("Groups" + "/" + group + "/" + task);
        if(isVisible){
            myRef.child("mStatus").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    if (!task.isSuccessful()){
                        Log.d(TAG, "Cannot set visibility");
                    }
                }
            });
        }
        else{
            myRef.child("mStatus").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    if (!task.isSuccessful()){
                        Log.d(TAG, "Cannot set visibility");
                    }
                }
            });
        }
    }

    public void getUserStatus(final OnGetDataListener onGetDataListener){
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("Users" +
                "/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("mStatus").getValue().toString();
                ArrayList<String> result = new ArrayList<>();
                result.add(status);
                onGetDataListener.onSuccess(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
