package com.example.movie.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static TheMovieDBInterface INSTANCE;
    private static final String baseURL = "https://api.themoviedb.org/3/";

    public static TheMovieDBInterface getInstance() {
        if (INSTANCE == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(TheMovieDBInterface.class);
        }
        return INSTANCE;
    }
}
