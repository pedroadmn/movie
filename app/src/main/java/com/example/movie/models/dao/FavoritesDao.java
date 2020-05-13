package com.example.movie.models.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movie.models.Favorite;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM favorite")
    LiveData<List<Favorite>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);

    @Delete
    public void delete(Favorite favorite);
}
