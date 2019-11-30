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

import androidx.annotation.Nullable;
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
    private RecyclerView.LayoutManager layoutManager;

    private Database database;
    private OnGetDataListener onGetDataListener;

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

        mRecyclerViewResultList = view.findViewById(R.id.recyclerViewResults);
        mSpinnerTasks = view.findViewById(R.id.spinnerTasksForResult);

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
                layoutManager = new GridLayoutManager(getContext(), 3);
                adapter = new ResultListAdapter(dataMap);
                mRecyclerViewResultList.setLayoutManager(layoutManager);
                mRecyclerViewResultList.setAdapter(adapter);
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
