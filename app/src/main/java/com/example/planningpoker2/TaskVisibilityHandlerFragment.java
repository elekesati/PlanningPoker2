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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskVisibilityHandlerFragment extends Fragment{
    private static final String TAG = "PlanningPokerVisible";
    private ResultFragment.OnHandlerFragmentInteractionListener mListener;

    private RecyclerView mRecyclerViewTaskList;
    private Spinner mSpinnerTasks;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Database database;
    private OnGetDataListener onGetDataListener;
    private OnGetDataListener onGetDataListenerVisibility;

    private ArrayList<String> mTaskList;
    private ArrayList<String> mVisibilityList;

    public TaskVisibilityHandlerFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Creating view");

        View view = getView();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_task_visibility_handler, container, false);
        }

        mRecyclerViewTaskList = view.findViewById(R.id.recyclerViewTaskSettings);
        mSpinnerTasks = view.findViewById(R.id.spinnerTasksForVisibility);

        database = new Database();
        onGetDataListener = new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> dataList) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item, dataList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerTasks.setAdapter(adapter);
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
                layoutManager = new LinearLayoutManager(getContext());
                adapter = new VisibilitySettingsListAdapter(mTaskList, mVisibilityList,
                        TaskVisibilityHandlerFragment.this);
                mRecyclerViewTaskList.setLayoutManager(layoutManager);
                mRecyclerViewTaskList.setAdapter(adapter);
            }
        };

        database.getListGroups(onGetDataListener);

        mSpinnerTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                database.getTaskList(mSpinnerTasks.getItemAtPosition(i).toString(), onGetDataListener);
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
}
