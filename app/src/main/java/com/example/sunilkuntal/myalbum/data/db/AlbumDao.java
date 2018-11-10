package com.example.sunilkuntal.myalbum.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.VisibleForTesting;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;

import java.util.List;

@Dao
public interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<AlbumEntity> albums);

    @Query("SELECT * FROM albums")
    LiveData<List<AlbumEntity>> getAllAlbumsLive();

    @Query("SELECT * FROM albums")
    List<AlbumEntity> getAllAlbums();

    @Query("SELECT * FROM albums LIMIT :limit")
    LiveData<List<AlbumEntity>> getAlbums(int limit);

    @Query("SELECT * FROM albums WHERE id=:id")
    LiveData<AlbumEntity> getAlbum(String id);

    @VisibleForTesting
    @Query("DELETE FROM albums")
    void deleteAllAlbums();


}

