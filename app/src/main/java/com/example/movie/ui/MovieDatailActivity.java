package com.example.movie.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovieDatailActivity extends AppCompatActivity {

    private ImageView MovieThumbnailImg;
    private ImageView MovieCoverImg;
    private TextView tv_title;
    private TextView tv_description;
    private FloatingActionButton play_fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        iniViews();
    }

    void iniViews() {
        play_fab = findViewById(R.id.play_fab);

        int imageResourceId = getIntent().getExtras().getInt("imgUrl");
        MovieThumbnailImg = findViewById(R.id.detail_movie_img);
        Glide.with(this).load(imageResourceId).into(MovieThumbnailImg);

        int imageCover = getIntent().getExtras().getInt("imgCover");
        MovieCoverImg = findViewById(R.id.detail_movie_cover);
        Glide.with(this).load(imageCover).into(MovieCoverImg);

        String movieTitle = getIntent().getExtras().getString("title");
        tv_title = findViewById(R.id.detail_movie_title);
        tv_title.setText(movieTitle);

        getSupportActionBar().setTitle(movieTitle);

        tv_description = findViewById(R.id.detail_movie_desc);

        // Setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
    }


}
