package com.example.weatherforecast.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.weatherforecast.viewmodel.SampleDao;

@Database(entities = SampleApiCall.class, version = 1, exportSchema = false)
@TypeConverters(SampleDbCoverter.class)
public abstract class SampleDatabase extends RoomDatabase {
    public abstract SampleDao sampleDao();
    public static SampleDatabase instance;
    public static SampleDatabase getInstance(Context context){

        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SampleDatabase.class, "database-sample.db").build();
        }
        return instance;
    }
}
