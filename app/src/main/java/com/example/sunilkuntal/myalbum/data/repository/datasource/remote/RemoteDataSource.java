package com.example.sunilkuntal.myalbum.data.repository.datasource.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;
import com.example.sunilkuntal.myalbum.data.repository.datasource.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoteDataSource implements DataSource<List<AlbumEntity>> {
    private static final String TAG = RemoteDataSource.class.getSimpleName();
    private final MutableLiveData<String> mError=new MutableLiveData<>();
    private final MutableLiveData<List<AlbumEntity>> mDataApi=new MutableLiveData<>();
    private final MutableLiveData<AlbumEntity> mAlbumApi=new MutableLiveData<>();
    private  AlbumService service;
    ArrayList mList = new ArrayList();
    public RemoteDataSource(Context appContext) {
      service= ServiceGenerator.createService(appContext,AlbumService.class);
    }


    @Override
    public LiveData<List<AlbumEntity>> getDataStream() {
        return mDataApi;
    }

    @Override
    public LiveData<AlbumEntity> getDetail(int id) {
      return mAlbumApi;
    }


    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    public LiveData<List<AlbumEntity>> fetchAlbumList() {
            service.fetchAlbums().enqueue(new Callback<List<AlbumEntity>>() {
                @Override
                public void onResponse(Call<List<AlbumEntity>> call, Response<List<AlbumEntity>> response) {
                    if (response.isSuccessful())
                    {
                        mDataApi.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<AlbumEntity>> call, Throwable t) {
                    mError.setValue(t.getMessage().toString());
                }
            });
            return mDataApi;
        }

    public LiveData<AlbumEntity> fetchAlbumDetail(int id) {
        service.fetchAlbumDetail(id).enqueue(new Callback<AlbumEntity>() {
            @Override
            public void onResponse(Call<AlbumEntity> call, Response<AlbumEntity> response) {
                if (response.isSuccessful())
                {
                    mAlbumApi.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AlbumEntity> call, Throwable t) {
                mError.setValue(t.getMessage().toString());
            }
        });
        return mAlbumApi;
    }

}
