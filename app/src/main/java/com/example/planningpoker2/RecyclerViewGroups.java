package com.example.planningpoker2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewGroups extends RecyclerView.Adapter<RecyclerViewGroups.ViewHolder> {

    public static final String TAG ="PlanningPokerGroupsRecyclerView";
    List<String> groups;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public RecyclerViewGroups(List<String> myList){
        this.groups = myList;
    }

    @NonNull
    @Override
    public RecyclerViewGroups.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        RecyclerViewGroups.ViewHolder resultViewHolder = new RecyclerViewGroups.ViewHolder(view, mListener);
        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((TextView) holder.mTextView.findViewById(R.id.tv_groups)).setText(groups.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.mTextView = itemView.findViewById(R.id.tv_groups);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public String getItem(int id){
        return groups.get(id);
    }


}
