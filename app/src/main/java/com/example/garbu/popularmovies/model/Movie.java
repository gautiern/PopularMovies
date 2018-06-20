package com.example.garbu.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.garbu.popularmovies.MainActivity;
import com.example.garbu.popularmovies.R;
import com.example.garbu.popularmovies.data.MovieTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by garbu on 5/6/2018.
 * Referenced tutorial on Retrofit/GSON mapping
 * URL: https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 */
@Entity
public class Movie implements Parcelable{
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

    //Empty Constructor
    public Movie(){}

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


    public String getPosterPath() {return mPosterPath;}

    public void setPosterPath(String posterPath) {this.mPosterPath = posterPath;}

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    public String getBackdropPath (){return mBackdropPath;}

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

    //Parcelable methods
    //Referred to example project: https://github.com/udacity/android-custom-arrayadapter/tree/parcelable


    public Movie(Parcel in) {
        mOriginalTitle = in.readString();
        mPosterPath = in.readString();
        mBackdropPath = in.readString();
        mOverview = in.readString();
        mVoteAverage = (Double) in.readValue(Double.class.getClassLoader());
        mReleaseDate = in.readString();
        mMovieID = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mPosterPath);
        parcel.writeString(mBackdropPath);
        parcel.writeString(mOverview);
        parcel.writeValue(mVoteAverage);
        parcel.writeString(mReleaseDate);
        parcel.writeValue(mMovieID);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
