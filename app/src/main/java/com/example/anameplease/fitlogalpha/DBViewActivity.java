package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.anameplease.fitlogalpha.databinding.ActivityDbviewBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.levitnudi.legacytableview.LegacyTableView;
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

public class DBViewActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    private ActivityDbviewBinding binding;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference uploadReference;
    private UploadTask uploadTask;
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    private File root = android.os.Environment.getExternalStorageDirectory();
    private String rootPath = root.toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dbview);

        Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://fitlogalpha.appspot.com/LogContainer");

        final Context context  = getApplicationContext();

        rfaLayout = binding.activityLogRfal;
        rfaBtn = binding.activityLogRfab;

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>().setLabel("Upload")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(2));

        rfaContent
                .setItems(items)
                .setIconShadowRadius(RFABTextUtil.dip2px(context, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(RFABTextUtil.dip2px(context, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                context,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();

        List<Notes> all = new Async1(getApplicationContext()).getAll();

        LegacyTableView.insertLegacyTitle("Id", "Name", "Date", "Notes");

        String[] arr = new String[all.size()];


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
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (item.getLabel()){
            case "Upload":
                new ChooserDialog().with(this)
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

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {

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
