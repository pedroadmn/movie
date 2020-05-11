package com.example.movie.api;

import com.example.movie.api.response.MovieResult;
import com.example.movie.api.response.MovieVideoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBInterface {
    @GET("movie/popular")
    Call<MovieResult> getPopularMovies(@Query("api_key") String chaveApi);

    @GET("movie/{id}/videos")
    Call<MovieVideoResult> getTrailers(@Path("id") String movieId, @Query("api_key") String userkey);
}
