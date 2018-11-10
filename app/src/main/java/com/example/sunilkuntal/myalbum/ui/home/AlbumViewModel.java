package com.example.sunilkuntal.myalbum.ui.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;
import com.example.sunilkuntal.myalbum.data.repository.AlbumRepository;
import com.example.sunilkuntal.myalbum.data.repository.AlbumRepositoryImpl;

import java.util.List;

public class AlbumViewModel extends AndroidViewModel {
    private static final String TAG = AlbumViewModel.class.getSimpleName();
    private AlbumRepository albumRepository;

    public LiveData<List<AlbumEntity>> getAlbumData() {
        return albumRepository.getAlbumsData();
    }

    public LiveData<String> getErrorUpdates() {
        return albumRepository.getErrorStream();
    }

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        albumRepository = AlbumRepositoryImpl.create(application);
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }

    public void fetchData() {
        albumRepository.fetchData();
    }

    @VisibleForTesting
    public AlbumViewModel(@NonNull Application application, AlbumRepositoryImpl repo) {
        super(application);
        this.albumRepository = repo;
    }
}
