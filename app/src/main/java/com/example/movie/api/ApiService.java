package com.example.movie.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static TheMovieDBInterface INSTANCE;

    public static TheMovieDBInterface getInstance() {
        if (INSTANCE == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(TheMovieDBInterface.class);
        }
        return INSTANCE;
    }
}
