package com.shahryar960103.mynotepade.views.aboutUsFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shahryar960103.mynotepade.R;
import com.shahryar960103.mynotepade.databinding.FragmentAboutUsBinding;


public class AboutUsFragment extends Fragment {
        FragmentAboutUsBinding binding;
        SharedPreferences sharedPreferences;
    String myPref = "Save", SIZE = "size", FONT_NAZANIN = "nazanin"
            , FONT_CHILD = "child", FONT_TITR = "titr";
    private static final int BASE_FONT_SIZE = 16;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAboutUsBinding.inflate(getLayoutInflater(),container,false);

        binding.txtWebAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://android-learn.ir"));
                startActivity(intent);

            }
        });





        binding.txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:35°42'39.2,N 51°24'30.3"));
                startActivity(intent);
            }
        });

        binding.btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections = AboutUsFragmentDirections.actionAboutUsFragmentToAddNoteFragment();
                Navigation.findNavController(binding.btnNewNote).navigate(navDirections);
            }
        });

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections = AboutUsFragmentDirections.actionAboutUsFragmentToHomeFragment();
                Navigation.findNavController(binding.imageBack).navigate(navDirections);
            }
        });
        runSetting();


        return binding.getRoot();
    }

    private void runSetting(){

        //definition typefaces for access to fonts
        Typeface typeface_nazanin = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BNAZANB.TTF");
        Typeface typeface_titr = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BTITRBD.TTF");
        Typeface typeface_child = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BKOODAKO.TTF");


        sharedPreferences = this.getActivity().getSharedPreferences(myPref,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        binding.btnNewNote.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        //binding.txtAddress.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.txtWebAddress.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        if (sharedPreferences.getBoolean(FONT_NAZANIN,false)){
            binding.txtWebAddress.setTypeface(typeface_nazanin);
            binding.txtAddress.setTypeface(typeface_nazanin);
            binding.btnNewNote.setTypeface(typeface_nazanin);
        }else if (sharedPreferences.getBoolean(FONT_CHILD,false)){
            binding.txtWebAddress.setTypeface(typeface_child);
            binding.txtAddress.setTypeface(typeface_child);
            binding.btnNewNote.setTypeface(typeface_child);
        }else if (sharedPreferences.getBoolean(FONT_TITR,false)){
            binding.txtWebAddress.setTypeface(typeface_titr);
            binding.txtAddress.setTypeface(typeface_titr);
            binding.btnNewNote.setTypeface(typeface_titr);
        }

    }


}