package com.example.onbording;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //full screen
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (restorePrefData()){
            Intent a = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(a);
            finish();
        }
        //end full screen
        setContentView(R.layout.activity_intro);

        // INDICATOR
        tabIndicator = findViewById(R.id.tabLayout_indicator);
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_getStarted);


        // fill screen list
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Анализы", "Экспресс сбор и получение проб", R.drawable.pic1));
        mList.add(new ScreenItem("Уведомление", "Экспресс сбор и получение проб", R.drawable.pic2));
        mList.add(new ScreenItem("Мониторинг", "Экспресс сбор и получение проб", R.drawable.pic3));

        //setup viewpager
        screenPager = findViewById(R.id.viewPager2);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        //next btn click
         btnNext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 position = screenPager.getCurrentItem();
                  if (position < mList.size()){
                      position++;
                      screenPager.setCurrentItem(position);
                  }
                  if (position == mList.size()-1){// last screen
                      loadLoadScreen();

                  }
             }
         });

         tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {

                 if (tab.getPosition() == mList.size()-1){
                     loadLoadScreen();
                 }
             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });

         btnGetStarted.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(i);
                 savePrefsData();
                 finish();
             }
         });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "myPref", MODE_PRIVATE);
        Boolean isIntroActivityOpened = pref.getBoolean("isIntroOpened", false);
        return isIntroActivityOpened;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

    private void loadLoadScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
    }
}