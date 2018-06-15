package com.example.garbu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by garbu on 6/13/2018.
 */

public class Review {

    @SerializedName("author")
    @Expose
    private String author;


    @SerializedName("content")
    @Expose
    private String content;


    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("url")
    @Expose
    private String url;

    //empty constructor
    public Review() {

    }

    //getters and setters

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
