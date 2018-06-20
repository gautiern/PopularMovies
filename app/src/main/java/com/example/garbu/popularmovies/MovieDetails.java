package com.example.garbu.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garbu.popularmovies.data.AppExecutors;
import com.example.garbu.popularmovies.data.MovieDB;
import com.example.garbu.popularmovies.model.Movie;
import com.example.garbu.popularmovies.model.MovieResponse;
import com.example.garbu.popularmovies.model.Review;
import com.example.garbu.popularmovies.model.ReviewResponse;
import com.example.garbu.popularmovies.utils.MovieInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.garbu.popularmovies.MainActivity.API_KEY;

public class MovieDetails extends AppCompatActivity {

    private ImageView mBackdropImage;
    private ImageView mPosterImage;
    private TextView mMovieTitle;
    private TextView mOverview;
    private TextView mMovieRating;
    private TextView mReleaseDate;
    private MovieDB mDb;
    private Movie movie;
    private List<Movie> mFavorites = new ArrayList<>();
    private static final String mTMDBBaseUrl = "http://image.tmdb.org/t/p/";
    private static final String mPosterSize = "w342";
    private static final String mBackdropSize = "w500";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //get details passed from parent activity
        Bundle bundle = getIntent().getExtras();
        //DB
        mDb = MovieDB.getInstance(getApplicationContext());
        //get references to all detail views
        mBackdropImage = findViewById(R.id.backdropIV);
        mPosterImage = findViewById(R.id.posterIV);
        mMovieTitle = findViewById(R.id.titleTV);
        mOverview = findViewById(R.id.overviewTV);
        mMovieRating = findViewById(R.id.ratingTV);
        mReleaseDate = findViewById(R.id.releaseDateTV);

        if (bundle != null) {
            movie  = bundle.getParcelable("PopularMovies");
            //movie = MainActivity.mMovies.get(position);

            //load trailers fragment
            FragmentTransaction trailerFT = getSupportFragmentManager().beginTransaction();
            TrailersFragment trailersFragment = TrailersFragment.newInstance(movie.getMovieID());
            trailerFT.replace(R.id.trailersFragment, trailersFragment);
            trailerFT.commit();

            //load review fragment
            //https://guides.codepath.com/android/Creating-and-Using-Fragments#communicating-with-fragments
            FragmentTransaction reviewFT = getSupportFragmentManager().beginTransaction();
            ReviewsFragment reviewsFragment = ReviewsFragment.newInstance(movie.getMovieID());
            reviewFT.replace(R.id.reviewFragment, reviewsFragment);
            reviewFT.commit();


            //end fragment test
            boolean landscape = false;
            //if there is a backdrop ImageView(portrait orientation) then display it
            if (mBackdropImage != null) {
                //load backdrop image with Picasso
                Picasso.with(this)
                        .load(mTMDBBaseUrl + mBackdropSize + movie.getBackdropPath())
                        .into(mBackdropImage);
            } else {
                //if there is no backdrop ImageView, it is landscape orientation
                landscape = true;
            }

            Picasso.with(this)
                    //load movie poster with Picasso
                    .load(mTMDBBaseUrl + mPosterSize+ movie.getPosterPath())
                    .into(mPosterImage);
            //set other details for selected movie
            mMovieTitle.setText(movie.getOriginalTitle());
            mOverview.setText(movie.getOverview());
            mMovieRating.setText(movie.getVoteAverage().toString());
            mReleaseDate.setText(movie.getReleaseDate());

        }
        readFavorites();
 //       updateFavorite();
       // getReviews();

    }
    public  void updateFavorite(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                    // insert new task
                boolean isFavorite = false;
                for (int i=0;i<mFavorites.size();i++){
                    if (mFavorites.get(i).getMovieID()==movie.getMovieID()) {
                        isFavorite = true;
                    }
                }
                if (isFavorite) {
                    Log.d("DetailActivity", "is a favorite");
                    mDb.movieDao().deleteMovie(movie);
                }
                else {
                    Log.d("DetailActivity", "not a favorite");
                    mDb.movieDao().insertMovie(movie);

                }
                //readFavorites();
            }
        });
    }
    public void readFavorites(){
        /*
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // read favorites
                favorites = mDb.movieDao().loadMovies();

            }
        });
        */

        Log.d("MainActivity", "Getting favorites from DataBase");
        //final LiveData<List<Movie>> favorites = mDb.movieDao().loadMovies();
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d("MainActivity", "Getting update from LiveData");
                mFavorites = movies;

            }
        });

        }

}