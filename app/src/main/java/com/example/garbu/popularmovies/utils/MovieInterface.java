package com.example.garbu.popularmovies.utils;

import com.example.garbu.popularmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by garbu on 5/6/2018./
 * */

public interface MovieInterface {

    @GET("movie/{preference}")
    Call<MovieResponse> getMovies(@Path("preference") String preference,
                                  @Query("api_key") String apiKey);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}






