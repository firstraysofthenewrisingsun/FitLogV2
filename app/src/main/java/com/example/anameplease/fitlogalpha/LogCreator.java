package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.ActivityLogCreatorBinding;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LogCreator extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_creator);


        /*Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();*/

        final Context context  = getApplicationContext();

        binding.bar.addOverflowItem("Add Log");
        binding.bar.addOverflowItem("Edit Logs");

        binding.bar.setOverflowActionItemClickListener(new AwesomeBar.OverflowActionItemClickListener() {
            @Override
            public void onOverflowActionItemClicked(int position, String item) {
                switch (item){
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
                    case "Edit Logs":
                        Intent intent5 = new Intent(LogCreator.this, NoteListActivity.class);
                        startActivity(intent5);
                        break;

                }
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://fitlogalpha.appspot.com/LogContainer");



        binding.btnBench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liftname = "Bench";
            }
        });

        binding.btnDeadlift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liftname = "Deadlifts";
            }
        });

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

    private static class Async1 extends NotesServices {

        public Async1(Context context){
            super(context);
        }

        public Async1(Context context, Notes notes) {
            super(context, notes);
        }

    }
    }






