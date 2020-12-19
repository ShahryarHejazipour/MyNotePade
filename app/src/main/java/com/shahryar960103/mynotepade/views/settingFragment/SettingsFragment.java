package com.shahryar960103.mynotepade.views.settingFragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


import com.shahryar960103.mynotepade.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {

   FragmentSettingsBinding binding;
   int fontSize = 16;

   SharedPreferences sharedPreferences;
   String myPref = "Save", SIZE = "size", FONT_NAZANIN = "nazanin"
           , FONT_CHILD = "child", FONT_TITR = "titr",FONT_MJ = "mj";


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(getLayoutInflater(),container,false);


        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavDirections navDirections = SettingsFragmentDirections.actionSettingsFragmentToHomeFragment();
                NavHostFragment.findNavController(SettingsFragment.this).navigate(navDirections);

            }
        });


        //set font size
        sharedPreferences = this.getActivity().getSharedPreferences(myPref,0);
        binding.seekBarFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                fontSize=i;
                binding.txtFontSize.setTextSize(fontSize);
                editor.putInt(SIZE,fontSize);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (fontSize<16){
                    fontSize = 16;
                    binding.seekBarFontSize.setProgress(fontSize);
                }


            }
        });
        if (sharedPreferences.contains(SIZE)){
            fontSize=sharedPreferences.getInt(SIZE,16);
            binding.seekBarFontSize.setProgress(fontSize);
            binding.txtFontSize.setTextSize(fontSize);
        }

        // set Fonts
        changeFontStyle();



        return binding.getRoot();
    }

    private void changeFontStyle() {

        //definition typefaces for access to fonts
        Typeface typeface_nazanin = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BNAZANB.TTF");
        Typeface typeface_titr = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BTITRBD.TTF");
        Typeface typeface_child = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BKOODAKO.TTF");
        Typeface typeface_mj_Nil = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/Mj_Nil 3.ttf");

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (binding.radioNazanin.isChecked()){
                    binding.txtFontSize.setTypeface(typeface_nazanin);
                }else if (binding.radioChild.isChecked()){
                    binding.txtFontSize.setTypeface(typeface_child);
                }else if (binding.radioTitr.isChecked()){
                    binding.txtFontSize.setTypeface(typeface_titr);
                }else if (binding.radioMj.isChecked()){
                    binding.txtFontSize.setTypeface(typeface_mj_Nil);
                }

                //saving radio button checking condition in shared preferences
                editor.putBoolean(FONT_NAZANIN,binding.radioNazanin.isChecked());
                editor.putBoolean(FONT_CHILD,binding.radioChild.isChecked());
                editor.putBoolean(FONT_TITR,binding.radioTitr.isChecked());
                editor.putBoolean(FONT_MJ,binding.radioMj.isChecked());
                editor.apply();

            }
        });

        if (sharedPreferences.contains(FONT_TITR)&&sharedPreferences.contains(FONT_CHILD)
                &&sharedPreferences.contains(FONT_NAZANIN)&&sharedPreferences.contains(FONT_MJ)){

            //for stabling checked radio button
            binding.radioNazanin.setChecked(sharedPreferences.getBoolean(FONT_NAZANIN,false));
            binding.radioChild.setChecked(sharedPreferences.getBoolean(FONT_CHILD,false));
            binding.radioTitr.setChecked(sharedPreferences.getBoolean(FONT_TITR,false));
            binding.radioMj.setChecked(sharedPreferences.getBoolean(FONT_MJ,false));


            //for stabling fonts when fragment is in onResume
            if (binding.radioNazanin.isChecked()){
                binding.txtFontSize.setTypeface(typeface_nazanin);
            }else if (binding.radioChild.isChecked()){
                binding.txtFontSize.setTypeface(typeface_child);
            }else if (binding.radioTitr.isChecked()){
                binding.txtFontSize.setTypeface(typeface_titr);
            }else if (binding.radioMj.isChecked()){
                binding.txtFontSize.setTypeface(typeface_mj_Nil);
            }

        }



    }



}