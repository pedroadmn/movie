package com.example.movie.utils;

public class Urls {

    public static String movieImage500PathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }

    public static String movieImage185PathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w185" +
                imagePath;
    }
}
