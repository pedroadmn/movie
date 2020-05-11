package com.example.movie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.movie.api.ApiService;
import com.example.movie.api.response.MovieResponse;
import com.example.movie.api.response.MovieResult;
import com.example.movie.adapters.MovieAdapter;
import com.example.movie.adapters.MovieItemClickListener;
import com.example.movie.R;
import com.example.movie.models.Slide;
import com.example.movie.adapters.SliderPagerAdapter;
import com.example.movie.utils.DataSource;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements MovieItemClickListener {

    private List<Slide> lstSlides;
    private TabLayout indicator;
    private ViewPager sliderPager;
    private RecyclerView MoviesRV;
    private RecyclerView MoviesRvWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initSlider();
        iniPopularMovies();
        iniWeekMovies();

    }

    private void iniWeekMovies() {
        //Recycleview Setup
        // ini data
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
        //Recycleview Setup
        // ini data
        ApiService.getInstance().getPopularMovies("b716390ac8f59773894a29bdcdb2f4be")
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
        lstSlides = new ArrayList<Slide>();
        lstSlides.add(new Slide(R.drawable.slide1, "Slide 1 \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide2, "Slide 2 \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide1, "Slide 1 \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide2, "Slide 2 \nmore text here"));

        SliderPagerAdapter adapter = new SliderPagerAdapter(this, lstSlides);
        sliderPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        indicator.setupWithViewPager(sliderPager, true);
    }

    private void initViews() {
        sliderPager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movie);
        MoviesRvWeek = findViewById(R.id.rv_movie_week);
    }

    @Override
    public void onMovieClick(MovieResponse movie, ImageView movieImageView) {
        // Here we send movie information to detail activity
        // Also we will create the transition animation between the two actitivies.

        Intent intent = new Intent(this, MovieDatailActivity.class);
        intent.putExtra("title", movie.getOriginalTitle());
        intent.putExtra("imgUrl", movie.getPosterPath());
        intent.putExtra("imgCover", movie.getPosterPath());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, movieImageView, "sharedName");

        startActivity(intent, options.toBundle());
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
}
