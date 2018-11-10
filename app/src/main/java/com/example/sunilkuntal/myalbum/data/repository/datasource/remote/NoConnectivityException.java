package com.example.sunilkuntal.myalbum.data.repository.datasource.remote;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "Network Connection exception";
    }

}