package com.example.anameplease.fitlogalpha;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.example.anameplease.fitlogalpha.databinding.ActivityMaxEstimateBinding;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;

public class MaxEstimateActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMaxEstimateBinding binding;


    private appFunc heyump = new appFunc();

    private static final int NUM_PAGES = 2;

    private PagerAdapter mPagerAdapter;

    private ArrayList<Fragment> fragmentArrayList;

    private ResideMenu resideMenu;
    private ResideMenuItem itemNewLog;
    private ResideMenuItem itemViewLog;
    private ResideMenuItem itemMax;
    private ResideMenuItem itemFitUtil;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemEditLog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_max_estimate);

        /*Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();*/

        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.bluebackground);


        resideMenu.attachToActivity(this);

        itemHome = new ResideMenuItem(this, R.drawable.icons8homepage24, "Home");
        itemNewLog = new ResideMenuItem(this,R.drawable.icons8create24,"New Log" );
        itemViewLog = new ResideMenuItem(this, R.drawable.icons8_view_24,"View Log" );
        itemEditLog = new ResideMenuItem(this, R.drawable.icons8_compose_24, "Edit Logs");
        itemMax = new ResideMenuItem(this, R.drawable.icons8weightlifting50,"Max Estimate" );
        itemFitUtil = new ResideMenuItem(this, R.drawable.icons8_gym_24, "Fitness Utilites" );

        itemNewLog.setOnClickListener(this);
        itemFitUtil.setOnClickListener(this);
        itemMax.setOnClickListener(this);
        itemViewLog.setOnClickListener(this);
        itemHome.setOnClickListener(this);
        itemEditLog.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemNewLog, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemViewLog, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemEditLog, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMax, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFitUtil, ResideMenu.DIRECTION_LEFT);
        resideMenu.addIgnoredView(binding.viewPager2);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);


        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        binding.viewPager2.setPageTransformer(true,new RotateDownTransformer());
        binding.viewPager2.setAdapter(mPagerAdapter);

        binding.indicator.setViewPager(binding.viewPager2);


        binding.bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding.drawerLayout.openDrawer(Gravity.START);

                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        binding.bar.displayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

                return onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v== itemEditLog){
            Intent intent8 = new Intent(MaxEstimateActivity.this, NoteListActivity.class);
            startActivity(intent8);
        } else if (v==itemHome){
            Intent intent7 = new Intent(MaxEstimateActivity.this, MainActivity.class);
            startActivity(intent7);
        } else if (v == itemNewLog){
            Intent intent6 = new Intent(MaxEstimateActivity.this, LogCreator.class);
            startActivity(intent6);
        } else if (v == itemViewLog){
            Intent intent5 = new Intent(MaxEstimateActivity.this, DBViewActivity.class);
            startActivity(intent5);
        } else if (v == itemMax) {
            Intent intent2 = new Intent(MaxEstimateActivity.this, MaxEstimateActivity.class);
            startActivity(intent2);
        } else if (v == itemFitUtil){
            Intent intent4 = new Intent(MaxEstimateActivity.this, FitUtilActivity.class);
            startActivity(intent4);
        }

        resideMenu.closeMenu();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return  new LowerMaxFragment();
            else
                return  new UpperMaxFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
    }



}
