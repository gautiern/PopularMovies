package com.example.garbu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garbu on 6/13/2018.
 */

public class VideoResponse {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("results")
    @Expose
    private List<Video> results = null;

    //Empty Constructor
    public VideoResponse(){}

    //getters and setters
    public Integer getId(){
            return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;

    }

}
