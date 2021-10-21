package com.example.machinetest.ui.home.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetest.base.BaseViewHolder;
import com.example.machinetest.data.remote.Comment;
import com.example.machinetest.data.remote.Post;
import com.example.machinetest.databinding.ItemPostBinding;
import com.example.machinetest.databinding.ItemPostCommentBinding;

import java.util.ArrayList;
import java.util.List;

public class PostCommentItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<Comment> mDataSet;

    public PostCommentItemAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostCommentBinding binding = ItemPostCommentBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new PostCommentItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public void addItems(List<Comment> dataSet) {
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mDataSet.clear();
    }

    public class PostCommentItemViewHolder extends BaseViewHolder {

        ItemPostCommentBinding mBinding;

        public PostCommentItemViewHolder(ItemPostCommentBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            Comment data = mDataSet.get(position);

            mBinding.setCommentItem(data);
            mBinding.executePendingBindings();
        }

    }

}
