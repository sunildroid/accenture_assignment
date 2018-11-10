package com.example.sunilkuntal.myalbum.data.repository.datasource;

import android.arch.lifecycle.LiveData;



public interface DataSource<T> {

    LiveData<T> getDataStream();
    LiveData<String> getErrorStream();
}
