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

import com.example.garbu.popularmovies.model.Video;
import com.example.garbu.popularmovies.model.VideoResponse;
import com.example.garbu.popularmovies.utils.MovieInterface;
import com.example.garbu.popularmovies.utils.TrailerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.garbu.popularmovies.MainActivity.API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrailersFragment extends Fragment {
    private int mMovieId;
    private RecyclerView mTrailersRV;
    private TrailerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public TrailersFragment() {
        // Required empty public constructor
    }

    public static TrailersFragment newInstance(int movieId){
        TrailersFragment trailersFragment = new TrailersFragment();
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);
        trailersFragment.setArguments(args);
        return trailersFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieId = getArguments().getInt("movieId");
        getTrailers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trailers, container, false);
        mTrailersRV = view.findViewById(R.id.trailersRV);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mTrailersRV.setLayoutManager(layoutManager);
        // Inflate the layout for this fragment
        return view;
    }

    public void getTrailers(){
        String trailerType="Trailer";
        MovieInterface movieInterface = MovieInterface.retrofit.create(MovieInterface.class);
        Call<VideoResponse> call = movieInterface.getVideos(mMovieId, API_KEY);
        //make an asynchronous API call to the movie database
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    List<Video> videos = response.body().getResults();
                    mAdapter = new TrailerAdapter(videos);
                    mTrailersRV.setAdapter(mAdapter);
                    mTrailersRV.setHasFixedSize(true);
                    Log.d("DetailActivity", "Number of reviews received: " + videos.size());
                } else {
                    int statusCode = response.code();
                    Log.e("DetailActivity", "API request returned Status Code: " + statusCode);
                }

            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.e("MainActivity", "Error loading API results");
                Toast.makeText(getContext(), "There was an error connecting to get movie data.", Toast.LENGTH_LONG).show();
            }
        });

    }

}
