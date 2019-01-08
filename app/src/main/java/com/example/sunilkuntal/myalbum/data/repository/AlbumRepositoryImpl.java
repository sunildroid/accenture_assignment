package com.example.sunilkuntal.myalbum.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;
import com.example.sunilkuntal.myalbum.data.repository.datasource.local.LocalDataSource;
import com.example.sunilkuntal.myalbum.data.repository.datasource.remote.RemoteDataSource;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AlbumRepositoryImpl implements AlbumRepository {

    private static final String TAG = AlbumRepositoryImpl.class.getSimpleName();
    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);
    private final RemoteDataSource mRemoteDataSource;
    private final LocalDataSource mLocalDataSource;
     static int selectedID;
    MediatorLiveData<List<AlbumEntity>> mDataMerger = new MediatorLiveData<>();

    MediatorLiveData<AlbumEntity> mDatamergerDetails = new MediatorLiveData<AlbumEntity>();

    MediatorLiveData<String> mErrorMerger = new MediatorLiveData<>();

    private AlbumRepositoryImpl(RemoteDataSource mRemoteDataSource,final LocalDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;

        mDatamergerDetails.addSource(this.mRemoteDataSource.getDetail(selectedID), new Observer<AlbumEntity>() {
            @Override
            public void onChanged(@Nullable final AlbumEntity albumDetail) {
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmRemoteDataSource onChange invoked");
                        mLocalDataSource.writeDetailData(albumDetail);
                        mDatamergerDetails.postValue(albumDetail);


                    }
                });
            }
        });

        mDatamergerDetails.addSource(this.mLocalDataSource.getDetail(selectedID), new Observer<AlbumEntity>() {
            @Override
            public void onChanged(@Nullable final AlbumEntity albumEntities) {
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmLocalDataSource onChange invoked");
                        mDatamergerDetails.postValue(albumEntities);
                    }
                });
            }
        });


        mDataMerger.addSource(this.mRemoteDataSource.getDataStream(), new Observer<List<AlbumEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AlbumEntity> albumEntities) {
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmRemoteDataSource onChange invoked");
                        mLocalDataSource.writeData(albumEntities);
                        mDataMerger.postValue(albumEntities);


                    }
                });
            }
        });

        mDataMerger.addSource(this.mLocalDataSource.getDataStream(), new Observer<List<AlbumEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AlbumEntity> albumEntities) {
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmLocalDataSource onChange invoked");
                        // List<AlbumEntity> models = mMapper.mapEntityToModel(entities);
                        mDataMerger.postValue(albumEntities);
                    }
                });
            }
        });

        mErrorMerger.addSource(mRemoteDataSource.getErrorStream(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "Network error -> fetching from LocalDataSource");
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<AlbumEntity> entities = (mLocalDataSource.getALlAlbums());
                        mDataMerger.postValue(entities);
                    }
                });
            }
        });

      mErrorMerger.addSource(mLocalDataSource.getErrorStream(), new Observer<String>() {
          @Override
          public void onChanged(@Nullable String s) {
              mErrorMerger.setValue(s);
          }
      });
    }

    public static AlbumRepository create(Context mAppContext) {
        final RemoteDataSource remoteDataSource = new RemoteDataSource(mAppContext);
        final LocalDataSource localDataSource = new LocalDataSource(mAppContext);
        return new AlbumRepositoryImpl(remoteDataSource, localDataSource);
    }

    public static AlbumRepository create(Context mAppContext,int id) {
        AlbumRepositoryImpl.selectedID=id;
        final RemoteDataSource remoteDataSource = new RemoteDataSource(mAppContext);
        final LocalDataSource localDataSource = new LocalDataSource(mAppContext);
        return new AlbumRepositoryImpl(remoteDataSource, localDataSource);
    }




    @VisibleForTesting
    public static AlbumRepositoryImpl createImpl(Context mAppContext) {
        final RemoteDataSource remoteDataSource = new RemoteDataSource(mAppContext);
        final LocalDataSource localDataSource = new LocalDataSource(mAppContext);
        return new AlbumRepositoryImpl(remoteDataSource, localDataSource);
    }

    @Override
    public void fetchData() {
        mRemoteDataSource.fetchAlbumList();
    }

    @Override
    public LiveData<AlbumEntity> getAlbumsDetails() {
        return  mDatamergerDetails;
    }

    @Override
    public void fetchDetail(int id) {
        mRemoteDataSource.fetchAlbumDetail(id);
    }

    @Override
    public LiveData<List<AlbumEntity>> getAlbumsData() {
        return mDataMerger;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mErrorMerger;
    }



    @VisibleForTesting
    public void insertAllAlbums(List<AlbumEntity> entities) {
        mLocalDataSource.writeData(entities);
    }

    @VisibleForTesting
    public void deleteAllAlbums() {
        mLocalDataSource.deleteAllAlbums();
    }
}

