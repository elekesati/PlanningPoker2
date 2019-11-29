package com.example.planningpoker2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;


public class GroupsFragment extends Fragment {

    private RecyclerView mRecyclerViewResultList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<String> groups = new ArrayList<>();
    DividerItemDecoration dividerItemDecoration;
    private Button btnAddNewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_groups, container, false);
        groups.add("2020-10-26");
        groups.add("2200-15-36");
        groups.add("2200-15-36");
        groups.add("2200-15-36");
        groups.add("2200-15-36");
        groups.add("2200-15-36");
        mRecyclerViewResultList = view.findViewById(R.id.rv_groups);
        dividerItemDecoration = new DividerItemDecoration(getContext(), VERTICAL);
        btnAddNewGroup = view.findViewById(R.id.btn_add_group);
        mRecyclerViewResultList.addItemDecoration(dividerItemDecoration);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecyclerViewGroups(groups);
        mRecyclerViewResultList.setLayoutManager(layoutManager);
        mRecyclerViewResultList.setAdapter(adapter);

        btnAddNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, new AddGroupsFragment(),null).commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Toolbar toolbar = getView().findViewById(R.id.toolbar);
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, new AddGroupsFragment(),null).commit();
            }
        });
    }
}
