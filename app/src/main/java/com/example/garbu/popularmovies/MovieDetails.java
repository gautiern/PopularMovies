package com.example.garbu.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbu.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    private ImageView mBackdropImage;
    private ImageView mPosterImage;
    private TextView mMovieTitle;
    private TextView mOverview;
    private TextView mMovieRating;
    private TextView mReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //get details passed from parent activity
        Bundle bundle = getIntent().getExtras();

        //get references to all detail views
        mBackdropImage = findViewById(R.id.backdropIV);
        mPosterImage = findViewById(R.id.posterIV);
        mMovieTitle = findViewById(R.id.titleTV);
        mOverview = findViewById(R.id.overviewTV);
        mMovieRating = findViewById(R.id.ratingTV);
        mReleaseDate = findViewById(R.id.releaseDateTV);

        if (bundle != null) {
            int position = bundle.getInt("position");
            Movie movie = MainActivity.movies.get(position);
            boolean landscape = false;

            //if there is a backdrop ImageView(portrait orientation) then display it
            if (mBackdropImage != null) {
                //load backdrop image with Picasso
                Picasso.with(this)
                        .load(movie.getBackdropPath())
                        .into(mBackdropImage);
            } else {
                //if there is no backdrop ImageView, it is landscape orientation
                landscape = true;
            }

            Picasso.with(this)
                    //load movie poster with Picasso
                    .load(movie.getPosterPath())
                    .into(mPosterImage);
            //set other details for selected movie
            mMovieTitle.setText(movie.getOriginalTitle());
            mOverview.setText(movie.getOverview());
            mMovieRating.setText(movie.getVoteAverage().toString());
            mReleaseDate.setText(movie.getReleaseDate());

        }
    }
}
