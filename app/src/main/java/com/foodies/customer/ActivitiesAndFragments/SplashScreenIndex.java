package com.foodies.customer.ActivitiesAndFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodies.customer.Adapters.SliderPagerAdapter;
import com.foodies.customer.R;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenIndex extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private LinearLayout mDotLayout;
    private Button btnNext;
    private Button btnBack;

    private int mCurrent;

    private TextView[] mDots;

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
        mDotLayout = findViewById(R.id.dotsLayout);

        btnBack = findViewById(R.id.back);
        btnNext = findViewById(R.id.next);

        pagerAdapter = new SliderPagerAdapter(getSupportFragmentManager(),list);

        pager.setAdapter(pagerAdapter);

        addDotsIndicator(0);

        pager.addOnPageChangeListener(viewListener);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(mCurrent +1);
                Log.i("TAG", String.valueOf(mCurrent));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(mCurrent -1);
                Log.i("TAG", String.valueOf(mCurrent));
            }
        });
    }

    public  void addDotsIndicator(int position){
        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for(int i=0; i < mDots.length; i++){
            mDots[i] = new TextView(this );
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
                mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            mCurrent = i;

            if (i == 0){
                btnBack.setEnabled(true);
                btnNext.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);

                btnNext.setText("Siguiente");
                btnBack.setText("");
            }else if(i == mDots.length -1){
                btnBack.setEnabled(true);
                btnNext.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText("Continuar");
                btnBack.setText("Anterior");
            }else{
                btnBack.setEnabled(true);
                btnNext.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText("Siguiente");
                btnBack.setText("Anterior");
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}