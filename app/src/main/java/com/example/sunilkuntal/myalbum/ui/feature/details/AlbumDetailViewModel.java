package com.example.sunilkuntal.myalbum.ui.feature.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;
import com.example.sunilkuntal.myalbum.data.repository.AlbumRepository;
import com.example.sunilkuntal.myalbum.data.repository.AlbumRepositoryImpl;

public class AlbumDetailViewModel extends AndroidViewModel {

    private static final String TAG = AlbumDetailViewModel.class.getSimpleName();

    private AlbumRepository albumRepository;
    private Application application;
    private int id;

    public LiveData<AlbumEntity> getAlbumData() {
        return albumRepository.getAlbumsDetails();
    }

    public LiveData<String> getErrorUpdates() {
        return albumRepository.getErrorStream();
    }

    public AlbumDetailViewModel(@NonNull Application application,int id) {
        super(application);
        this.id=id;
            albumRepository = AlbumRepositoryImpl.create(application,id);
    }


    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }


    public void fetchData(int id) {
        albumRepository.fetchDetail(id);
    }

    @VisibleForTesting
    public AlbumDetailViewModel(@NonNull Application application, AlbumRepositoryImpl repo) {
        super(application);
        if(repo==null)
            albumRepository = AlbumRepositoryImpl.create(application);
        this.albumRepository = repo;
    }
}
