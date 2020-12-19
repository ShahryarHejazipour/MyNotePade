package com.shahryar960103.mynotepade.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shahryar960103.mynotepade.models.Note;

@Database(entities = Note.class,version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    public abstract DAO getDAO();

    private static NoteDataBase instance = null;

    public static synchronized NoteDataBase getInstance(Context context){

        if (instance == null){

            instance = Room.databaseBuilder(context,NoteDataBase.class,"NoteDataBase")
                    .allowMainThreadQueries().build();
        }

        return instance;

    }





}
