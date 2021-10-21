package com.example.machinetest.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Rishad
 * @since 19/10/2021
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);
}