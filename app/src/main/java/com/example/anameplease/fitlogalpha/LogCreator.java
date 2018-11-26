package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.ActivityLogCreatorBinding;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Objects;


public class LogCreator extends AppCompatActivity implements View.OnClickListener {

    private ActivityLogCreatorBinding binding;

    private Notes noteToInsert;

    private static AppDatabase db;

    private appFunc heyump = new appFunc();

    private File root = android.os.Environment.getExternalStorageDirectory();
    private String rootPath = root.toString();



    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference uploadReference;
    private UploadTask uploadTask;
    private String liftname;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_creator);

        /*Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();*/

        final Context context  = getApplicationContext();

        binding.bar.addAction(R.drawable.icons8_add_24, "Add Log");

        binding.bar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {
                switch (actionItem.getText()){
                    case "Add Log":

                        if ( TextUtils.isEmpty(binding.txtID.getText()) || (binding.simpleDatePicker.getYear() == 0) || TextUtils.isEmpty(binding.txtNt.getText()) || (binding.simpleDatePicker.getMonth() == 0) || (binding.simpleDatePicker.getDayOfMonth() == 0)){
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Please enter the appropriate data", Toast.LENGTH_LONG);
                            toast1.show();
                        } else {

                            Integer id = Integer.valueOf(binding.txtID.getText().toString());

                            List<Notes> notesList = new Async1(getApplicationContext()).getAll();

                            int newid = newID(id, notesList);

                            String name = liftname;
                            int month = binding.simpleDatePicker.getMonth() + 1;

                            String selectedDate = binding.simpleDatePicker.getDayOfMonth()+""+month+""+binding.simpleDatePicker.getYear();

                            Integer date = Integer.valueOf(selectedDate);
                            String note = binding.txtNt.getText().toString();

                            noteToInsert  = new Notes(newid, name, date, note);

                            new Async1(getApplicationContext(), noteToInsert).execute(noteToInsert);

                            Toast toast = Toast.makeText(getApplicationContext(), "Success!!! "+noteToInsert.getName()+" "+noteToInsert.getId(), Toast.LENGTH_SHORT);
                            toast.show();


                        }

                        break;


                }
            }
        });

        binding.bar.addOverflowItem("Add From CSV");
        binding.bar.setOverflowActionItemClickListener(new AwesomeBar.OverflowActionItemClickListener() {
            @Override
            public void onOverflowActionItemClicked(int position, String item) {
                switch (item){
                    case "Add From CSV":
                        new ChooserDialog().with(LogCreator.this)
                                .withStartFile((rootPath))
                                .withChosenListener(new ChooserDialog.Result() {
                                    @Override
                                    public void onChoosePath(String path, File pathFile) {

                                        try {
                                            Reader in = new FileReader(path);

                                            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("info_id", "info_name", "info_date", "info_note").withIgnoreHeaderCase().withTrim().withSkipHeaderRecord(true).parse(in);
                                            for (CSVRecord record : records) {

                                                if(record.size() >= ((CSVParser) records).getHeaderMap().size()){


                                                    String info_id = record.get("info_id");
                                                    String info_name = record.get("info_name");
                                                    String info_date = record.get("info_date");
                                                    String info_note = record.get("info_note");

                                                    List<Notes> notesList = new Async1(getApplicationContext()).getAll();

                                                    int newid = newID(Integer.valueOf(info_id), notesList);




                                                        Notes newNoteInHere = new Notes(newid, info_name, Integer.valueOf(info_date), info_note);

                                                        new Async1(getApplicationContext(), newNoteInHere).execute(newNoteInHere);



                                                Toast toast = Toast.makeText(getApplicationContext(), newid+" "+info_date+" "+info_name+" "+info_note, Toast.LENGTH_SHORT);
                                                toast.show();
                                            }

                                            }

                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                })
                                .build()
                                .show();
                        break;
                }
            }
        });

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


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://fitlogalpha.appspot.com/LogContainer");



        binding.btnBench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liftname = "Bench";
                Toast toast = Toast.makeText(getApplicationContext(), "Bench Selected!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        binding.btnDeadlift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liftname = "Deadlifts";
                Toast toast = Toast.makeText(getApplicationContext(), "Deadlift Selected!", Toast.LENGTH_LONG);
                toast.show();


            }
        });

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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
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

    public int newID(int ids, List<Notes> notesList1) {

        int i = 0;
        int newid = 0;
        while (i < notesList1.size()) {
            if (Objects.equals(ids, notesList1.get(i).getId()))
                ids = notesList1.get(i).getId() + 1;

            if (Objects.equals(ids, notesList1.get(i).getId())){

                return newID(ids, notesList1);
            } else {
                 newid = ids;

                Toast toast1 = Toast.makeText(getApplicationContext(), "Duplicate ID caught. New ID: "+newid, Toast.LENGTH_SHORT);
                toast1.show();
            }
            i++;
        }

        return newid;
    }

    @Override
    public void onClick(View v) {
        if (v== itemEditLog){
            Intent intent8 = new Intent(LogCreator.this, NoteListActivity.class);
            startActivity(intent8);
        } else if (v==itemHome){
            Intent intent7 = new Intent(LogCreator.this, MainActivity.class);
            startActivity(intent7);
        } else if (v == itemNewLog){
            Intent intent6 = new Intent(LogCreator.this, LogCreator.class);
            startActivity(intent6);
        } else if (v == itemViewLog){
            Intent intent5 = new Intent(LogCreator.this, DBViewActivity.class);
            startActivity(intent5);
        } else if (v == itemMax) {
            Intent intent2 = new Intent(LogCreator.this, MaxEstimateActivity.class);
            startActivity(intent2);
        } else if (v == itemFitUtil){
            Intent intent4 = new Intent(LogCreator.this, FitUtilActivity.class);
            startActivity(intent4);
        }

        resideMenu.closeMenu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                // Re-attempt file write

        }
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






