package com.example.planningpoker2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VisibilitySettingsListAdapter extends RecyclerView.Adapter<VisibilitySettingsListAdapter.VisibilitySettingViewHolder> {
    public static final String TAG = "PlanningPokerSetAdap";
    ArrayList<String> taskList;
    ArrayList<String> visibilityList;

    public class VisibilitySettingViewHolder  extends RecyclerView.ViewHolder{
        public TextView textViewTask;
        public Switch switchIsEnabled;

        public VisibilitySettingViewHolder(@NonNull View view) {
            super(view);
            this.textViewTask = view.findViewById(R.id.textViewTask);
            this.switchIsEnabled = view.findViewById(R.id.switchIsEnabled);
        }
    }

    public VisibilitySettingsListAdapter(ArrayList<String> taskList, ArrayList<String> visibilityList) {
        this.taskList = taskList;
        this.visibilityList = visibilityList;
    }

    @NonNull
    @Override
    public VisibilitySettingsListAdapter.VisibilitySettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "Creating view holder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visibility_settings_list_element, parent, false);
        return new VisibilitySettingsListAdapter.VisibilitySettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisibilitySettingsListAdapter.VisibilitySettingViewHolder holder, int position) {
        Log.d(TAG, "Binding view holder");

        holder.textViewTask.setText(taskList.get(position));
        holder.switchIsEnabled.setChecked(visibilityList.get(position).equals("1"));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
