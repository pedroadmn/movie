package com.example.movie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.api.response.MovieResponse;
import com.example.movie.utils.Urls;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context context;
    List<MovieResponse> mData;
    MovieItemClickListener movieItemClickListener;

    public MovieAdapter(Context context, List<MovieResponse> mData, MovieItemClickListener listener) {
        this.context = context;
        this.mData = mData;
        this.movieItemClickListener = listener;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.TvTitle.setText(mData.get(i).getOriginalTitle());
        Glide.with(context).load(Urls.movieImage500PathBuilder(mData.get(i).getPosterPath())).into(myViewHolder.ImgMovie);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TvTitle;
        private ImageView ImgMovie;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            TvTitle = itemView.findViewById(R.id.item_movie_title);
            ImgMovie = itemView.findViewById(R.id.item_movie_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClickListener.onMovieClick(mData.get(getAdapterPosition()), ImgMovie);
                }
            });
        }
    }
}
