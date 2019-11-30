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

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskVisibilityHandlerFragment extends Fragment {
    private static final String TAG = "PlanningPokerVisible";
    private ResultFragment.OnHandlerFragmentInteractionListener mListener;

    private RecyclerView mRecyclerViewTaskList;
    private Spinner mSpinnerTasks;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Database database;
    private OnGetDataListener onGetDataListener;

    public TaskVisibilityHandlerFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Creating view");

        View view = getView();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_result, container, false);
        }

        mRecyclerViewTaskList = view.findViewById(R.id.recyclerViewTaskSettings);
        mSpinnerTasks = view.findViewById(R.id.spinnerTasksForVisibility);

        mSpinnerTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
            }
        });

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
            public void onSuccess(Map<String, Double> dataMap) {
                layoutManager = new LinearLayoutManager(getContext());
                adapter = new VisibilitySettingsListAdapter(dataMap);
                mRecyclerViewTaskList.setLayoutManager(layoutManager);
                mRecyclerViewTaskList.setAdapter(adapter);
            }
        };

        //database.getAverage(onGetDataListener);

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
}
