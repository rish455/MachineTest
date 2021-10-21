package com.example.machinetest.ui.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetest.BR;
import com.example.machinetest.R;
import com.example.machinetest.base.BaseActivity;
import com.example.machinetest.databinding.ActivityAlbumBinding;
import com.example.machinetest.ui.album.photos.AlbumPhotosBottomSheet;
import com.example.machinetest.ui.home.comment.PostCommentBottomSheet;
import com.example.machinetest.utils.Constants;
import com.google.android.material.snackbar.Snackbar;

public class AlbumActivity
        extends BaseActivity<ActivityAlbumBinding, AlbumViewModel>
        implements AlbumNavigator, AlbumItemAdapter.AlbumItemAdapterIListener {

    private AlbumViewModel mViewModel;
    private ActivityAlbumBinding mBinding;

    public static Intent start(Context context) {
        return new Intent(context, AlbumActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewDataBinding();
        mViewModel.setNavigator(this);

        mViewModel.getAlbums();
        setUpToolbar();
        setUp();
        subscribeToLiveData();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_album;
    }

    @Override
    public AlbumViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        return mViewModel;
    }

    @Override
    public void onClickAlbum(int albumId) {
        showAlbumPhotosBottomSheetDialog(albumId);
    }

    @Override
    public void handleProgress(boolean isShow) {
        if (isShow)
            showProgressDialog();
        else dismissProgressDialog();
    }

    @Override
    public void handleError(Constants.ApiError err, String msg) {
        switch (err) {
            case RESPONSE_FAILED:
                Snackbar.make(mBinding.parentLayout, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show();
                break;
            case SERVER_ERROR:
                Snackbar.make(mBinding.parentLayout, R.string.server_error, Snackbar.LENGTH_SHORT).show();
                break;
            case NETWORK_ISSUE:
                Snackbar.make(mBinding.parentLayout, R.string.please_check_your_internet, Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void handleSuccess() {

    }

    private void setUpToolbar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Album");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void setUp() {
        AlbumItemAdapter albumItemAdapter = new AlbumItemAdapter(this, this);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        mBinding.rvAlbum.setLayoutManager(layoutManager);
        mBinding.rvAlbum.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvAlbum.setAdapter(albumItemAdapter);
    }

    private void subscribeToLiveData() {
        mViewModel.getAlbumListLiveData().observe(this, data -> mViewModel.addAlbumItemsToList(data));
    }

    private void showAlbumPhotosBottomSheetDialog(int albumId) {
        AlbumPhotosBottomSheet bottomSheetFragment = AlbumPhotosBottomSheet.newInstance(albumId);
        bottomSheetFragment.show(getSupportFragmentManager(), "");
    }
}