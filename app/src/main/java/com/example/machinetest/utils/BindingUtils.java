package com.example.machinetest.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetest.data.remote.Album;
import com.example.machinetest.data.remote.AlbumPhotos;
import com.example.machinetest.data.remote.Comment;
import com.example.machinetest.data.remote.Post;
import com.example.machinetest.ui.album.AlbumItemAdapter;
import com.example.machinetest.ui.album.photos.AlbumPhotosItemAdapter;
import com.example.machinetest.ui.home.PostItemAdapter;
import com.example.machinetest.ui.home.comment.PostCommentItemAdapter;

import java.util.List;

/**
 * @author Rishad
 * @since 19/10/2021
 */
public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }


    @BindingAdapter({"adapter"})
    public static void addPostItems(RecyclerView recyclerView, List<Post> dataSet) {
        PostItemAdapter adapter = (PostItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dataSet);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addPostCommentItems(RecyclerView recyclerView, List<Comment> dataSet) {
        PostCommentItemAdapter adapter = (PostCommentItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dataSet);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addAlbumItems(RecyclerView recyclerView, List<Album> dataSet) {
        AlbumItemAdapter adapter = (AlbumItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dataSet);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addAlbumPhotosItems(RecyclerView recyclerView, List<AlbumPhotos> dataSet) {
        AlbumPhotosItemAdapter adapter = (AlbumPhotosItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dataSet);
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        CommonUtils.loadImage(context, url, imageView);
    }

}
