package com.example.sunilkuntal.myalbum.ui.feature.details;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class AlbumDetailViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private int mID;


    public AlbumDetailViewModelFactory(Application application, int id) {
        mApplication = application;
        mID = id;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AlbumDetailViewModel(mApplication, mID);
    }
}