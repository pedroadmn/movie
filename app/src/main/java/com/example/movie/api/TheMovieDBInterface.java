package com.example.movie.api;

import com.example.movie.api.response.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDBInterface {
    @GET("movie/popular")
    Call<MovieResult> getPopularMovies(@Query("api_key") String chaveApi);
}
