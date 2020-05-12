package com.example.movie.api.response;

import com.google.gson.annotations.SerializedName;

public class CastResponse {
    @SerializedName("name")
    private final String name;

    @SerializedName("profile_path")
    private final String profile_path;

    public CastResponse(String name, String profile_path) {
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }
}
