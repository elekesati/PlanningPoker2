package com.example.planningpoker2;

import java.util.ArrayList;
import java.util.List;

public interface OnGetDataListener {
    void onSuccess(List<String> dataList);
    void onSuccess(ArrayList<String> taskList);
}