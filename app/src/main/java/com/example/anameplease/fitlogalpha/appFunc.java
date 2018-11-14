package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class appFunc {



    public appFunc(){

    }

    public String lowerCalc(double a, Context context){
        Double answ1 =(a*1.09703) + 14.2546;
        Double check = null;

        if(answ1 == check){

            Toast toast = Toast.makeText(context.getApplicationContext(), "Please enter some data", Toast.LENGTH_LONG);
            toast.show();

        }

        String result = Double.toString(answ1);


        return result;
    }

    public String upperCalc(double b, Context context){
        Double answ = (b*1.1307) + 0.6998;
        Double check = null;

        if(answ == check){

            Toast toast = Toast.makeText(context.getApplicationContext(), "Please enter some data", Toast.LENGTH_LONG);
            toast.show();

        }

        String results = Double.toString(answ);


        return results;

    }

    public String readFile (File file) {

        String content = null;
        try {
            // Read the entire contents of sample.txt
            content = FileUtils.readFileToString(file, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }


    public void appendFile(File filename, String data){

        try {
            FileOutputStream fileinput = new FileOutputStream(filename, true);
            PrintStream printstream = new PrintStream(fileinput);
            printstream.print(data+"\n");
            fileinput.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void writeToSDFile(String n, String d, String nts, String fleNme, File root){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal




        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath());
        dir.mkdirs();
        File file = new File(dir, fleNme);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(n);
            pw.println(d);
            pw.println(nts);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToSDFile(String n, String fleNme, File root){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal




        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath());
        dir.mkdirs();
        File file = new File(dir, fleNme);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(n);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }

    public void conCat(String data, Notes notes, Context context){

        if(data == null){

            Toast toast = Toast.makeText(context.getApplicationContext(), "Please enter some data", Toast.LENGTH_LONG);
            toast.show();


        } else {

            String myConcatedString = notes.getNote().concat("\n").concat(data);

            notes.setNote(myConcatedString);

        }
    }

    public ArrayList<String> GetFiles() {
        ArrayList<String> MyFiles = new ArrayList<>();

        String path = Environment.getExternalStorageDirectory().toString();
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            MyFiles.add(files[i].getName());
        }
        return MyFiles;
    }







}
