package com.example.movie.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CastResult implements Serializable {
    @SerializedName("cast")
    private ArrayList<CastResponse> castResult;

    public CastResult(ArrayList<CastResponse> castResult) { this.castResult = castResult; }

    public List<CastResponse> getCastResult() { return castResult; }
}
