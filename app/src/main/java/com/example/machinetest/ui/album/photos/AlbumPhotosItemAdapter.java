package com.example.machinetest.ui.album.photos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetest.base.BaseViewHolder;
import com.example.machinetest.data.remote.AlbumPhotos;
import com.example.machinetest.databinding.ItemAlbumPhotosBinding;

import java.util.ArrayList;
import java.util.List;

public class AlbumPhotosItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<AlbumPhotos> mDataSet;


    public AlbumPhotosItemAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAlbumPhotosBinding binding = ItemAlbumPhotosBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new AlbumPhotosItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItems(List<AlbumPhotos> dataSet) {
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mDataSet.clear();
    }

    public class AlbumPhotosItemViewHolder extends BaseViewHolder {

        ItemAlbumPhotosBinding mBinding;

        public AlbumPhotosItemViewHolder(ItemAlbumPhotosBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            AlbumPhotos data = mDataSet.get(position);

            mBinding.setAlbumPhotosItem(data);
            mBinding.executePendingBindings();
        }

    }

}
