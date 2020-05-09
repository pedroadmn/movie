package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    private List<Slide> lstSlides;
    private TabLayout indicator;
    private ViewPager sliderPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderPager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);

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
