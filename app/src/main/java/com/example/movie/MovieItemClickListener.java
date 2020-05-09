package com.example.movie;

import android.widget.ImageView;

public interface MovieItemClickListener {
    void onMovieClick(Movie movie, ImageView movieImageView); // We will need the imageview to make shared animatio between the two actitivies.

}
