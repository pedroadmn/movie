package com.example.movie.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieResponse implements Serializable {
    @SerializedName("id")
    private final int id;

    @SerializedName("poster_path")
    private final String posterPath;

    @SerializedName("backdrop_path")
    private final String backdropPath;

    @SerializedName("original_title")
    private final String originalTitle;

    @SerializedName("overview")
    private final String overview;

    public MovieResponse(int id, String posterPath, String backdropPath, String originalTitle, String overview) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public int getId() {
        return id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
