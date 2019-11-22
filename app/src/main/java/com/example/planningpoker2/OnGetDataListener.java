package com.example.planningpoker2;

import java.util.List;
import java.util.Map;

public interface OnGetDataListener {
    void onSuccess(List<String> dataList);
    void onSuccess(Map<String, Double> dataMap);
}