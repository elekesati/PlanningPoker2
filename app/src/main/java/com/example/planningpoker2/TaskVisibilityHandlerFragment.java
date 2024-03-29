package com.example.planningpoker2;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskVisibilityHandlerFragment extends Fragment{
    private static final String TAG = "PlanningPokerVisible";
    private ResultFragment.OnHandlerFragmentInteractionListener mListener;

    private RecyclerView mRecyclerViewTaskList;
    private Spinner mSpinnerTasks;

    private VisibilitySettingsListAdapter mVisibilitySettingsListAdapter;
    private ArrayAdapter<String> mSpinnerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Database database;
    private OnGetDataListener onGetDataListener;
    private OnGetDataListener onGetDataListenerVisibility;

    private ArrayList<String> mTaskList;
    private ArrayList<String> mVisibilityList;

    private int mSelectedSpinnerItem = 0;

    public TaskVisibilityHandlerFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        Log.d(TAG, "Creating view");

        View view = getView();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_task_visibility_handler, container, false);
        }

        mRecyclerViewTaskList = view.findViewById(R.id.recyclerViewTaskSettings);
        mSpinnerTasks = view.findViewById(R.id.spinnerTasksForVisibility);

        enableBackArrow(true);
        MainActivity.showMenu(true);

        database = new Database();
        onGetDataListener = new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> dataList) {
                try{
                    if (mSpinnerAdapter == null){
                        Log.d(TAG, "Setting adapter for spinner");
                        mSpinnerAdapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_spinner_item, dataList);
                        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpinnerTasks.setAdapter(mSpinnerAdapter);
                    }
                    else{
                        mSpinnerAdapter.notifyDataSetChanged();
                    }
                    mSpinnerTasks.setSelection(mSelectedSpinnerItem);
                }catch(NullPointerException e){
                    Log.d(TAG, "Null when get group list for spinner");
                }
            }

            @Override
            public void onSuccess(ArrayList<String> taskList) {
                mTaskList = taskList;
                Log.d(TAG, "TaskList OK");
            }
        };

        onGetDataListenerVisibility = new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> dataList) {

            }

            @Override
            public void onSuccess(ArrayList<String> taskList) {
                mVisibilityList = taskList;
                if(mVisibilitySettingsListAdapter == null) {
                    layoutManager = new LinearLayoutManager(getContext());
                    mVisibilitySettingsListAdapter = new VisibilitySettingsListAdapter(mTaskList, mVisibilityList, TaskVisibilityHandlerFragment.this);
                    mRecyclerViewTaskList.setLayoutManager(layoutManager);
                    mRecyclerViewTaskList.setAdapter(mVisibilitySettingsListAdapter);
                }
                else {
                    //mVisibilitySettingsListAdapter.setTaskList(mTaskList);
                    //mVisibilitySettingsListAdapter.setVisibilityList(mVisibilityList);
                    mVisibilitySettingsListAdapter.notifyDataSetChanged();
                }
            }
        };

        database.getListGroups(onGetDataListener);

        mSpinnerTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedSpinnerItem = i;
                database.getAllTask(mSpinnerTasks.getItemAtPosition(i).toString(), onGetDataListener);
                database.getTaskVisibilityByGroup(mSpinnerTasks.getItemAtPosition(i).toString(), onGetDataListenerVisibility);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Back button click");
                MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        new GroupsListFragment(),null).commit();
            }
        });
    }

    public void onSwitchSateChanged(String task, Boolean isEnabled){
        String group = mSpinnerTasks.getSelectedItem().toString();
        database.updateVisibility(group, task, isEnabled);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.replaceFragment(0);
        }
    }

    public interface OnHandlerFragmentInteractionListener {
        void replaceFragment(int fragment);
    }

    private void enableBackArrow(boolean enable){
        ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(enable);
        supportActionBar.setDisplayHomeAsUpEnabled(enable);
        supportActionBar.setDisplayShowHomeEnabled(enable);
    }
}
