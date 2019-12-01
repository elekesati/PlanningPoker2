package com.example.planningpoker2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VisibilitySettingsListAdapter
        extends RecyclerView.Adapter<VisibilitySettingsListAdapter.VisibilitySettingViewHolder> {
    private static final String TAG = "PlanningPokerSetAdap";
    private ArrayList<String> taskList;
    private ArrayList<String> visibilityList;
    private TaskVisibilityHandlerFragment mTaskVisibilityHandlerFragment;

    public class VisibilitySettingViewHolder  extends RecyclerView.ViewHolder{
        public TextView textViewTask;
        public Switch switchIsEnabled;

        public VisibilitySettingViewHolder(@NonNull View view) {
            super(view);
            this.textViewTask = view.findViewById(R.id.textViewTask);
            this.switchIsEnabled = view.findViewById(R.id.switchIsEnabled);
        }
    }

    public VisibilitySettingsListAdapter(ArrayList<String> taskList,
                                         ArrayList<String> visibilityList,
                                         TaskVisibilityHandlerFragment taskVisibilityHandlerFragment) {
        this.taskList = taskList;
        this.visibilityList = visibilityList;
        this.mTaskVisibilityHandlerFragment = taskVisibilityHandlerFragment;
    }

    @NonNull
    @Override
    public VisibilitySettingsListAdapter.VisibilitySettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "Creating view holder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visibility_settings_list_element, parent, false);

        return new VisibilitySettingsListAdapter.VisibilitySettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisibilitySettingsListAdapter.VisibilitySettingViewHolder holder, final int position) {
        Log.d(TAG, "Binding view holder");

        holder.textViewTask.setText(taskList.get(position));
        holder.switchIsEnabled.setChecked(visibilityList.get(position).equals("1"));

        holder.switchIsEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mTaskVisibilityHandlerFragment.onSwitchSateChanged(taskList.get(position), b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
