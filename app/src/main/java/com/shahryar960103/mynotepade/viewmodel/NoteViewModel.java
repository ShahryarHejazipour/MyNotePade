package com.shahryar960103.mynotepade.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shahryar960103.mynotepade.models.Note;

public class NoteViewModel extends ViewModel {

    MutableLiveData<Note> noteMutableLiveData;

    public MutableLiveData<Note> getNoteMutableLiveData(){
        if (noteMutableLiveData == null){
            noteMutableLiveData = new MutableLiveData<>();
        }
        return noteMutableLiveData;
    }


}
