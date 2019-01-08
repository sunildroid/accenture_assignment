package com.example.sunilkuntal.myalbum.data.repository.datasource.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.example.sunilkuntal.myalbum.data.db.RoomDb;
import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;
import com.example.sunilkuntal.myalbum.data.repository.datasource.DataSource;

import java.util.List;


public class LocalDataSource implements DataSource<List<AlbumEntity>> {
    private final RoomDb mDb;
    private final MutableLiveData<String> mError=new MutableLiveData<>();
    public LocalDataSource(Context mAppContext) {
        mDb= RoomDb.getDatabase(mAppContext);
    }
    @Override
    public LiveData<List<AlbumEntity>> getDataStream() {
        return mDb.albumDao().getAllAlbumsLive();
    }

    @Override
    public LiveData<AlbumEntity> getDetail(int id) {
        return mDb.albumDao().getAlbum(id);
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    public void writeData(List<AlbumEntity> albums) {
        try {
            mDb.albumDao().insertAlbums(albums);
        }catch(Exception e)
        {
            e.printStackTrace();
            mError.postValue(e.getMessage());
        }
    }


    public void writeDetailData(AlbumEntity album) {
        try {
            mDb.albumDao().insertAlbum(album);
        }catch(Exception e)
        {
            e.printStackTrace();
            mError.postValue(e.getMessage());
        }
    }

    public List<AlbumEntity> getALlAlbums() {
        return mDb.albumDao().getAllAlbums();
    }

    @VisibleForTesting
    public void deleteAllAlbums()
    {
        mDb.albumDao().deleteAllAlbums();
    }
}
