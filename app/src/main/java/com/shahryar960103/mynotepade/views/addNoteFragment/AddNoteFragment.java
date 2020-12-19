package com.shahryar960103.mynotepade.views.addNoteFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.shahryar960103.mynotepade.R;
import com.shahryar960103.mynotepade.config.MyApplication;
import com.shahryar960103.mynotepade.database.NoteDataBase;
import com.shahryar960103.mynotepade.databinding.FragmentAddNoteBinding;
import com.shahryar960103.mynotepade.models.Note;
import com.shahryar960103.mynotepade.views.homeFragment.HomeFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;


public class AddNoteFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

   FragmentAddNoteBinding binding;

   NoteDataBase noteDataBase;
   SharedPreferences sharedPreferences;
    String myPref = "Save", SIZE = "size";
    private static final int BASE_FONT_SIZE = 16;




    public AddNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(getLayoutInflater());

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar2);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteDataBase = NoteDataBase.getInstance(getActivity());
                Long result = noteDataBase.getDAO().insertNote(new Note(Objects.requireNonNull(binding.edtTxtTitle.getText()).toString(),binding.edtTxtDescription.getText().toString()
                        ,binding.btnDate.getText().toString(),binding.btnTime.getText().toString()));
              if (result>0){

                  Toast.makeText((AppCompatActivity)getActivity(), R.string.adding_note_successfully, Toast.LENGTH_SHORT).show();

              }else{
                  Toast.makeText((AppCompatActivity)getActivity(), R.string.adding_note_failed, Toast.LENGTH_SHORT).show();
              }


              // Second way to insert a note, with another Method in DAO interface
                /*noteDataBase.getDAO().insertANote(new Note(Objects.requireNonNull(binding.edtTxtTitle.getText()).toString(),binding.edtTxtDescription.getText().toString()
                        ,binding.btnDate.getText().toString(),binding.btnTime.getText().toString()));*/

                NavDirections navDirections = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment();
                Navigation.findNavController(binding.btnSave).navigate(navDirections);

            }
        });

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment();
                Navigation.findNavController(binding.imageBack).navigate(navDirections);
            }
        });

        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


                //Source of this code is in :  https://github.com/wdullaer/MaterialDateTimePicker

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(AddNoteFragment.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                // If you're calling this from a support Fragment
                dpd.show(getFragmentManager(), "Datepickerdialog");

                // If you're calling this from an AppCompatActivity
                 // dpd.show(getSupportFragmentManager(), "Datepickerdialog");

                onDateSet(dpd,now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH));

            }
        });

        binding.btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar now = Calendar.getInstance();

                TimePickerDialog timePickerDialog = TimePickerDialog
                        .newInstance(AddNoteFragment.this,now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),now.get(Calendar.SECOND),true);


                // If you're calling this from a support Fragment
                timePickerDialog.show(getFragmentManager(), "Datepickerdialog");

                // If you're calling this from an AppCompatActivity
                // dpd.show(getSupportFragmentManager(), "Datepickerdialog");

                onTimeSet(timePickerDialog,now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),now.get(Calendar.SECOND));

            }
        });
        runSetting();





        return binding.getRoot();


    }

    private void runSetting(){

        sharedPreferences = this.getActivity().getSharedPreferences(myPref,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
       // binding.txtTitleText.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.btnTime.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.btnDate.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.btnSave.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.edtTxtDescription.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.edtTxtTitle.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        binding.btnDate.setText(date);
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay+":"+minute+":"+second;
        binding.btnTime.setText(time);
    }
}