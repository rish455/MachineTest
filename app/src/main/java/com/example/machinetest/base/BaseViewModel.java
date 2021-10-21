package com.example.machinetest.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.lang.ref.WeakReference;

/**
 * @author Rishad
 * @since 19/10/2021
 */

public abstract class BaseViewModel<N> extends AndroidViewModel {

    private WeakReference<N> mNavigator;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

}
