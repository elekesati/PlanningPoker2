package com.example.planningpoker2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder>  {
    public static final String TAG = "PlanningPokerResAdap";
    List<Object> tasks;
    List<Object> results;

    public class ResultViewHolder  extends RecyclerView.ViewHolder{
        public CardView cardView;


        public ResultViewHolder(@NonNull View view) {
            super(view);
            this.cardView = view.findViewById(R.id.cardView);
        }
    }

    public ResultListAdapter(Map<String, Double> results) {
        this.tasks = Arrays.asList(results.keySet().toArray());
        this.results = Arrays.asList(results.values().toArray());
    }

    @NonNull
    @Override
    public ResultListAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "Creating view holder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_item, parent, false);
        ResultListAdapter.ResultViewHolder resultViewHolder = new ResultListAdapter.ResultViewHolder(view);
        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultListAdapter.ResultViewHolder holder, int position) {
        Log.d(TAG, "Binding view holder");

        ((TextView) holder.cardView.findViewById(R.id.textViewTaskResult)).
                setText(tasks.get(position).toString());
        ((TextView) holder.cardView.findViewById(R.id.textViewResult)).
                setText(results.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}