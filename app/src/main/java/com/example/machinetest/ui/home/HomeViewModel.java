package com.example.machinetest.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetest.api.ApiClient;
import com.example.machinetest.api.ApiInterface;
import com.example.machinetest.base.BaseViewModel;
import com.example.machinetest.data.remote.Post;
import com.example.machinetest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private final ApiInterface mApiService;
    public final ObservableList<Post> postObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Post>> postListLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mApiService = ApiClient.getClient().create(ApiInterface.class);
        postListLiveData = new MutableLiveData<>();
    }

    void getPosts(){
        getNavigator().handleProgress(true);
        Call<List<Post>> call = mApiService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> resp = response.body();
                    //getNavigator().handleSuccess(resp);
                    postListLiveData.setValue(resp);
                } else {
                    // response error
                    getNavigator().handleError(Constants.ApiError.SERVER_ERROR, "");
                }

                getNavigator().handleProgress(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                getNavigator().handleError(Constants.ApiError.NETWORK_ISSUE, "");
                getNavigator().handleProgress(false);
            }
        });
    }

    public void addPostItemsToList(List<Post> dataSet) {
        postObservableArrayList.clear();
        postObservableArrayList.addAll(dataSet);
    }
    public MutableLiveData<List<Post>> getPostListLiveData() {
        return postListLiveData;
    }
}
