package com.example.maps2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button next_btn, skip_btn;

    TextView[] dots;
    SliderAdapter sliderAdapter;

    boolean isAndroidReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (isAndroidReady) {
                    content.getViewTreeObserver().removeOnPreDrawListener(this);

                }

                dismissSplashScreen();
                return false;

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        next_btn = findViewById(R.id.nextbtn);
        skip_btn = findViewById(R.id.skipButton);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 3)
                    mSLideViewPager.setCurrentItem(getItem(1),true);
                else {
                    Intent i = new Intent(OnBoardingActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnBoardingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        sliderAdapter = new SliderAdapter(this);

        mSLideViewPager.setAdapter(sliderAdapter);

        setUpIndicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
    }

    private void dismissSplashScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isAndroidReady = true;
            }
        }, 2000);

    }

    public void setUpIndicator(int position){

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.sec_main_color,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.main_color,getApplicationContext().getTheme()));

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpIndicator(position);

            if (position > 0){

                skip_btn.setVisibility(View.VISIBLE);

            }else {

                skip_btn.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem(int i){

        return mSLideViewPager.getCurrentItem() + i;
    }
}