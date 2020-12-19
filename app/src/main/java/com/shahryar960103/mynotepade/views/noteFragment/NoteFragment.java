package com.shahryar960103.mynotepade.views.noteFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shahryar960103.mynotepade.R;
import com.shahryar960103.mynotepade.databinding.FragmentNoteBinding;


public class NoteFragment extends Fragment {

    FragmentNoteBinding binding;


    public NoteFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentNoteBinding.inflate(getLayoutInflater(),container,false);

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.imageBack).navigate(R.id.action_noteFragment_to_homeFragment);
            }
        });

        binding.txtTitle.setText(getArguments().getString("title"));
        binding.txtDate.setText(getArguments().getString("date"));
        binding.txtTime.setText(getArguments().getString("time"));
        binding.txtDescription.setText(getArguments().getString("description"));


        return binding.getRoot();
    }
}