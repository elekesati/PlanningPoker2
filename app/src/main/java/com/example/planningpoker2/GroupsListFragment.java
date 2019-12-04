package com.example.planningpoker2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;

public class GroupsListFragment extends DialogFragment {
    /**
     * variables list
     */
    private static final String TAG = "PlanningPokerGroupListFragment";
    private RecyclerView mRecyclerViewResultList;
    private RecyclerViewGroups adapter;
    private RecyclerView.LayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;
    OnGetDataListener onGetDataListener;
    Database database = new Database();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups_list, container, false);

        mRecyclerViewResultList = view.findViewById(R.id.rv_groups);
        mRecyclerViewResultList.setHasFixedSize(true);
        dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL);
        mRecyclerViewResultList.addItemDecoration(dividerItemDecoration);

        enableBackArrow(false);

        /**
         * when we click an item, return the item name
         * and then sent the scoring fragment with bundle
         */
        onGetDataListener = new OnGetDataListener() {
        @SuppressLint("LongLogTag")
        @Override
        public void onSuccess(final List<String> dataList) {
            layoutManager = new LinearLayoutManager(getContext());
            adapter = new RecyclerViewGroups(dataList);
            Log.d(TAG, dataList.toString());
            mRecyclerViewResultList.setLayoutManager(layoutManager);
            mRecyclerViewResultList.setAdapter(adapter);
            adapter.setClickListener(new RecyclerViewGroups.OnItemClickListener() {
                 @Override
                 public void onItemClick(int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("groupName", adapter.getItem(position));
                    ScoringFragment scoringFragment = new ScoringFragment();
                    scoringFragment.setArguments(bundle);
                    MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, scoringFragment, null).commit();
                 }
             });
        }

        @Override
        public void onSuccess(ArrayList<String> taskList) {

        }

        };
        database.getListGroups(onGetDataListener);

        return view;
    }

    private void enableBackArrow(boolean enable){
        ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(enable);
        supportActionBar.setDisplayHomeAsUpEnabled(enable);
        supportActionBar.setDisplayShowHomeEnabled(enable);
    }
}
