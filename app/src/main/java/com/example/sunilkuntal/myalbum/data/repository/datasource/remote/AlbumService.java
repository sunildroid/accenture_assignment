package com.example.sunilkuntal.myalbum.data.repository.datasource.remote;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumService {

    @GET("/albums")
    Call<List<AlbumEntity>> fetchAlbums();

}