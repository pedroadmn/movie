package com.example.movie.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieVideoResult implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<VideoResponse> trailerResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<VideoResponse> getTrailerResult() {
        return trailerResult;
    }

    public void setTrailerResult(ArrayList<VideoResponse> trailerResult) {
        this.trailerResult = trailerResult;
    }
}