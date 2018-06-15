package com.example.garbu.popularmovies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.garbu.popularmovies.model.Movie;
import com.example.garbu.popularmovies.model.Review;
import com.example.garbu.popularmovies.model.ReviewResponse;
import com.example.garbu.popularmovies.utils.MovieInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.garbu.popularmovies.MainActivity.API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {
    private int mMovieId;
    private RecyclerView mReviewsRV;
    private ReviewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static ReviewsFragment newInstance(int movieId){
        ReviewsFragment reviewsFragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);
        reviewsFragment.setArguments(args);
        return reviewsFragment;
    }

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieId = getArguments().getInt("movieId");
        getReviews();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        mReviewsRV = view.findViewById(R.id.reviewsRV);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mReviewsRV.setLayoutManager(layoutManager);
        // Inflate the layout for this fragment
        return view;
    }


    public void getReviews(){

        MovieInterface movieInterface = MovieInterface.retrofit.create(MovieInterface.class);
        Call<ReviewResponse> call = movieInterface.getReviews(mMovieId, API_KEY);
        //make an asynchronous API call to the movie database
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body().getResults();
                    mAdapter = new ReviewAdapter(reviews);
                    mReviewsRV.setAdapter(mAdapter);
                    mReviewsRV.setHasFixedSize(true);
                    Log.d("DetailActivity", "Number of reviews received: " + reviews.size());
                } else {
                    int statusCode = response.code();
                    Log.e("DetailActivity", "API request returned Status Code: " + statusCode);
                }

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("MainActivity", "Error loading API results");
                Toast.makeText(getContext(), "There was an error connecting to get movie data.", Toast.LENGTH_LONG).show();
            }
        });

    }

}
