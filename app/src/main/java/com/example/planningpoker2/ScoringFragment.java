package com.example.planningpoker2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScoringFragment extends Fragment {

    private Database database;
    private OnGetDataListener onGetDataListener;
    private TextView mTask,mNext, mValue, mPrev,mSkip;
    private List<String> mNumbers = new ArrayList<>();
    private Integer count=0;
    private static final String TAG = "PlanningPokerNewScore";
    private String mLastElement;

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
        return view;
    }

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
}
