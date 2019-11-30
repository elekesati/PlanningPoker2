package com.example.planningpoker2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;

public class GroupsListFragment extends Fragment {
    /**
     * variables list
     */
    private static final String TAG = "PlanningPokerGroupListFragment";
    private RecyclerView mRecyclerViewResultList;
    private RecyclerViewGroups adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnAddNewGroup;
    DividerItemDecoration dividerItemDecoration;
    OnGetDataListener onGetDataListener;
    Database database = new Database();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups_list, container, false);

        mRecyclerViewResultList = view.findViewById(R.id.rv_groups);
        btnAddNewGroup = view.findViewById(R.id.btn_add_group);
        mRecyclerViewResultList.setHasFixedSize(true);
        dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL);
        mRecyclerViewResultList.addItemDecoration(dividerItemDecoration);

        onGetDataListener = new OnGetDataListener() {
        @Override
        public void onSuccess(final List<String> dataList) {
            layoutManager = new LinearLayoutManager(getContext());
            adapter = new RecyclerViewGroups(dataList);
            mRecyclerViewResultList.setLayoutManager(layoutManager);
            mRecyclerViewResultList.setAdapter(adapter);
            adapter.setClickListener(new RecyclerViewGroups.OnItemClickListener() {
                 @Override
                 public void onItemClick(int position) {
                    Toast.makeText(getActivity(),"Clicked" + adapter.getItem(position) + "on row number" + position, Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("groupName", adapter.getItem(position));
                    ScoringFragment scoringFragment = new ScoringFragment();
                    scoringFragment.setArguments(bundle);
                    MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, scoringFragment, null).commit();
//                    onGetDataListener = new OnGetDataListener() {
//                        @SuppressLint("LongLogTag")
//                        @Override
//                        public void onSuccess(List<String> dataList) {
//
//                        }
//
//                        @SuppressLint("LongLogTag")
//                        @Override
//                        public void onSuccess(ArrayList<String> taskList) {
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("taskList", taskList);
//                            ScoringFragment scoringFragment = new ScoringFragment();
//                            scoringFragment.setArguments(bundle);
//                            Log.d(TAG, "Meghivta valamiert ezt");
//                            MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, scoringFragment, null).commit();
//                        }
//
//                    };
                    database.getTaskList(adapter.getItem(position), onGetDataListener);
                 }
             });
        }

        @Override
        public void onSuccess(ArrayList<String> taskList) {

        }

        };
        database.getListGroups(onGetDataListener);


        btnAddNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, new AddGroupsFragment(),null).commit();
            }
        });
        return view;
    }



}
