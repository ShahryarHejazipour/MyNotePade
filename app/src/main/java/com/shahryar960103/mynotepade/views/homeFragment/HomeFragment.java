package com.shahryar960103.mynotepade.views.homeFragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.shahryar960103.mynotepade.R;
import com.shahryar960103.mynotepade.adapter.NoteAdapter;
import com.shahryar960103.mynotepade.config.Constants;
import com.shahryar960103.mynotepade.database.NoteDataBase;
import com.shahryar960103.mynotepade.databinding.FragmentHomeBinding;
import com.shahryar960103.mynotepade.models.Note;
import com.shahryar960103.mynotepade.viewmodel.NoteViewModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;




public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    ActionBarDrawerToggle toggle;

    NoteDataBase noteDataBase;

    NoteViewModel viewModel;



    List<Note> noteList;
    SharedPreferences sharedPreferences;
    String myPref = "Save", SIZE = "size";
    private static final int BASE_FONT_SIZE = 16;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Doing dataBinding for this Fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);

        //Adding Toolbar to this Fragment
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar);

        //Attaching Toggle for Navigation View
        toggle = new ActionBarDrawerToggle(((AppCompatActivity)getActivity())
                ,binding.drawerLayout,binding.toolbar,R.string.open,R.string.close);
        toggle.syncState();

        //Managing Clicking On Navigation View's Items
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.item_settings:

                    NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToSettingsFragment();
                    NavHostFragment.findNavController(HomeFragment.this).navigate(navDirections);


                        break;

                    case R.id.item_share:
                        sharingApp();
                        break;

                    case R.id.item_exit:
                        Objects.requireNonNull(getActivity()).finishAffinity();
                        break;




                }


                return false;
            }
        });



        //this method will show the list of all notes in Room data base
        showNotesList();

        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment();
                Navigation.findNavController(binding.fabAddNote).navigate(R.id.action_homeFragment_to_addNoteFragment);
            }
        });


        //clicking on Edit Profile in Header for going to EditProfileFragment
        binding.navigationView.getHeaderView(0)
             .findViewById(R.id.txt_edit_profile).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToEditProfileFragment();
             NavHostFragment.findNavController(HomeFragment.this).navigate(navDirections);

         }
     });

        //set text in User name in header. this data is giving from EditProfileFragment
       /*AppCompatTextView appCompatTextView = binding.navigationView.getHeaderView(0).findViewById(R.id.text_username);
       appCompatTextView.setText(getArguments().getString("username"));*/

       



        binding.fabDeleteAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!noteDataBase.getDAO().getExistenceNotes().isEmpty()){
                    AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                    alert.setTitle(R.string.deleting_all_notes)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    noteDataBase = NoteDataBase.getInstance(getActivity());
                                    noteDataBase.getDAO().deleteAllNotes();
                                    showNotesList();
                                }

                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alert.setCancelable(true);
                                }
                            })
                            .setIcon(R.drawable.ic_baseline_delete_forever_24).setCancelable(false).setMessage(R.string.ask_for_delete);
                    alert.show();
                }else {
                    Snackbar.make(getView(),R.string.no_notes_for_delete,Snackbar.LENGTH_LONG).show();
                }

            }
        });



       /* //MVVM Architecture
        viewModel = new NoteViewModel();
        Observer<Note> observer = new Observer<Note>() {
            @Override
            public void onChanged(Note note) {

                showNotesList();
            }
        };

        viewModel.getNoteMutableLiveData().observe(HomeFragment.this,observer);*/


        return binding.getRoot();



    }




    private void showNotesList() {
        //Using Room dataBase

        //1- Creating an Object from  NoteDataBase class
        noteDataBase = NoteDataBase.getInstance(getActivity());

        //2- Creating a List for saving the list of notes from Room data base
        noteList = new ArrayList<>();


        //3-Calling DAO's Special method for a list of All notes in Room data base
        noteList = noteDataBase.getDAO().getAllNote();


        //4- Setting the RecyclerView with above noteList
        binding.recyclerView.setAdapter(new NoteAdapter(getActivity(),noteList));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));

        //End Room data base for recyclerView
    }

    private void sharingApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(Constants.SHARE_APP));
        startActivity(intent);
    }

    private void runSetting(){
        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(myPref,0);
        Menu menu = binding.navigationView.getMenu();
        //MenuItem menuItem = menu.findItem(R.id.item_share).
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.item_contactUs:
               NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToContactUsFragment();
                NavHostFragment.findNavController(this).navigate(navDirections);

                break;

            case  R.id.item_aboutUs:
                NavDirections navDirections1 = HomeFragmentDirections.actionHomeFragmentToAboutUsFragment();
                NavHostFragment.findNavController(this).navigate(navDirections1);
                break;

            case R.id.item_exit:
                ((AppCompatActivity) Objects.requireNonNull(getActivity())).finishAffinity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}