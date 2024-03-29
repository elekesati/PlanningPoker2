package com.example.planningpoker2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;

public class ScoringFragment extends DialogFragment {

    private static final String TAG = "PlanningPokerNewScore";
    private Database database;
    private TextView mTask,mNext, mValue, mPrev,mSkip;
    private List<String> mNumbers = new ArrayList<>();
    private Integer count=0;
    private String mLastElement,mGroupName;
    OnGetDataListener onGetDataListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scoring, container, false);
        addElementToArrayList();
        mTask = view.findViewById(R.id.tv_tasks);
        mNext = view.findViewById(R.id.tv_next);
        mValue = view.findViewById(R.id.tv_numbers2);
        mPrev = view.findViewById(R.id.tv_prev);
        mSkip = view.findViewById(R.id.tv_skips);
        database = new Database();
        openDialog();
        getDataFromBundle();
        enableBackArrow(false);
        MainActivity.showMenu(false);

        return view;
    }

    /**
     * which numbers we can vote, we add manualy
     */
    public void addElementToArrayList(){
        mNumbers.add("1");
        mNumbers.add("2");
        mNumbers.add("3");
        mNumbers.add("5");
        mNumbers.add("8");
        mNumbers.add("13");
        mNumbers.add("21");
        mNumbers.add("34");
        mNumbers.add("55");
    }

    /**
     * get datas from bundles to mTasksList
     */
    void getDataFromBundle(){
        Bundle bundle = getArguments();
        mGroupName = bundle.getString("groupName");
        scoring(mGroupName);
    }

    /**
     * show the task name, and someone whose join the group, should vote the actual task
     * if you does not want, you can skip
     * @param mGroupName - return taskList to a group
     */
    public void scoring(final String mGroupName){
        onGetDataListener = new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> dataList) {

            }

            @Override
            public void onSuccess(final ArrayList<String> taskList) {
                mLastElement = taskList.get(taskList.size()-1);
                mTask.setText(taskList.get(count));
                mNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer index = mNumbers.indexOf(mValue.getText());
                        if (index+1<mNumbers.size()){
                            mValue.setText(mNumbers.get(index + 1));
                        }
                    }
                });

                mPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer index = mNumbers.indexOf(mValue.getText());
                        if (index > 0){
                            mValue.setText(mNumbers.get(index-1));
                        }
                    }
                });

                mValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTask.getText() != mLastElement){
                            Database db = new Database();
                            db.addTaskScoreToDatabase(mGroupName,taskList.get(count),mValue.getText().toString());
                            mValue.setText("1");
                            count = count + 1;
                            mTask.setText(taskList.get(count));
                        }
                        else {
                            Database db = new Database();
                            db.addTaskScoreToDatabase(mGroupName,taskList.get(count),mValue.getText().toString());
                            enableBackArrow(true);
                            MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, new GroupsListFragment(),null).commit();
                        }
                    }
                });
                mSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTask.getText() != mLastElement){
                            mValue.setText("1");
                            count = count + 1;
                            mTask.setText(taskList.get(count));
                        }
                        else{
                            enableBackArrow(true);
                            MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, new GroupsListFragment(),null).commit();
                        }
                    }
                });
            }
        };

        database.getTaskList(mGroupName, onGetDataListener);
    }

    /**
     * when you click to an item, show a message, which ask for you, are you ready to start
     */
    public void openDialog(){
        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.show(getFragmentManager(), "example dialog");
    }

    private void enableBackArrow(boolean enable){
        ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(enable);
        supportActionBar.setDisplayHomeAsUpEnabled(enable);
        supportActionBar.setDisplayShowHomeEnabled(enable);
    }
}
