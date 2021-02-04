package com.foodies.customer.ActivitiesAndFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.foodies.customer.Adapters.SliderPagerAdapter;
import com.foodies.customer.R;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenIndex extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_index);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTheme(R.style.AppTheme);



        List<Fragment> list = new ArrayList<>();
        list.add(new PagerFragments1());
        list.add(new PagerFragments2());
        list.add(new PagerFragments3());
        list.add(new PagerFragments4());

        pager = findViewById(R.id.pagerView);
        pagerAdapter = new SliderPagerAdapter(getSupportFragmentManager(),list);

        pager.setAdapter(pagerAdapter);


    }
}