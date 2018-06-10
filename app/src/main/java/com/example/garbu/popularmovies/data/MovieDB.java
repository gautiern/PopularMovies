package com.example.garbu.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.garbu.popularmovies.model.Movie;
import com.example.garbu.popularmovies.model.MovieResponse;

/**
 * Created by garbu on 6/9/2018.
 */
@Database(entities = {MovieResponse.class,Movie.class},version = 1, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {

    private static final String LOG_TAG = MovieDB.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movies";
    private static MovieDB sInstance;

    public static MovieDB getInstance(Context context){
        if(sInstance==null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new DB instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),MovieDB.class,MovieDB.DATABASE_NAME)
                        //.allow main thread queries to be removed
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG,"Getting DB instance");
        return sInstance;
    }
    public abstract MovieDao movieDao();
}
