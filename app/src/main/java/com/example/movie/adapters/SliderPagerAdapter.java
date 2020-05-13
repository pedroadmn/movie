package com.example.movie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.api.response.MovieResponse;
import com.example.movie.models.Slide;
import com.example.movie.utils.Urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<MovieResponse> mList;
    MovieItemClickListener movieItemClickListener;

    public SliderPagerAdapter(Context mContext, List<MovieResponse> mList, MovieItemClickListener movieItemClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item, null);

        FloatingActionButton playButton = slideLayout.findViewById(R.id.slide_action_btn);

        playButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                movieItemClickListener.onSlideMovieClick(mList.get(position));
            }
        });

        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        TextView slideText = slideLayout.findViewById(R.id.slide_title);
        Glide.with(mContext).load(Urls.movieImage500PathBuilder(mList.get(position).getBackdropPath())).into(slideImg);
        slideText.setText(mList.get(position).getOriginalTitle());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
