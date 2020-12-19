package com.shahryar960103.mynotepade.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.shahryar960103.mynotepade.R;
import com.shahryar960103.mynotepade.database.NoteDataBase;
import com.shahryar960103.mynotepade.databinding.RowNoteBinding;
import com.shahryar960103.mynotepade.models.Note;
import com.shahryar960103.mynotepade.views.homeFragment.HomeFragmentDirections;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter {

    RowNoteBinding binding;
    SharedPreferences sharedPreferences;
    String myPref = "Save", SIZE = "size", FONT_NAZANIN = "nazanin"
            , FONT_CHILD = "child", FONT_TITR = "titr",FONT_MJ = "mj";
    private static final int BASE_FONT_SIZE = 16;

    Context context;
    List<Note> noteList;
    public NoteAdapter(Context context,List<Note> noteList){
        this.context = context;
        this.noteList = noteList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_note,parent,false);

        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Note note = noteList.get(position);
        binding.setNote(note);

        binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.deleting_this_note)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NoteDataBase noteDataBase = NoteDataBase.getInstance(context);
                                //noteDataBase.getDAO().deleteNote(note);
                                noteDataBase.getDAO().deleteANote(note);
                                noteList.remove(holder.getAdapterPosition());
                                //notifyItemRangeChanged(holder.getAdapterPosition(),noteList.size());
                                notifyItemRemoved(holder.getAdapterPosition());


                            }

                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alert.setCancelable(true);
                            }
                        }).setIcon(R.drawable.ic_baseline_delete_forever_24).setCancelable(false).setMessage(R.string.ask_for_delete_one_note);


                alert.show();




            }
        });
        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
               // intent.putExtra(Intent.EXTRA_TEXT,note.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT,note.getDescription());
                intent.setType("text/plain");
                context.startActivity(intent);
            }
        });
        binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToNoteFragment();

                Bundle bundle = new Bundle();
               bundle.putString("title",note.getTitle());
                bundle.putString("description",note.getDescription());
                bundle.putString("time",note.getTimeNote());
                bundle.putString("date",note.getDateNote());

                //getTitle(binding.txtTitle,note.getTitle());

                Navigation.findNavController(binding.rootLayout).navigate(R.id.action_homeFragment_to_noteFragment,bundle);

            }
        });
        runSetting();



    }


    private void runSetting(){

        //definition typefaces for access to fonts
        Typeface typeface_nazanin = Typeface.createFromAsset(context.getAssets(),"fonts/BNAZANB.TTF");
        Typeface typeface_titr = Typeface.createFromAsset(context.getAssets(),"fonts/BTITRBD.TTF");
        Typeface typeface_child = Typeface.createFromAsset(context.getAssets(),"fonts/BKOODAKO.TTF");
        Typeface typeface_mj_Nil = Typeface.createFromAsset(context.getAssets(),"fonts/Mj_Nil 3.ttf");

        sharedPreferences = context.getSharedPreferences(myPref,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(FONT_NAZANIN,false)){
            binding.lblDate.setTypeface(typeface_nazanin);
            binding.txtDate.setTypeface(typeface_nazanin);
            binding.lblTime.setTypeface(typeface_nazanin);
            binding.txtTime.setTypeface(typeface_nazanin);
            binding.lblDescription.setTypeface(typeface_nazanin);
            binding.txtDescription.setTypeface(typeface_nazanin);
            binding.txtTitle.setTypeface(typeface_nazanin);
            binding.lblTime.setTypeface(typeface_nazanin);
        }else if (sharedPreferences.getBoolean(FONT_CHILD,false)){
            binding.lblDate.setTypeface(typeface_child);
            binding.txtDate.setTypeface(typeface_child);
            binding.lblTime.setTypeface(typeface_child);
            binding.txtTime.setTypeface(typeface_child);
            binding.lblDescription.setTypeface(typeface_child);
            binding.txtDescription.setTypeface(typeface_child);
            binding.txtTitle.setTypeface(typeface_child);
            binding.lblTime.setTypeface(typeface_child);
        }else if (sharedPreferences.getBoolean(FONT_MJ,false)){
            binding.lblDate.setTypeface(typeface_mj_Nil);
            binding.txtDate.setTypeface(typeface_mj_Nil);
            binding.lblTime.setTypeface(typeface_mj_Nil);
            binding.txtTime.setTypeface(typeface_mj_Nil);
            binding.lblDescription.setTypeface(typeface_mj_Nil);
            binding.txtDescription.setTypeface(typeface_mj_Nil);
            binding.txtTitle.setTypeface(typeface_mj_Nil);
            binding.lblTime.setTypeface(typeface_mj_Nil);
        }else if (sharedPreferences.getBoolean(FONT_TITR,false)){
            binding.lblDate.setTypeface(typeface_titr);
            binding.txtDate.setTypeface(typeface_titr);
            binding.lblTime.setTypeface(typeface_titr);
            binding.txtTime.setTypeface(typeface_titr);
            binding.lblDescription.setTypeface(typeface_titr);
            binding.txtDescription.setTypeface(typeface_titr);
            binding.txtTitle.setTypeface(typeface_titr);
            binding.lblTime.setTypeface(typeface_titr);
        }

       /* sharedPreferences = context.getSharedPreferences(myPref,0);
        binding.lblDate.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.txtDate.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.lblTime.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.txtTime.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.lblDescription.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.lblTitle.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.txtDescription.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));
        binding.txtTitle.setTextSize(sharedPreferences.getInt(SIZE,BASE_FONT_SIZE));*/

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        RowNoteBinding binding;

        public NoteViewHolder(RowNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
