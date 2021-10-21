package com.example.machinetest.api;


import com.example.machinetest.data.remote.Album;
import com.example.machinetest.data.remote.AlbumPhotos;
import com.example.machinetest.data.remote.Comment;
import com.example.machinetest.data.remote.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Rishad
 * @since 19/10/2021
 */

public interface ApiInterface {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Call<List<Comment>> getPostComment(@Path("id") int id);

    @GET("albums")
    Call<List<Album>> getAlbums();

    @GET("albums/{id}/photos")
    Call<List<AlbumPhotos>> getAlbumPhotos(@Path("id") int id);;
}