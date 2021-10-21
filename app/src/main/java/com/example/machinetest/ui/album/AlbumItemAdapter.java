package com.example.machinetest.ui.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetest.base.BaseViewHolder;
import com.example.machinetest.data.remote.Album;
import com.example.machinetest.data.remote.Post;
import com.example.machinetest.databinding.ItemAlbumBinding;
import com.example.machinetest.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.List;

public class AlbumItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<Album> mDataSet;
    private AlbumItemAdapterIListener mListener;


    public AlbumItemAdapter(Context mContext, AlbumItemAdapterIListener mListener) {
        this.mContext = mContext;
        this.mDataSet = new ArrayList<>();
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAlbumBinding binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new AlbumItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItems(List<Album> dataSet) {
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mDataSet.clear();
    }

    public interface AlbumItemAdapterIListener{
        void onClickAlbum(int postId);
    }

    public class AlbumItemViewHolder extends BaseViewHolder implements View.OnClickListener {

        ItemAlbumBinding mBinding;

        public AlbumItemViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onBind(int position) {
            Album data = mDataSet.get(position);

            mBinding.setAlbumItem(data);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            mListener.onClickAlbum(mDataSet.get(getAdapterPosition()).getId());
        }
    }

}
