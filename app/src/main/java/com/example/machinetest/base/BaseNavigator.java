package com.example.machinetest.base;

import com.example.machinetest.utils.Constants;

public interface BaseNavigator {
    void handleProgress(boolean isShow);
    void handleError(Constants.ApiError err, String msg);
}
