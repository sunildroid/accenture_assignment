package com.example.sunilkuntal.myalbum.ui.home;


import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;

import java.util.List;

public interface AlbumScreen {

    void updateData(List<AlbumEntity> data);
    void setError(String msg);
}
