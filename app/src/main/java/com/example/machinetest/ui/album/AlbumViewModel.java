package com.example.machinetest.ui.album;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetest.api.ApiClient;
import com.example.machinetest.api.ApiInterface;
import com.example.machinetest.base.BaseViewModel;
import com.example.machinetest.data.remote.Album;
import com.example.machinetest.data.remote.Post;
import com.example.machinetest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumViewModel extends BaseViewModel<AlbumNavigator> {

    private final ApiInterface mApiService;
    public final ObservableList<Album> albumObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Album>> albumListLiveData;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        mApiService = ApiClient.getClient().create(ApiInterface.class);
        albumListLiveData = new MutableLiveData<>();
    }

    void getAlbums(){
        getNavigator().handleProgress(true);
        Call<List<Album>> call = mApiService.getAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(@NonNull Call<List<Album>> call, @NonNull Response<List<Album>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Album> resp = response.body();
                    //getNavigator().handleSuccess(resp);
                    albumListLiveData.setValue(resp);
                } else {
                    // response error
                    getNavigator().handleError(Constants.ApiError.SERVER_ERROR, "");
                }

                getNavigator().handleProgress(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Album>> call, @NonNull Throwable t) {
                getNavigator().handleError(Constants.ApiError.NETWORK_ISSUE, "");
                getNavigator().handleProgress(false);
            }
        });
    }

    public void addAlbumItemsToList(List<Album> dataSet) {
        albumObservableArrayList.clear();
        albumObservableArrayList.addAll(dataSet);
    }
    public MutableLiveData<List<Album>> getAlbumListLiveData() {
        return albumListLiveData;
    }
}
