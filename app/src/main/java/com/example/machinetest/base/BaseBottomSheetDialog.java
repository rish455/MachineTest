package com.example.machinetest.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.example.machinetest.ui.dialog.ProgressDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * @author Rishad
 * @since 20/10/2021
 */

public abstract class BaseBottomSheetDialog <T extends ViewDataBinding, V extends BaseViewModel>  extends BottomSheetDialogFragment {

    private T mViewDataBinding;
    private V mViewModel;
    private ProgressDialogFragment mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialogFragment.newInstance();
            mProgressDialog.show(getParentFragmentManager(), "progress_dialog");
        }
    }

    public void dismissProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public abstract int getBindingVariable();

    public T getViewDataBinding() {
        return mViewDataBinding;
    }


    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();
}
