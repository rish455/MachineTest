package com.example.machinetest.ui.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import com.example.machinetest.BuildConfig;
import com.example.machinetest.R;
import com.example.machinetest.base.BaseActivity;
import com.example.machinetest.databinding.ActivityAlbumBinding;
import com.example.machinetest.databinding.ActivitySettingsBinding;
import com.example.machinetest.ui.album.AlbumActivity;
import com.example.machinetest.ui.album.AlbumViewModel;
import com.example.machinetest.utils.PreferenceUtils;

public class SettingsActivity
        extends BaseActivity<ActivitySettingsBinding, SettingsViewModel> {

    private SettingsViewModel mViewModel;
    private ActivitySettingsBinding mBinding;

    public static Intent start(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public SettingsViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        return mViewModel;
    }

    private void setUp(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Settings");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mViewModel.updateAppVersion(version);

        mBinding.swDarkTheme.setChecked(PreferenceUtils.isDarkMode());
        mBinding.swDarkTheme.setOnCheckedChangeListener((compoundButton, checked) -> {
            PreferenceUtils.setAsDarkMode(checked);
            setTheme();
        });
    }
}