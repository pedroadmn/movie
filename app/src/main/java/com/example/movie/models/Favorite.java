package com.example.movie.models;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "favorite")
public class Favorite {
    @PrimaryKey
    @NonNull
    private int movie_id;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "original_title")
    private String originalTitle;

    @ColumnInfo(name = "overview")
    private String overview;

    public Favorite(int movie_id, String posterPath, String backdropPath, String originalTitle, String overview) {
        this.movie_id = movie_id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }
}
