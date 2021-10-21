package com.example.machinetest.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.machinetest.BuildConfig;
import com.example.machinetest.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

/**
 * @author Rishad
 * @since 19/10/2021
 */
public final class CommonUtils {

    public static void loadImage(Context context, String url, ImageView ivImage) {
        RequestOptions options = new RequestOptions()
                .placeholder(getShimmerPlaceholder())
                .error(R.drawable.img_album_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(ivImage);
    }

    private static Drawable getShimmerPlaceholder() {
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.5f) //the alpha of the underlying children
                .setHighlightAlpha(0.4f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        return shimmerDrawable;
    }

}
