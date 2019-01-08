package com.example.sunilkuntal.myalbum.data.repository.datasource;

import android.arch.lifecycle.LiveData;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;


public interface DataSource<T> {

    LiveData<T> getDataStream();
    LiveData<AlbumEntity> getDetail(int id);
    LiveData<String> getErrorStream();
}
