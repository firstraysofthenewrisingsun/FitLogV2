package com.example.anameplease.fitlogalpha;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.anameplease.fitlogalpha.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
        });

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

}
