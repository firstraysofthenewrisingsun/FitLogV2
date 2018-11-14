package com.example.anameplease.fitlogalpha;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.anameplease.fitlogalpha.databinding.ActivityTxtlogBinding;

public class TXTLogActivity extends AppCompatActivity {

    private ActivityTxtlogBinding binding;

    private static final int NUM_PAGES = 2;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_txtlog);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        mPagerAdapter = new TXTLogActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        binding.viewPager2.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return  new AppendNoteFragment();
            else
                return  new ViewTXTFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
