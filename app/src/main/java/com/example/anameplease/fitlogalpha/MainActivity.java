package com.example.anameplease.fitlogalpha;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.anameplease.fitlogalpha.databinding.ActivityMainBinding;
import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.romainpiel.shimmer.Shimmer;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private GyroscopeObserver gyroscopeObserver;
    private ResideMenu resideMenu;
    private ResideMenuItem itemNewLog;
    private ResideMenuItem itemViewLog;
    private ResideMenuItem itemMax;
    private ResideMenuItem itemFitUtil;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /*Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);*/

        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.bluebackground);

        resideMenu.attachToActivity(this);

        itemNewLog = new ResideMenuItem(this,R.drawable.icons8create24,"New Log" );
        itemViewLog = new ResideMenuItem(this, R.drawable.icons8_view_24,"View Log" );
        itemMax = new ResideMenuItem(this, R.drawable.icons8weightlifting50,"Max Estimate" );
        itemFitUtil = new ResideMenuItem(this, R.drawable.icons8_gym_24, "Fitness Utilites" );

        itemNewLog.setOnClickListener(this);
        itemFitUtil.setOnClickListener(this);
        itemMax.setOnClickListener(this);
        itemViewLog.setOnClickListener(this);


        resideMenu.addMenuItem(itemNewLog, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemViewLog, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMax, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFitUtil, ResideMenu.DIRECTION_LEFT);

        gyroscopeObserver = new GyroscopeObserver();

        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);

        binding.panoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        binding.panoramaImageView.setEnableScrollbar(false);

        Shimmer shim = new Shimmer();

        shim.start(binding.textView);

        binding.bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding.drawerLayout.openDrawer(Gravity.START);

                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        binding.bar.displayHomeAsUpEnabled(true);


        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);*/


        /*binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                binding.drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                switch(id){
                    case R.id.max_estimate1:
                        Intent intent2 = new Intent(MainActivity.this, MaxEstimateActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.fit_util1:
                        Intent intent4 = new Intent(MainActivity.this, FitUtilActivity.class);
                        startActivity(intent4);
                        return true;
                    case R.id.logView1:
                        Intent intent5 = new Intent(MainActivity.this, DBViewActivity.class);
                        startActivity(intent5);
                        return true;
                    case R.id.new_log2:
                        Intent intent6 = new Intent(MainActivity.this, LogCreator.class);
                        startActivity(intent6);
                        return true;

                }

                return true;
            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == itemNewLog){
            Intent intent6 = new Intent(MainActivity.this, LogCreator.class);
            startActivity(intent6);
        } else if (v == itemViewLog){
            Intent intent5 = new Intent(MainActivity.this, DBViewActivity.class);
            startActivity(intent5);
        } else if (v == itemMax) {
            Intent intent2 = new Intent(MainActivity.this, MaxEstimateActivity.class);
            startActivity(intent2);
        } else if (v == itemFitUtil){
            Intent intent4 = new Intent(MainActivity.this, FitUtilActivity.class);
            startActivity(intent4);
        }

        resideMenu.closeMenu();

    }
}
