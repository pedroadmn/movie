package com.example.movie.adapters;

import android.widget.ImageView;

import com.example.movie.api.response.MovieResponse;

public interface MovieItemClickListener {
    void onMovieClick(MovieResponse movie, ImageView movieImageView); // We will need the imageview to make shared animatio between the two actitivies.
    void onSlideMovieClick(MovieResponse movie);
}
