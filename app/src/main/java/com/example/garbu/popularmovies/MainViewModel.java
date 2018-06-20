package com.example.garbu.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.garbu.popularmovies.data.MovieDB;
import com.example.garbu.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by garbu on 6/18/2018.
 * ViewModel for LiveData
 * Referenced lesson on Android Architecture components
 */

public class MainViewModel extends AndroidViewModel{

    private LiveData<List<Movie>> favorites;
    private static final String TAG = MainViewModel.class.getSimpleName();

    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieDB database = MovieDB.getInstance(this.getApplication());
        Log.d(TAG,"Getting favorite movies from the database");
        favorites = database.movieDao().loadMovies();
    }

    public LiveData<List<Movie>> getFavorites(){
        return favorites;
    }
}
