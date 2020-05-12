package com.example.movie.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movie.models.Favorite;
import com.example.movie.models.dao.FavoritesDao;

@Database(entities = Favorite.class, version = 1)
public abstract class MovieRoomDatabase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();

    private static volatile MovieRoomDatabase movieRoomInstance;

    static MovieRoomDatabase getDatabase(final Context context) {
        if (movieRoomInstance == null) {
            synchronized (MovieRoomDatabase.class) {
                if (movieRoomInstance == null) {
                    movieRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .build();
                }
            }
        }
        return movieRoomInstance;
    }
}
