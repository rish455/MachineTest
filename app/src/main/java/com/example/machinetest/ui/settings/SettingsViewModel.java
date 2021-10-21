package com.example.machinetest.ui.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetest.api.ApiClient;
import com.example.machinetest.api.ApiInterface;
import com.example.machinetest.base.BaseViewModel;
import com.example.machinetest.data.remote.Album;
import com.example.machinetest.ui.album.AlbumNavigator;
import com.example.machinetest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsViewModel extends BaseViewModel {

    private final ObservableField<String> appVersion = new ObservableField<>();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }
}
