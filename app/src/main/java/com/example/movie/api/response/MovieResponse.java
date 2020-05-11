package com.example.movie.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieResponse implements Serializable {
    @SerializedName("poster_path")
    private final String posterPath;

    @SerializedName("original_title")
    private final String originalTitle;

    @SerializedName("overview")
    private final String overview;

    public MovieResponse(String posterPath, String originalTitle, String overview) {
        this.posterPath = posterPath;
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
}
