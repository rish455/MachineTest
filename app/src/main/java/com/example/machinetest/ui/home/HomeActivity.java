package com.example.machinetest.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.machinetest.BR;
import com.example.machinetest.R;
import com.example.machinetest.base.BaseActivity;
import com.example.machinetest.data.remote.Post;
import com.example.machinetest.databinding.ActivityHomeBinding;
import com.example.machinetest.ui.album.AlbumActivity;
import com.example.machinetest.ui.home.comment.PostCommentBottomSheet;
import com.example.machinetest.ui.settings.SettingsActivity;
import com.example.machinetest.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeActivity
        extends BaseActivity<ActivityHomeBinding, HomeViewModel>
        implements NavigationView.OnNavigationItemSelectedListener, HomeNavigator, PostItemAdapter.PostItemAdapterIListener {

    public ActionBarDrawerToggle actionBarDrawerToggle;

    private HomeViewModel mViewModel;
    private ActivityHomeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewDataBinding();
        mViewModel.setNavigator(this);

        mViewModel.getPosts();
        setUpNavDrawer();
        setUp();
        subscribeToLiveData();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        return mViewModel;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_album:
                startActivity(AlbumActivity.start(this));
                break;
            case R.id.nav_settings:
                startActivity(SettingsActivity.start(this));
                break;
        }
        mBinding.drawerLayout.closeDrawers();
        return false;
    }

    @Override
    public void onClickComment(int postId) {
        showPostCommentBottomSheetDialog(postId);
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
                Snackbar.make(mBinding.drawerLayout, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show();
                break;
            case SERVER_ERROR:
                Snackbar.make(mBinding.drawerLayout, R.string.server_error, Snackbar.LENGTH_SHORT).show();
                break;
            case NETWORK_ISSUE:
                Snackbar.make(mBinding.drawerLayout, R.string.please_check_your_internet, Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void handleSuccess(List<Post> dataSet) {
        //mPostAdapter.setPostItems(dataSet);
    }


    private void setUpNavDrawer(){
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, R.string.nav_open, R.string.nav_close);

        mBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBinding.navigationView.setNavigationItemSelectedListener(this);
    }

    private void setUp() {
        PostItemAdapter mPostAdapter = new PostItemAdapter(this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvHome.setLayoutManager(layoutManager);
        mBinding.rvHome.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvHome.setAdapter(mPostAdapter);
    }

    private void subscribeToLiveData() {
        mViewModel.getPostListLiveData().observe(this, post -> mViewModel.addPostItemsToList(post));
    }

    private void showPostCommentBottomSheetDialog(int postId) {
        PostCommentBottomSheet bottomSheetFragment = PostCommentBottomSheet.newInstance(postId);
        bottomSheetFragment.show(getSupportFragmentManager(), "");
    }
}