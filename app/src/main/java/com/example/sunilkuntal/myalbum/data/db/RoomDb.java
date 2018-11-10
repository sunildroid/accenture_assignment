package com.example.sunilkuntal.myalbum.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.sunilkuntal.myalbum.data.entities.AlbumEntity;


@Database(entities = {AlbumEntity.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase{

    static final String DATABASE_NAME = "album_data";
    private static RoomDb INSTANCE;

    public abstract AlbumDao albumDao();
    public static RoomDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                    RoomDb.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

}
