package com.example.planningpoker2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;


public class GroupsFragment extends Fragment {

    private RecyclerView mRecyclerViewResultList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<String> groups = new ArrayList<>();
    DividerItemDecoration dividerItemDecoration;

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
        mRecyclerViewResultList.addItemDecoration(dividerItemDecoration);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecyclerViewGroups(groups);
        mRecyclerViewResultList.setLayoutManager(layoutManager);
        mRecyclerViewResultList.setAdapter(adapter);
        return view;
    }

}
