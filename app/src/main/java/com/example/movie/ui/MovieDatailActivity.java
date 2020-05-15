package com.example.movie.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.adapters.CastAdapter;
import com.example.movie.api.ApiService;
import com.example.movie.api.response.CastResult;
import com.example.movie.models.Favorite;
import com.example.movie.utils.FavoritesViewModel;
import com.example.movie.utils.Urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.movie.api.response.MovieVideoResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ValueEventListener event;

    int id;

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
        //favoriteAction(favoriteViewModel);

        reference = firebaseDatabase.getInstance().getReference().child("Favorite"+Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));

        iniEvent();

        String id = getIntent().getExtras().getString("id");
        String imageResourceId = getIntent().getExtras().getString("imgUrl");
        String imageCover = getIntent().getExtras().getString("imgCover");
        String movieTitle = getIntent().getExtras().getString("title");
        String movieDescription = getIntent().getExtras().getString("overview");

        fab_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(Integer.parseInt(id),imageCover,imageResourceId,movieTitle,movieDescription);
                favoriteViewModel.insert(favorite);
                onDataChangeNotify();

                reference.child(String.valueOf(id+1)).setValue(favorite);
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.sucess_favorite,
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });
    }

    private void iniEvent() {
        event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    id = (int)dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void onDataChangeNotify() {
        reference.addListenerForSingleValueEvent(event);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notification();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void notification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText(getString(R.string.app_name))
                .setSmallIcon(R.drawable.exo_notification_small_icon)
                .setAutoCancel(true)
                .setContentText(getIntent().getExtras().getString("title")+" "+getString(R.string.sucess_favorite));

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
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
        String id = getIntent().getExtras().getString("id");
        ApiService.getInstance().getCast(Integer.parseInt(id), "b716390ac8f59773894a29bdcdb2f4be")
                .enqueue(new Callback<CastResult>() {

                    @Override
                    public void onResponse(Call<CastResult> call, Response<CastResult> response) {
                        if (response.isSuccessful()) {
                            castAdapter = new CastAdapter(MovieDatailActivity.this, response.body().getCastResult());
                            Rv_cast.setAdapter(castAdapter);
                            Rv_cast.setLayoutManager(new LinearLayoutManager(MovieDatailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        }
                    }

                    @Override
                    public void onFailure(Call<CastResult> call, Throwable t) {

                    }
                });
    }

    public void showTrailer(View view) {
        String movieId = getIntent().getExtras().getString("id");

        ApiService.getInstance().getTrailers(movieId, "b716390ac8f59773894a29bdcdb2f4be")
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
}
