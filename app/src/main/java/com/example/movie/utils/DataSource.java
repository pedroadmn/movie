package com.example.movie.utils;

import com.example.movie.R;
import com.example.movie.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Movie> getPopularMovies() {
        List<Movie> lstMovie = new ArrayList<>();
        lstMovie.add(new Movie("Come Unto Me", R.drawable.comeuntome, R.drawable.unmissablecover));
        lstMovie.add(new Movie("Gods Compass", R.drawable.godscompass, R.drawable.innocentscover));
        lstMovie.add(new Movie("New Life", R.drawable.newlife, R.drawable.smartercover));
        lstMovie.add(new Movie("Super Book", R.drawable.superbook, R.drawable.smartercover));
        return lstMovie;
    }

    public static List<Movie> getWeekMovies() {
        List<Movie> lstMovie = new ArrayList<>();
        lstMovie.add(new Movie("New Life", R.drawable.newlife, R.drawable.smartercover));
        lstMovie.add(new Movie("Super Book", R.drawable.superbook, R.drawable.smartercover));
        lstMovie.add(new Movie("Come Unto Me", R.drawable.comeuntome, R.drawable.unmissablecover));
        lstMovie.add(new Movie("Gods Compass", R.drawable.godscompass, R.drawable.innocentscover));
        return lstMovie;
    }
}
