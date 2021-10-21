package com.example.machinetest.ui.album.photos;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetest.BR;
import com.example.machinetest.R;
import com.example.machinetest.base.BaseBottomSheetDialog;
import com.example.machinetest.databinding.BottomSheetAlbumPhotosBinding;
import com.example.machinetest.databinding.BottomSheetPostCommentBinding;
import com.example.machinetest.ui.home.comment.PostCommentItemAdapter;
import com.example.machinetest.ui.home.comment.PostCommentNavigator;
import com.example.machinetest.ui.home.comment.PostCommentViewModel;
import com.example.machinetest.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AlbumPhotosBottomSheet
        extends BaseBottomSheetDialog<BottomSheetAlbumPhotosBinding, AlbumPhotosViewModel>
        implements View.OnClickListener, PostCommentNavigator {

    private static final String ARG_ALBUM_ID = "album_id";
    private BottomSheetAlbumPhotosBinding mBinding;
    private AlbumPhotosViewModel mViewModel;
    private int mAlbumId;

    public AlbumPhotosBottomSheet() {
        // Required empty public constructor
    }

    public static AlbumPhotosBottomSheet newInstance(int albumId) {
        AlbumPhotosBottomSheet fragment = new AlbumPhotosBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ALBUM_ID, albumId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        if (getArguments() != null)
            mAlbumId = getArguments().getInt(ARG_ALBUM_ID, 0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        mViewModel.getAlbumPhotosComment(mAlbumId);
        setUp();
        subscribeToLiveData();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Making bottom sheet expanding to full height by default
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog1;

            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        return dialog;
    }

    @Override
    public int getTheme() {
        return R.style.BaseBottomSheetDialog;
    }



    @Override
    public int getLayoutId() {
        return R.layout.bottom_sheet_album_photos;
    }

    @Override
    public AlbumPhotosViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(AlbumPhotosViewModel.class);
        return mViewModel;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_close) {
            dismiss();
        }
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
                Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                break;
            case SERVER_ERROR:
                Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
                break;
            case NETWORK_ISSUE:
                Toast.makeText(getActivity(), R.string.please_check_your_internet, Toast.LENGTH_SHORT).show();
                break;
        }
        dismiss();
    }

    @Override
    public void handleSuccess() {

    }

    private void initViews() {
        mBinding = getViewDataBinding();
        mBinding.ibClose.setOnClickListener(this);
    }


    private void setUp() {
        AlbumPhotosItemAdapter albumPhotosItemAdapter = new AlbumPhotosItemAdapter(getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        mBinding.rvAlbumPhotos.setLayoutManager(layoutManager);
        mBinding.rvAlbumPhotos.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvAlbumPhotos.setAdapter(albumPhotosItemAdapter);
    }

    private void subscribeToLiveData() {
        mViewModel.getAlbumPhotosListLiveData().observe(this, data -> mViewModel.addAlbumPhotosItemsToList(data));
    }
}
