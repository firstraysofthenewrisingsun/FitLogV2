package com.example.anameplease.fitlogalpha;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.provider.ContactsContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Notes> getAll();

    @Query("SELECT * FROM notes WHERE info_name =:selectedName")
    Notes searchByName(String selectedName);

    @Query("SELECT * FROM notes WHERE info_id =:selectedID")
    Notes searchByID(Integer selectedID);

    @Query("SELECT * FROM notes WHERE info_id =:checkedID")
    List<Notes> getAllByID(Integer checkedID);

    @Update
    void updateNote (Notes item);

    @Insert
    void insert(Notes item);

    @Delete
    void delete(Notes item);
}
