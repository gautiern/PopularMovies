package com.example.garbu.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.garbu.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;


/**
 * Created by garbu on 5/5/2018.
 * MovieAdapter RecyclerView for grid of movie posters
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> movieList;
    public static final String mTMDBBaseUrl = "http://image.tmdb.org/t/p/";
    public static final String mPostersize = "w342";


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.posterIV);
        }


    }
    public void setMovies(List<Movie> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }

    public MovieAdapter(List<Movie> movieData) {
        movieList = movieData;

    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grid_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        //bind each movie in the list to a view holder
        final Movie movie = movieList.get(position);
        final Context context = holder.itemView.getContext();

        //load poster with Picasso
        Picasso.with(context)
                .load(mTMDBBaseUrl + mPostersize + movie.getPosterPath())
                .into(holder.moviePoster);

        //test
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToStartDetailActivity = new Intent(context, MovieDetails.class);
                intentToStartDetailActivity.putExtra("PopularMovies", movie);
                context.startActivity(intentToStartDetailActivity);
            }
        });

    }


    @Override
    public int getItemCount() {
        //get movie count for adapter
        return movieList.size();
    }

}