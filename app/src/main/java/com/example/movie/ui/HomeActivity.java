package com.example.movie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.movie.models.Movie;
import com.example.movie.adapters.MovieAdapter;
import com.example.movie.adapters.MovieItemClickListener;
import com.example.movie.R;
import com.example.movie.models.Slide;
import com.example.movie.adapters.SliderPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements MovieItemClickListener {

    private List<Slide> lstSlides;
    private TabLayout indicator;
    private ViewPager sliderPager;
    private RecyclerView MoviesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderPager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movie);

        lstSlides = new ArrayList<Slide>();
        lstSlides.add(new Slide(R.drawable.slide1, "Slide 1 \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide2, "Slide 2 \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide1, "Slide 1 \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide2, "Slide 2 \nmore text here"));

        SliderPagerAdapter adapter = new SliderPagerAdapter(this, lstSlides);
        sliderPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeActivity.SliderTimer(), 4000, 6000);

        indicator.setupWithViewPager(sliderPager, true);

        //Recycleview Setup
        // ini data

        List<Movie> lstMovie = new ArrayList<>();
        lstMovie.add(new Movie("Come Unto Me", R.drawable.comeuntome, R.drawable.unmissablecover));
        lstMovie.add(new Movie("Gods Compass", R.drawable.godscompass, R.drawable.innocentscover));
        lstMovie.add(new Movie("New Life", R.drawable.newlife, R.drawable.smartercover));
        lstMovie.add(new Movie("Super Book", R.drawable.superbook, R.drawable.smartercover));

        MovieAdapter movieAdapter = new MovieAdapter(this, lstMovie, this);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        // Here we send movie information to detail activity
        // Also we will create the transition animation between the two actitivies.

        Intent intent = new Intent(this, MovieDatailActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("imgUrl", movie.getThumbnail());
        intent.putExtra("imgCover", movie.getCoverPhoto());

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
