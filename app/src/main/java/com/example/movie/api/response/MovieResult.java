package com.example.movie.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieResult implements Serializable {
    @SerializedName("results")
    private ArrayList<MovieResponse> movieResult;

    public MovieResult(ArrayList<MovieResponse> movieResult) {
        this.movieResult = movieResult;
    }

    public List<MovieResponse> getMovieResult() {
        return movieResult;
    }
}
