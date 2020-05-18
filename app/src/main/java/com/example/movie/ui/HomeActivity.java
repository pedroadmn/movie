package com.example.movie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.movie.api.ApiService;
import com.example.movie.api.response.MovieResponse;
import com.example.movie.api.response.MovieResult;
import com.example.movie.adapters.MovieAdapter;
import com.example.movie.adapters.MovieItemClickListener;
import com.example.movie.R;
import com.example.movie.api.response.MovieVideoResult;
import com.example.movie.models.Favorite;
import com.example.movie.adapters.SliderPagerAdapter;
import com.example.movie.utils.FavoritesViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements MovieItemClickListener {

    private List<MovieResponse> lstSlides;

    @BindView(R.id.indicator)
    TabLayout indicator;

    @BindView(R.id.slider_pager)
    ViewPager sliderPager;

    @BindView(R.id.Rv_movie)
    RecyclerView MoviesRV;

    @BindView(R.id.rv_movie_week)
    RecyclerView MoviesRvWeek;

    @BindView(R.id.rv_favorites)
    RecyclerView MovieFavories;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initSlider();
        iniPopularMovies();
        iniWeekMovies();
        iniFavoritesMovies();
    }

    private void iniFavoritesMovies() {
        FavoritesViewModel favoriteViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        List<MovieResponse> listRes = new ArrayList<>();

        favoriteViewModel.getAllFavorites().observe(HomeActivity.this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                listRes.clear();
                for (int i =0; i<favorites.size(); i++) {
                    listRes.add(
                            new MovieResponse(favorites.get(i).getMovie_id(),favorites.get(i).getBackdropPath(),
                                    favorites.get(i).getPosterPath(),favorites.get(i).getOriginalTitle(),
                                    favorites.get(i).getOverview()));
                }
                MovieAdapter movieAdapter = new MovieAdapter(HomeActivity.this, listRes, HomeActivity.this);

                MovieFavories.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                MovieFavories.setAdapter(movieAdapter);
            }
        });
    }

    private void iniWeekMovies() {
        ApiService.getInstance().getPopularMovies("b716390ac8f59773894a29bdcdb2f4be")
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        MovieAdapter weekMovieAdapter = new MovieAdapter(HomeActivity.this, response.body().getMovieResult(), HomeActivity.this);

                        MoviesRvWeek.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        MoviesRvWeek.setAdapter(weekMovieAdapter);
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {

                    }
                });
    }

    private void iniPopularMovies() {
        ApiService.getInstance().getTopRatedMovies("b716390ac8f59773894a29bdcdb2f4be")
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful()){
                            MovieAdapter movieAdapter = new MovieAdapter(HomeActivity.this, response.body().getMovieResult(), HomeActivity.this);

                            MoviesRV.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            MoviesRV.setAdapter(movieAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {

                    }
                });
    }

    private void initSlider() {
        ApiService.getInstance().getUpcomingMovies("b716390ac8f59773894a29bdcdb2f4be")
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        lstSlides = response.body().getMovieResult();
                        SliderPagerAdapter adapter = new SliderPagerAdapter(HomeActivity.this, lstSlides, HomeActivity.this);
                        sliderPager.setAdapter(adapter);

                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

                        indicator.setupWithViewPager(sliderPager, true);
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onMovieClick(MovieResponse movie, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDatailActivity.class);
        intent.putExtra("id", Integer.toString(movie.getId()));
        intent.putExtra("title", movie.getOriginalTitle());
        intent.putExtra("overview", movie.getOverview());
        intent.putExtra("imgUrl", movie.getPosterPath());
        intent.putExtra("imgCover", movie.getBackdropPath());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, movieImageView, "sharedName");

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onSlideMovieClick(MovieResponse movie) {
        ApiService.getInstance().getTrailers(Integer.toString(movie.getId()), "b716390ac8f59773894a29bdcdb2f4be")
                .enqueue(new Callback<MovieVideoResult>() {
                    @Override
                    public void onResponse(Call<MovieVideoResult> call, Response<MovieVideoResult> response) {
                        Intent intent = new Intent(getApplicationContext(), MoviePlayerActivity.class);
                        assert response.body() != null;
                        intent.putExtra("movieVideoUrl", response.body().getTrailerResult().get(0).getKey());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<MovieVideoResult> call, Throwable t) {

                    }
                });
    }

    class SliderTimer extends TimerTask {
        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderPager.getCurrentItem() < lstSlides.size() - 1){
                        sliderPager.setCurrentItem(sliderPager.getCurrentItem() + 1);
                    } else {
                        sliderPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //rest of app
}
