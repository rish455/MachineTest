package com.example.machinetest;

import android.app.Application;

import com.example.machinetest.utils.PreferenceUtils;


/**
 * @author Rishad
 * @since 21/10/2021
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.newInstance(getApplicationContext());
    }
}
