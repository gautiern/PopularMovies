package com.example.garbu.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.util.Log;

import com.example.garbu.popularmovies.MainActivity;
import com.example.garbu.popularmovies.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by garbu on 5/6/2018.
 * Referenced tutorial on Retrofit/GSON mapping
 * URL: https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 */
@Entity //(tableName = "movies")
public class Movie {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int mMovieID;
    @SerializedName("vote_average")
    @Expose
    private Double mVoteAverage;
    @SerializedName("poster_path")
    @Expose
    private String mPosterPath;
    @SerializedName("original_title")
    @Expose
    private String mOriginalTitle;
    @SerializedName("backdrop_path")
    @Expose
    private String mBackdropPath;
    @SerializedName("overview")
    @Expose
    private String mOverview;
    @SerializedName("release_date")
    @Expose
    private String mReleaseDate;

    //Movie setters and getters

    public int getMovieID() {
        return mMovieID;
    }

    public void setMovieID(int movieID) {
        this.mMovieID = movieID;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

 //   public String getPosterPath(){return mPosterPath;}

    public String getPosterPath() {

        String baseTMDBURL = "http://image.tmdb.org/t/p/";
        String posterSize = "w342";
 //       if (landscape){
            //get larger poster size for landscape
 //           posterSize = context.getResources().getString(R.string.large_poster_size);
    //    }
        return baseTMDBURL + posterSize + mPosterPath;

    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }


    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    public String getBackdropPath (){return mBackdropPath;}
/*
    public String getBackdropPath(Context context) {
        //return backdrop image
        String baseTMDBURL = context.getResources().getString(R.string.tmdb_base_url);
        String backdropSize = context.getResources().getString(R.string.large_poster_size);
        return baseTMDBURL + backdropSize + mBackdropPath;
    }
    */

    public void setBackdropPath(String backdropPath) {
        this.mBackdropPath = backdropPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public String getReleaseDate() {
        if (mReleaseDate != null) {
            //convert the date into a nicer format
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(mReleaseDate);
                SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

                String formattedDate = format.format(date);
                return formattedDate;

            } catch (Exception e) {
                Log.e("Movie.class","Error converting date format.");

            }
        }

        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

}
