package com.example.machinetest.ui.home.comment;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.machinetest.BR;
import com.example.machinetest.R;
import com.example.machinetest.base.BaseBottomSheetDialog;
import com.example.machinetest.data.remote.Comment;
import com.example.machinetest.databinding.BottomSheetPostCommentBinding;
import com.example.machinetest.ui.home.PostItemAdapter;
import com.example.machinetest.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PostCommentBottomSheet
        extends BaseBottomSheetDialog<BottomSheetPostCommentBinding, PostCommentViewModel>
        implements View.OnClickListener, PostCommentNavigator {

    private static final String ARG_POST_ID = "post_id";
    private BottomSheetPostCommentBinding mBinding;
    private PostCommentViewModel mViewModel;
    private int mPostId;

    public PostCommentBottomSheet() {
        // Required empty public constructor
    }

    public static PostCommentBottomSheet newInstance(int postId) {
        PostCommentBottomSheet fragment = new PostCommentBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POST_ID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        if (getArguments() != null)
            mPostId = getArguments().getInt(ARG_POST_ID, 0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        mViewModel.getPostComment(mPostId);
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
        return R.layout.bottom_sheet_post_comment;
    }

    @Override
    public PostCommentViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(PostCommentViewModel.class);
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
        PostCommentItemAdapter postCommentAdapter = new PostCommentItemAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvComment.setLayoutManager(layoutManager);
        mBinding.rvComment.setItemAnimator(new DefaultItemAnimator());
        mBinding.rvComment.setAdapter(postCommentAdapter);
    }

    private void subscribeToLiveData() {
        mViewModel.getPostCommentListLiveData().observe(this, post -> mViewModel.addPostCommentItemsToList(post));
    }
}
