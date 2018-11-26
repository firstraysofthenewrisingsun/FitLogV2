package com.example.anameplease.fitlogalpha;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;



import java.io.Serializable;

@Entity (tableName = "notes")
public class Notes implements Serializable {


    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo (name = "info_id")
    private Integer id;
    @ColumnInfo (name = "info_name")
    private String name;


    @ColumnInfo (name = "info_date")
    private int date;


    @ColumnInfo (name = "info_note")
    private String note;



    @Ignore
    public Notes(){

    }

    public Notes(Integer id, String name, int date, String note){

        this.id = id;
        this.name = name;
        this.date = date;
        this.note = note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public int getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Log Name "+name+" Date "+ date+" Notes "+note;
    }
}
