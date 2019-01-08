package com.example.sunilkuntal.myalbum.ui.feature.details;


import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;

public interface AlbumDetailScreen {

    void updateData(AlbumEntity data);
    void setError(String msg);
}
