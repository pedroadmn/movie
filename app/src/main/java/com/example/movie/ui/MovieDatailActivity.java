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
import com.example.movie.api.ApiService;
import com.example.movie.api.response.CreditsResult;
import com.example.movie.utils.Urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private CastAdapter castAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        iniViews();
        // Setup List Cast
        iniCast();
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
        int id = getIntent().getExtras().getInt("id");
        ApiService.getInstance().getCast(id, "b716390ac8f59773894a29bdcdb2f4be")
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
}
