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

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ResultFragment extends Fragment {
    private static final String TAG = "PlanningPokerResult";
    private OnHandlerFragmentInteractionListener mListener;

    private RecyclerView mRecyclerViewResultList;
    private Spinner mSpinnerTasks;

    private RecyclerView.Adapter adapter;
    private ArrayAdapter<String> mSpinnerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Database database;
    private OnGetDataListener onGetDataListener;
    private OnGetDataListener onGetDataListenerResults;

    private ArrayList<String> mTaskList;
    private ArrayList<String> mResultList;

    private int mSelectedSpinnerItem = 0;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Creating view");

        View view = getView();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_result, container, false);
        }

        enableBackArrow(true);
        MainActivity.showMenu(true);

        mRecyclerViewResultList = view.findViewById(R.id.recyclerViewResults);
        mSpinnerTasks = view.findViewById(R.id.spinnerTasksForResult);

        database = new Database();
        onGetDataListener = new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> dataList) {
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
            }

            @Override
            public void onSuccess(ArrayList<String> taskList) {
                mTaskList = taskList;
            }
        };

        onGetDataListenerResults = new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> dataList) {

            }

            @Override
            public void onSuccess(ArrayList<String> taskList) {
                mResultList = taskList;
                if(adapter == null){
                    layoutManager = new GridLayoutManager(getContext(), 3);
                    adapter = new ResultListAdapter(mResultList, mTaskList);
                    mRecyclerViewResultList.setLayoutManager(layoutManager);
                    mRecyclerViewResultList.setAdapter(adapter);
                }
                else{
                    adapter.notifyDataSetChanged();
                }
            }
        };

        database.getListGroups(onGetDataListener);

        mSpinnerTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedSpinnerItem = i;
                database.getTaskList(mSpinnerTasks.getItemAtPosition(i).toString(), onGetDataListener);
                database.getTaskResultsByGroup(mSpinnerTasks.getItemAtPosition(i).toString(), onGetDataListenerResults);
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
