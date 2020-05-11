package com.example.movie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.adapters.CastAdapter;
import com.example.movie.models.Cast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MovieDatailActivity extends AppCompatActivity {

    private ImageView MovieThumbnailImg;
    private ImageView MovieCoverImg;
    private TextView tv_title;
    private TextView tv_description;
    private FloatingActionButton play_fab;
    private RecyclerView Rv_cast;
    private CastAdapter castAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        iniViews();
        // Setup List Cast
        setupRvCast();
    }

    void iniViews() {
        Rv_cast = findViewById(R.id.rv_cast);
        play_fab = findViewById(R.id.play_fab);

        String imageResourceId = getIntent().getExtras().getString("imgUrl");
        MovieThumbnailImg = findViewById(R.id.detail_movie_img);
        Glide.with(this).load(movieImagePathBuilder(imageResourceId)).into(MovieThumbnailImg);

        String imageCover = getIntent().getExtras().getString("imgCover");
        MovieCoverImg = findViewById(R.id.detail_movie_cover);
        Glide.with(this).load(movieImagePathBuilder(imageCover)).into(MovieCoverImg);

        String movieTitle = getIntent().getExtras().getString("title");
        tv_title = findViewById(R.id.detail_movie_title);
        tv_title.setText(movieTitle);

        String movieDescription = getIntent().getExtras().getString("overview");
        tv_description = findViewById(R.id.detail_movie_desc);
        tv_description.setText(movieDescription);

        getSupportActionBar().setTitle(movieTitle);

        // Setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
    }

    void setupRvCast() {
        List<Cast> mData = new ArrayList<>();
        mData.add(new Cast("Momoa", R.drawable.cast1));
        mData.add(new Cast("Julia", R.drawable.cast2));
        mData.add(new Cast("Erick", R.drawable.cast3));
        mData.add(new Cast("Mat", R.drawable.cast4));
        mData.add(new Cast("Bond", R.drawable.cast5));
        mData.add(new Cast("Robot", R.drawable.cast6));

        castAdapter = new CastAdapter(this, mData);
        Rv_cast.setAdapter(castAdapter);
        Rv_cast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public static String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }
}
