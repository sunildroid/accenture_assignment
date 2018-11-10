package com.example.sunilkuntal.myalbum.data.repository;

import android.arch.lifecycle.LiveData;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;

import java.util.List;



public interface AlbumRepository {

    LiveData<List<AlbumEntity>> getAlbumsData();
    LiveData<String> getErrorStream();
    void fetchData();
    }
