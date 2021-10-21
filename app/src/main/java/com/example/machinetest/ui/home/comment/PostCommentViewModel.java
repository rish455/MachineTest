package com.example.machinetest.ui.home.comment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetest.api.ApiClient;
import com.example.machinetest.api.ApiInterface;
import com.example.machinetest.base.BaseViewModel;
import com.example.machinetest.data.remote.Comment;
import com.example.machinetest.data.remote.Post;
import com.example.machinetest.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostCommentViewModel extends BaseViewModel<PostCommentNavigator> {

    private final ApiInterface mApiService;
    public final ObservableList<Comment> postCommentObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Comment>> postCommentListLiveData;

    public PostCommentViewModel(@NonNull Application application) {
        super(application);
        mApiService = ApiClient.getClient().create(ApiInterface.class);
        postCommentListLiveData = new MutableLiveData<>();
    }

    void getPostComment(int postId){
        getNavigator().handleProgress(true);
        Call<List<Comment>> call = mApiService.getPostComment(postId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call, @NonNull Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comment> resp = response.body();
                    postCommentListLiveData.setValue(resp);
                } else {
                    // response error
                    getNavigator().handleError(Constants.ApiError.SERVER_ERROR, "");
                }

                getNavigator().handleProgress(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {
                getNavigator().handleError(Constants.ApiError.NETWORK_ISSUE, "");
                getNavigator().handleProgress(false);
            }
        });
    }

    public void addPostCommentItemsToList(List<Comment> dataSet) {
        postCommentObservableArrayList.clear();
        postCommentObservableArrayList.addAll(dataSet);
    }

    public MutableLiveData<List<Comment>> getPostCommentListLiveData() {
        return postCommentListLiveData;
    }
}
