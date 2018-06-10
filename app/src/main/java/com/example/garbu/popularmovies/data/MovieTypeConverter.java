package com.example.garbu.popularmovies.data;

import android.arch.persistence.room.TypeConverter;

import com.example.garbu.popularmovies.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by garbu on 6/9/2018.
 */

public class MovieTypeConverter {

    @TypeConverter
    public static List<Movie> stringToMovies(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Movie>>() {}.getType();
        List<Movie> movies = gson.fromJson(json, type);
        return movies;
    }
    @TypeConverter
    public static String moviesToString(List<Movie> list){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Movie>>(){}.getType();
        String json = gson.toJson(list,type);
        return json;
    }
}
