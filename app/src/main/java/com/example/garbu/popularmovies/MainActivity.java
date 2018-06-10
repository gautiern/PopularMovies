package com.example.garbu.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.garbu.popularmovies.data.MovieDB;
import com.example.garbu.popularmovies.model.Movie;
import com.example.garbu.popularmovies.model.MovieResponse;
import com.example.garbu.popularmovies.utils.MovieInterface;
import com.facebook.stetho.Stetho;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, SharedPreferences.OnSharedPreferenceChangeListener {
    //Set API_KEY in gradle.properties
    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public String sortPreference;
    public static List<Movie> movies;
    private RecyclerView movieGridRV;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MovieDB mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Stetho for debugging
        Stetho.initializeWithDefaults(this);
        movieGridRV = findViewById(R.id.moviesRV);
        layoutManager = new GridLayoutManager(this, 3);
        movieGridRV.setLayoutManager(layoutManager);
        //setup Shared Prefs for settings
        setupSharedPrefs();
        //API movie request
        requestMovies();
        mDb = MovieDB.getInstance(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregister Shared Pref listener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
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

    @Override
    public void onClick(int position) {
        //when a movie poster is clicked, launch the details activity
        Intent intentToStartDetailActivity = new Intent(this, MovieDetails.class);
        intentToStartDetailActivity.putExtra("position", position);
        startActivity(intentToStartDetailActivity);

    }


    private void requestMovies() {
        // requestMovies method performs the API query to load the movies into the Movie object

        if (API_KEY == "") {
            Toast.makeText(MainActivity.this, "Please add your TMDB API key in MainActivity", Toast.LENGTH_LONG).show();
            return;
        }
        MovieInterface movieInterface = MovieInterface.retrofit.create(MovieInterface.class);
        Call<MovieResponse> call = movieInterface.getMovies(sortPreference, API_KEY);
        //make an asynchronous API call to the movie database
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    movies = response.body().getResults();
                    mAdapter = new MovieAdapter(movies, MainActivity.this);
                    movieGridRV.setAdapter(mAdapter);
                    movieGridRV.setHasFixedSize(true);
                    Log.d("MainActivity", "Number of movies received: " + movies.size());
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
