package com.shahryar960103.mynotepade.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shahryar960103.mynotepade.models.Note;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    Long insertNote(Note note);

   /* //for access to a Special Note in tbl_Notes
  *//*  @Query("select * from tbl_notes where id = id")
    LiveData<Note> getANote(int Note_id);*//*

    @Update
    void updateNote(Note... note);


    @Delete
    void deleteNote(Note note);*/

/////////////////////////////////////////////////////////////////////////////////////////////////

    //The other way to describe DAO

    //for access to All Notes in tbl_Notes
    @Query("select * from tbl_notes")
    List<Note> getAllNote();

    @Insert
    public void insertANote(Note... notes);

    @Update
    public void updateANote(Note... notes);

    @Delete
    public void deleteANote(Note note);

    @Query("select * from tbl_notes")
    public List<Note> NOTE_LIST();

    @Query("select * from tbl_notes where title = :title")
    public Note getNoteWithTitle(String title);

    @Query("select * from tbl_notes where id = :id")
    public Note getNoteWithId(int id);

    @Query("Delete From tbl_notes")
    public void deleteAllNotes();

    @Query("SELECT * FROM tbl_notes ORDER BY id LIMIT 1")
    List<Note> getExistenceNotes();








}
