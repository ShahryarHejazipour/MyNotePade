package com.shahryar960103.mynotepade.views.contactUsFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shahryar960103.mynotepade.R;
import com.shahryar960103.mynotepade.databinding.FragmentContactUsBinding;

import java.util.Objects;


public class ContactUsFragment extends Fragment {

   FragmentContactUsBinding binding;
   private static final String PHONE_NUMBER = "+982188946636";
   private static final String EMAIL = "shahryar.hejazipour90@gmail.com";

   SharedPreferences preferences;
    String myPref = "Save", SIZE = "size", FONT_NAZANIN = "nazanin"
            , FONT_CHILD = "child", FONT_TITR = "titr",FONT_MJ = "mj";
    private static final int BASE_FONT_SIZE = 16;

    public ContactUsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactUsBinding.inflate(getLayoutInflater(), container, false);


        //set font size to texts
        runSetting();



        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar2);
        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections = ContactUsFragmentDirections.actionContactUsFragmentToHomeFragment();
                Navigation.findNavController(binding.imageBack).navigate(navDirections);
            }
        });

        binding.btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections = ContactUsFragmentDirections.actionContactUsFragmentToAddNoteFragment();
                Navigation.findNavController(binding.btnNewNote).navigate(navDirections);
            }
        });

        binding.txtEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",EMAIL, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });
        binding.txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+PHONE_NUMBER));
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }

    private void runSetting(){

        //definition typefaces for access to fonts
        Typeface typeface_nazanin = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BNAZANB.TTF");
        Typeface typeface_titr = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BTITRBD.TTF");
        Typeface typeface_child = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/BKOODAKO.TTF");
        Typeface typeface_mj_Nil = Typeface.createFromAsset(this.getActivity().getAssets(),"fonts/Mj_Nil 3.ttf");

        preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(myPref,0);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        binding.txtEmailAddress.setTextSize(preferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.btnNewNote.setTextSize(preferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.txtPhone.setTextSize(preferences.getInt(SIZE,BASE_FONT_SIZE));

        if (preferences.getBoolean(FONT_NAZANIN,false)){
            binding.txtEmailAddress.setTypeface(typeface_nazanin);
            binding.txtPhone.setTypeface(typeface_nazanin);
            binding.btnNewNote.setTypeface(typeface_nazanin);
        }else if (preferences.getBoolean(FONT_CHILD,false)){
            binding.txtEmailAddress.setTypeface(typeface_child);
            binding.txtPhone.setTypeface(typeface_child);
            binding.btnNewNote.setTypeface(typeface_child);
        }else if (preferences.getBoolean(FONT_MJ,false)){
            binding.txtEmailAddress.setTypeface(typeface_mj_Nil);
            binding.txtPhone.setTypeface(typeface_mj_Nil);
            binding.btnNewNote.setTypeface(typeface_mj_Nil);
        }else if (preferences.getBoolean(FONT_TITR,false)){
            binding.txtEmailAddress.setTypeface(typeface_titr);
            binding.txtPhone.setTypeface(typeface_titr);
            binding.btnNewNote.setTypeface(typeface_titr);
        }




    }
}