package com.example.planningpoker2;

public class Task {
    public String mName, mStatus;

    public Task(){}
    public Task(String name){
        this.mName = name;
        this.mStatus = "1";
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }
}
