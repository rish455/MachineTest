package com.example.machinetest.ui.home;


import com.example.machinetest.base.BaseNavigator;
import com.example.machinetest.data.remote.Post;

import java.util.List;

public interface HomeNavigator extends BaseNavigator {
    void handleSuccess(List<Post> resp);
}
