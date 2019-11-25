package com.example.planningpoker2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewGroups extends RecyclerView.Adapter<RecyclerViewGroups.ViewHolder> {

    public static final String TAG ="PlanningPokerGroupsRecyclerView";
    List<Object> groups = new ArrayList<>();

    public RecyclerViewGroups(List<String> myList){
        this.groups = Arrays.asList(myList.toArray());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTextView = itemView.findViewById(R.id.tv_groups);
        }
    }

    @NonNull
    @Override
    public RecyclerViewGroups.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        RecyclerViewGroups.ViewHolder resultViewHolder = new RecyclerViewGroups.ViewHolder(view);
        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewGroups.ViewHolder holder, int position) {
        ((TextView) holder.mTextView.findViewById(R.id.tv_groups)).setText(groups.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

}
