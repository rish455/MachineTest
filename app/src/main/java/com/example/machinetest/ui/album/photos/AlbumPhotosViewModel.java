package com.example.machinetest.ui.album.photos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetest.api.ApiClient;
import com.example.machinetest.api.ApiInterface;
import com.example.machinetest.base.BaseViewModel;
import com.example.machinetest.data.remote.AlbumPhotos;
import com.example.machinetest.ui.home.comment.PostCommentNavigator;
import com.example.machinetest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumPhotosViewModel extends BaseViewModel<PostCommentNavigator> {

    public final ObservableList<AlbumPhotos> albumPhotosObservableArrayList = new ObservableArrayList<>();
    private final ApiInterface mApiService;
    private final MutableLiveData<List<AlbumPhotos>> albumPhotosListLiveData;

    public AlbumPhotosViewModel(@NonNull Application application) {
        super(application);
        mApiService = ApiClient.getClient().create(ApiInterface.class);
        albumPhotosListLiveData = new MutableLiveData<>();
    }

    void getAlbumPhotosComment(int albumId) {
        getNavigator().handleProgress(true);
        Call<List<AlbumPhotos>> call = mApiService.getAlbumPhotos(albumId);
        call.enqueue(new Callback<List<AlbumPhotos>>() {
            @Override
            public void onResponse(@NonNull Call<List<AlbumPhotos>> call, @NonNull Response<List<AlbumPhotos>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AlbumPhotos> resp = response.body();
                    albumPhotosListLiveData.setValue(resp);
                } else {
                    // response error
                    getNavigator().handleError(Constants.ApiError.SERVER_ERROR, "");
                }

                getNavigator().handleProgress(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<AlbumPhotos>> call, @NonNull Throwable t) {
                getNavigator().handleError(Constants.ApiError.NETWORK_ISSUE, "");
                getNavigator().handleProgress(false);
            }
        });
    }

    public void addAlbumPhotosItemsToList(List<AlbumPhotos> dataSet) {
        albumPhotosObservableArrayList.clear();
        albumPhotosObservableArrayList.addAll(dataSet);
    }

    public MutableLiveData<List<AlbumPhotos>> getAlbumPhotosListLiveData() {
        return albumPhotosListLiveData;
    }
}
