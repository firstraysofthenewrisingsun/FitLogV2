package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.ActivityDbviewBinding;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.levitnudi.legacytableview.LegacyTableView;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class DBViewActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityDbviewBinding binding;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference uploadReference;
    private UploadTask uploadTask;
    private File root = android.os.Environment.getExternalStorageDirectory();
    private String rootPath = root.toString();
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dbview);

        /*Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();*/

        binding.bar.addAction(R.drawable.icons8upload24, "Upload");

        binding.bar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {
                switch (actionItem.getText()){
                    case "Upload":
                        new ChooserDialog().with(DBViewActivity.this)
                                .withStartFile((rootPath))
                                .withChosenListener(new ChooserDialog.Result() {
                                    @Override
                                    public void onChoosePath(String path, File pathFile) {

                                        fireBaseUpload(path,pathFile.getName());
                                    }
                                })
                                .build()
                                .show();
                        break;
                }
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://fitlogalpha.appspot.com/LogContainer");

        final Context context  = getApplicationContext();

        List<Notes> all = new Async1(getApplicationContext()).getAll();

        LegacyTableView.insertLegacyTitle("Id", "Name", "Date", "Notes");

        for (int i=0; i< all.size(); i++) {

            LegacyTableView.insertLegacyContent(all.get(i).getId().toString(), all.get(i).getName(), String.valueOf(all.get(i).getDate()), all.get(i).getNote());

        }


        binding.legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
        binding.legacyTableView.setContent(LegacyTableView.readLegacyContent());
        binding.legacyTableView.setInitialScale(50);
        binding.legacyTableView.setZoomEnabled(true);
        binding.legacyTableView.setShowZoomControls(true);
        binding.legacyTableView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(Objects.equals(binding.editText.getText().toString(), null)){
                    Toast.makeText(getApplicationContext(), "Enter a file name", Toast.LENGTH_LONG).show();

                }

                String filename = binding.editText.getText().toString()+".csv";

                new DBViewActivity.Async1(getApplicationContext()).writeDBToSD(getApplicationContext(), filename);

                Toast.makeText(getApplicationContext(), "CSV created", Toast.LENGTH_LONG).show();

                return false;
            }
        });
        binding.legacyTableView.build();

        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.bluebackground);


        resideMenu.attachToActivity(this);

        itemHome = new ResideMenuItem(this, R.drawable.icons8homepage24, "Home");
        itemNewLog = new ResideMenuItem(this,R.drawable.icons8create24,"New Log" );
        itemViewLog = new ResideMenuItem(this, R.drawable.icons8_view_24,"View Log" );
        itemEditLog = new ResideMenuItem(this, R.drawable.icons8_compose_24, "Edit Logs");
        itemMax = new ResideMenuItem(this, R.drawable.icons8weightlifting50,"Max Estimate" );
        itemFitUtil = new ResideMenuItem(this, R.drawable.icons8_gym_24, "Fitness Utilites" );

        itemHome.setOnClickListener(this);
        itemNewLog.setOnClickListener(this);
        itemFitUtil.setOnClickListener(this);
        itemMax.setOnClickListener(this);
        itemViewLog.setOnClickListener(this);
        itemEditLog.setOnClickListener(this);


        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemNewLog, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemViewLog, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemEditLog, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMax, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFitUtil, ResideMenu.DIRECTION_LEFT);

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        binding.bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding.drawerLayout.openDrawer(Gravity.START);

                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        binding.bar.displayHomeAsUpEnabled(true);

    }

    public void fireBaseUpload (String path, String FileName){
        File file = new File(path);


        uploadReference = storageReference.child(FileName);

        uploadTask= uploadReference.putFile(Uri.fromFile(file));

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast toast = Toast.makeText(getApplicationContext(), "Success!!!", Toast.LENGTH_LONG);
                toast.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_LONG);
                toast.show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {

        if (v== itemEditLog){
            Intent intent8 = new Intent(DBViewActivity.this, NoteListActivity.class);
            startActivity(intent8);
        } else if (v==itemHome){
            Intent intent7 = new Intent(DBViewActivity.this, MainActivity.class);
            startActivity(intent7);
        } else if (v == itemNewLog){
            Intent intent6 = new Intent(DBViewActivity.this, LogCreator.class);
            startActivity(intent6);
        } else if (v == itemViewLog){
            Intent intent5 = new Intent(DBViewActivity.this, DBViewActivity.class);
            startActivity(intent5);
        } else if (v == itemMax) {
            Intent intent2 = new Intent(DBViewActivity.this, MaxEstimateActivity.class);
            startActivity(intent2);
        } else if (v == itemFitUtil){
            Intent intent4 = new Intent(DBViewActivity.this, FitUtilActivity.class);
            startActivity(intent4);
        }

        resideMenu.closeMenu();
    }




    private static class Async1 extends NotesServices {

        public Async1(Context context){
            super(context);
        }

        public Async1(Context context, Notes notes) {
            super(context, notes);
        }

    }

}
