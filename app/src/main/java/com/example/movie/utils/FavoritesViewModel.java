package com.example.movie.utils;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movie.models.Favorite;
import com.example.movie.models.dao.FavoritesDao;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesDao favoriteDao;
    private MovieRoomDatabase movieRoomDatabase;
    private LiveData<List<Favorite>> allFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        movieRoomDatabase = MovieRoomDatabase.getDatabase(application);
        favoriteDao = movieRoomDatabase.favoritesDao();
        allFavorites = favoriteDao.getAll();
    }

    public void insert(Favorite favorite) {
        new InsertAsyncTask(favoriteDao).execute(favorite);
    }

    public void delete(Favorite favorite) {
        new DeleteAsyncTask(favoriteDao).execute(favorite);
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }

    private class InsertAsyncTask extends AsyncTask<Favorite, Void, Void> {

        FavoritesDao favoritesDao;

        public InsertAsyncTask(FavoritesDao favoritesDao) {
            this.favoritesDao = favoritesDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.insert(favorites[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Favorite, Void, Void> {

        FavoritesDao favoritesDao;

        public DeleteAsyncTask(FavoritesDao favoritesDao) {
            this.favoritesDao = favoritesDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.delete(favorites[0]);
            return null;
        }
    }
}
