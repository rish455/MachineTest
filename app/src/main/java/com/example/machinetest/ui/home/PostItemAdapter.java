package com.example.machinetest.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetest.base.BaseViewHolder;
import com.example.machinetest.data.remote.Post;
import com.example.machinetest.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.List;

public class PostItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<Post> mDataSet;
    private PostItemAdapterIListener mListener;


    public PostItemAdapter(Context mContext, PostItemAdapterIListener mListener) {
        this.mContext = mContext;
        this.mDataSet = new ArrayList<>();
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new PostItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItems(List<Post> dataSet) {
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mDataSet.clear();
    }

    public interface PostItemAdapterIListener{
        void onClickComment(int postId);
    }

    public class PostItemViewHolder extends BaseViewHolder implements View.OnClickListener {

        ItemPostBinding mBinding;

        public PostItemViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            Post data = mDataSet.get(position);

            mBinding.setPostItem(data);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            mListener.onClickComment(mDataSet.get(getAdapterPosition()).getId());
        }
    }

}
