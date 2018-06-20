package com.example.garbu.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.garbu.popularmovies.data.AppExecutors;
import com.example.garbu.popularmovies.data.MovieDB;
import com.example.garbu.popularmovies.model.Movie;
import com.example.garbu.popularmovies.model.MovieResponse;
import com.example.garbu.popularmovies.utils.MovieInterface;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    //Set API_KEY in gradle.properties
    public  static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public String sortPreference;
    public static List<Movie> mMovies = new ArrayList<>();
    public static List<Movie> mFavorites = new ArrayList<>();
    private RecyclerView movieGridRV;
    private MovieAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MovieDB mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setupViewModel();

        setContentView(R.layout.activity_main);
        movieGridRV = findViewById(R.id.moviesRV);
        mLayoutManager = new GridLayoutManager(this, 3);
        movieGridRV.setLayoutManager(mLayoutManager);
        mDb = MovieDB.getInstance(getApplicationContext());
        //setup Shared Prefs for settings
        setupSharedPrefs();
        mAdapter = new MovieAdapter(mMovies);
        movieGridRV.setAdapter(mAdapter);
        movieGridRV.setHasFixedSize(true);
        requestMovies();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregister Shared Pref listener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setupViewModel();
        //requestMovies();
        Log.d("MainActivity", "OnResume Favorites: " +mFavorites);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();
        if (selectedItem == R.id.action_settings) {
            //start Settings activity
            Intent startSettings = new Intent(this, Settings.class);
            startActivity(startSettings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewModel(){

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d("MainActivity", "Getting Favorites from LiveData in ViewModel - " + movies.size());
                //mMovies = movies;
                mAdapter.setMovies(movies);

            }

        });
    }

    private void requestMovies() {
        // requestMovies method
        //DB call for favorites
        if (sortPreference.equals(getString(R.string.pref_favorites))) {
            Log.d("MainActivity", "RequestMovies Favorites: " +mFavorites);
            //mMovies = mFavorites;
            //mAdapter.setMovies(mMovies);
            setupViewModel();

        } else {
           // perform the API query to load the movies into the Movie object


            if (API_KEY == "") {
                Toast.makeText(MainActivity.this, "Please add your TMDB API key in gradle.properties", Toast.LENGTH_LONG).show();
                return;
            }
            MovieInterface movieInterface = MovieInterface.retrofit.create(MovieInterface.class);
            Call<MovieResponse> call = movieInterface.getMovies(sortPreference, API_KEY);
            //make an asynchronous API call to the movie database
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        mMovies.clear();
                        mMovies = response.body().getResults();
                        mAdapter.setMovies(mMovies);
                        Log.d("MainActivity", "Number of movies received: " + mMovies.size());
                    } else {
                        int statusCode = response.code();
                        Log.e("MainActivity", "API request returned Status Code: " + statusCode);
                    }

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e("MainActivity", "Error loading API results");
                    Toast.makeText(MainActivity.this, "There was an error connecting to get movie data.", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    //Initial method to read sort preference and provide default sort method, in none exists
    private void setupSharedPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sortPreference = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_most_popular));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    //Invoked when the preference is changed to update the sorting option and query API for the selected sort method
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_key))) {
            sortPreference = sharedPreferences.getString(key, getString(R.string.pref_most_popular));
            requestMovies();

        }
    }

}
