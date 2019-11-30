package com.example.planningpoker2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VisibilitySettingsListAdapter extends RecyclerView.Adapter<VisibilitySettingsListAdapter.VisibilitySettingViewHolder> {
    public static final String TAG = "PlanningPokerSetAdap";
    List<Object> tasks;
    List<Object> isEnabled;

    public class VisibilitySettingViewHolder  extends RecyclerView.ViewHolder{
        public TextView textViewTask;
        public Switch switchIsEnabled;

        public VisibilitySettingViewHolder(@NonNull View view) {
            super(view);
            this.textViewTask = view.findViewById(R.id.textViewTask);
            this.switchIsEnabled = view.findViewById(R.id.switchIsEnabled);
        }
    }

    public VisibilitySettingsListAdapter(Map<String, Double> results) {
        this.tasks = Arrays.asList(results.keySet().toArray());
        this.isEnabled = Arrays.asList(results.values().toArray());
    }

    @NonNull
    @Override
    public VisibilitySettingsListAdapter.VisibilitySettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "Creating view holder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_item, parent, false);
        VisibilitySettingsListAdapter.VisibilitySettingViewHolder visibilitySettingViewHolder =
                new VisibilitySettingsListAdapter.VisibilitySettingViewHolder(view);
        return visibilitySettingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VisibilitySettingsListAdapter.VisibilitySettingViewHolder holder, int position) {
        Log.d(TAG, "Binding view holder");

        holder.textViewTask.setText(tasks.get(position).toString());
        holder.switchIsEnabled.setTextOff("Invisible");
        holder.switchIsEnabled.setTextOn("Visible");
        holder.switchIsEnabled.setChecked(isEnabled.get(position).equals(1d));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
