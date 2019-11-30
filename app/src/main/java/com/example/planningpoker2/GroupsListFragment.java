package com.example.planningpoker2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import static android.widget.LinearLayout.VERTICAL;

public class GroupsListFragment extends Fragment {

    private RecyclerView mRecyclerViewResultList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;
    private Button btnAddNewGroup;
    OnGetDataListener onGetDataListener;
    Database database = new Database();
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups_list, container, false);
        mRecyclerViewResultList = view.findViewById(R.id.rv_groups);
        dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL);
        btnAddNewGroup = view.findViewById(R.id.btn_add_group);
        mRecyclerViewResultList.addItemDecoration(dividerItemDecoration);
        onGetDataListener = new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> dataList) {
                layoutManager = new LinearLayoutManager(getContext());
                adapter = new RecyclerViewGroups(dataList);
                mRecyclerViewResultList.setLayoutManager(layoutManager);
                mRecyclerViewResultList.setAdapter(adapter);
            }

            @Override
            public void onSuccess(Map<String, Double> dataMap) {

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
