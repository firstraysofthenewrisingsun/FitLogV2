package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.anameplease.fitlogalpha.databinding.ActivityDbviewBinding;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.List;

public class DBViewActivity extends AppCompatActivity {

    private ActivityDbviewBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dbview);

        List<Notes> all = new Async1(getApplicationContext()).getAll();

        LegacyTableView.insertLegacyTitle("Id", "Name", "Date", "Notes");

        String[] arr = new String[all.size()];


        for (int i=0; i< all.size(); i++) {

            LegacyTableView.insertLegacyContent(all.get(i).getId().toString(), all.get(i).getName(), String.valueOf(all.get(i).getDate()), all.get(i).getNote());

        }


        binding.legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
        binding.legacyTableView.setContent(LegacyTableView.readLegacyContent());
        binding.legacyTableView.setInitialScale(50);
        binding.legacyTableView.build();

    }

    private class Async1 extends NotesServices {

        public Async1(Context context){
            super(context);
        }

        public Async1(Context context, Notes notes) {
            super(context, notes);
        }

    }

}
