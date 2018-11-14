package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.dbexporterlibrary.ExportDbUtil;
import com.example.anameplease.fitlogalpha.databinding.ActivityNoteListBinding;
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

public class NoteListActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{

    private ActivityNoteListBinding binding;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Notes> list = new ArrayList<>();
    private File root = android.os.Environment.getExternalStorageDirectory();
    private String rootPath = root.toString();

    private appFunc heyump = new appFunc();

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference uploadReference;
    private UploadTask uploadTask;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_list);

        final Context context  = getApplicationContext();

        rfaLayout = binding.activityLogRfal;
        rfaBtn = binding.activityLogRfab;

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>().setLabel("Append Log")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(1));
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


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://fitlogalpha.appspot.com/LogContainer");

        binding.recyclerView.setHasFixedSize(true);


        List<Notes> init = initNotes();

        mLayoutManager = new LinearLayoutManager(this);

        binding.recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NotesAdapter(init, new OnItemClickListener() {
            @Override
            public void onItemClick(Notes item) {

                if (TextUtils.isEmpty(binding.edttxtAppend.getText())) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Please enter the appropiate data", Toast.LENGTH_LONG);
                    toast1.show();
                } else {

                    int month = binding.simpleDatePicker.getMonth() + 1;
                    String selectedDate = binding.simpleDatePicker.getDayOfMonth()+""+month+""+binding.simpleDatePicker.getYear();
                    new Async1(getApplicationContext()).appendData(getApplicationContext(), selectedDate+"\n"+binding.edttxtAppend.getText().toString(), item);

                    mAdapter.notifyDataSetChanged();



                }

            }

        }, new ItemLongClickListener() {
            @Override
            public void onItemLongClick(Notes item) {

                new Async1(getApplicationContext()).writeDBToSD(getApplicationContext(), binding.edttxtAppend.getText().toString()+".csv");

                Toast.makeText(getApplicationContext(), "CSV created", Toast.LENGTH_LONG).show();
            }
        });

        binding.recyclerView.setAdapter(mAdapter);

        ItemTouchHelper helper =  new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                int position1 = viewHolder.getAdapterPosition();

                Notes notessss = ((NotesAdapter) mAdapter).getNoteAtPosition(position1);

                Toast.makeText(getApplicationContext(), "Deleting " +
                        notessss.getName(), Toast.LENGTH_LONG).show();

                new Async1(getApplicationContext()).deleteNote(notessss);

                Toast.makeText(getApplicationContext(), "Deleted " +
                        notessss.getName(), Toast.LENGTH_LONG).show();
            }
        });

        helper.attachToRecyclerView(binding.recyclerView);



    }

    public List<Notes> initNotes(){
        List<Notes> notesArrayList;
        notesArrayList = new Async1(getApplicationContext()).getAll();
        return notesArrayList;
    }

    public void deleteNote(Notes item){
        new Async1(getApplicationContext()).deleteNote(item);
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

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (item.getLabel()){
            case "Append Log":
                new ChooserDialog().with(this)
                        .withStartFile((rootPath))
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {

                                if (TextUtils.isEmpty(binding.edttxtAppend.getText())){
                                    Toast toast1 = Toast.makeText(getApplicationContext(), "Please enter the appropiate data", Toast.LENGTH_LONG);
                                    toast1.show();
                                } else {

                                    String data = binding.edttxtAppend.getText().toString();
                                    new Async1(getApplicationContext()).appendToSD(pathFile, binding.edttxtAppend.getText().toString(), getApplicationContext());

                                }
                            }
                        })
                        .build()
                        .show();
                break;
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


    private class Async1 extends NotesServices{

        private AppDatabase db;

        public Async1(Context context) {
            super(context);
        }

        public Async1(Context context, Integer id){
            super(context, id);
        }

        @Override
        protected Notes doInBackground(Notes... notes) {
            db.notesDao().delete(notes[0]);

            return super.doInBackground(notes);
        }
    }
}
