package com.example.anameplease.fitlogalpha;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {



    public abstract NotesDao notesDao();

}


