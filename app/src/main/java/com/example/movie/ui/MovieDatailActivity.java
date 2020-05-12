package com.example.movie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.adapters.CastAdapter;
import com.example.movie.api.ApiService;
import com.example.movie.api.response.CreditsResult;
import com.example.movie.models.Favorite;
import com.example.movie.utils.FavoritesViewModel;
import com.example.movie.utils.MovieRoomDatabase;
import com.example.movie.utils.Urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.movie.adapters.MovieAdapter;
import com.example.movie.api.ApiService;
import com.example.movie.api.response.MovieResult;
import com.example.movie.api.response.MovieVideoResult;
import com.example.movie.models.Cast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDatailActivity extends AppCompatActivity {

    @BindView(R.id.detail_movie_img)
    ImageView MovieThumbnailImg;

    @BindView(R.id.detail_movie_cover)
    ImageView MovieCoverImg;

    @BindView(R.id.detail_movie_title)
    TextView tv_title;

    @BindView(R.id.detail_movie_desc)
    TextView tv_description;

    @BindView(R.id.play_fab)
    FloatingActionButton play_fab;

    @BindView(R.id.rv_cast)
    RecyclerView Rv_cast;

    @BindView(R.id.fab_favorite)
    FloatingActionButton fab_favorite;

    private CastAdapter castAdapter;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        iniViews();
        // Setup List Cast
        iniCast();
        // Favorite
        FavoritesViewModel favoriteViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

        favoriteAction(favoriteViewModel);
    }

    private void favoriteAction(FavoritesViewModel favoriteViewModel) {
        String id = getIntent().getExtras().getString("id");
        fab_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(Integer.parseInt(id));
                favoriteViewModel.getAllFavorites().observe(MovieDatailActivity.this, new Observer<List<Favorite>>() {
                    @Override
                    public void onChanged(List<Favorite> favorites) {
                        favoriteViewModel.insert(favorite);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Added to favorites",
                                Toast.LENGTH_SHORT);

                        toast.show();
                    }
                });
            }
        });
    }

    void iniViews() {
        String imageResourceId = getIntent().getExtras().getString("imgUrl");
        Glide.with(this).load(Urls.movieImage500PathBuilder(imageResourceId)).into(MovieThumbnailImg);

        String imageCover = getIntent().getExtras().getString("imgCover");
        Glide.with(this).load(Urls.movieImage500PathBuilder(imageCover)).into(MovieCoverImg);

        String movieTitle = getIntent().getExtras().getString("title");
        tv_title.setText(movieTitle);

        String movieDescription = getIntent().getExtras().getString("overview");
        tv_description.setText(movieDescription);

        getSupportActionBar().setTitle(movieTitle);

        // Setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
    }

    private void iniCast() {
        //Recycleview Setup
        // ini data
        String id = getIntent().getExtras().getString("id");
        ApiService.getInstance().getCast(Integer.parseInt(id), "b716390ac8f59773894a29bdcdb2f4be")
                .enqueue(new Callback<CreditsResult>() {

                    @Override
                    public void onResponse(Call<CreditsResult> call, Response<CreditsResult> response) {
                        if (response.isSuccessful()) {
                            castAdapter = new CastAdapter(MovieDatailActivity.this, response.body().getCastResult());
                            Rv_cast.setAdapter(castAdapter);
                            Rv_cast.setLayoutManager(new LinearLayoutManager(MovieDatailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        }
                    }

                    @Override
                    public void onFailure(Call<CreditsResult> call, Throwable t) {

                    }
                });
    }

    public void showTrailer(View view) {
        String movieId = getIntent().getExtras().getString("id");

        ApiService.getInstance().getTrailers(movieId, "b716390ac8f59773894a29bdcdb2f4be")
            .enqueue(new Callback<MovieVideoResult>() {
            @Override
            public void onResponse(Call<MovieVideoResult> call, Response<MovieVideoResult> response) {
                //String urlVideo = "https://youtube.com/watch?v=" + response.body().getTrailerResult().get(0).getKey();
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
}
