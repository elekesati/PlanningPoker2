package com.example.planningpoker2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddGroupsFragment extends Fragment {

    private Button mAddQuestion, mBack, mSubmit;
    private EditText mNewTask, mGroupName;
    private TextView mTasksList;
    Database database = new Database();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_groups, container, false);
        mAddQuestion = view.findViewById(R.id.btn_addquestion);
        mNewTask = view.findViewById(R.id.et_newquestion);
        mTasksList = view.findViewById(R.id.tv_questions);
        mGroupName = view.findViewById(R.id.et_groupname);
        mBack = view.findViewById(R.id.btn_backtogroups);
        mSubmit = view.findViewById(R.id.btn_Submit);
        mTasksList.setMovementMethod(new ScrollingMovementMethod());

        mAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskAndCheckField();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGroupName.getText().toString().matches("")){
                    database.removeTaskIfClickBackButton(mGroupName.getText().toString());
                }
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new GroupsFragment(),null).commit();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new GroupsFragment(),null).commit();
            }
        });
        return view;
    }

    public void addTaskAndCheckField(){
        // check if group name is empty show an error message
        if (!mGroupName.getText().toString().matches("")){
            if (mNewTask.getText().toString().matches("")){
                mNewTask.setError("Pleas fill with task name");
                mNewTask.requestFocus();
            }else {
                addNewTask();
            }
        }
        else {
            mGroupName.setError("Please enter the group name");
            mGroupName.requestFocus();
        }
    }

    public void addNewTask(){
        String text = mNewTask.getText().toString();
        String oldText = mTasksList.getText().toString();
        String newText;
        if (!oldText.matches("")){
            newText = oldText + "\n" + text;
        }
        else{
            newText = text;
        }
        mTasksList.setText(newText);
        mNewTask.setText("");
        database.addNewTaskToAGroup(mGroupName.getText().toString(),text);
    }

}
