package com.shahryar960103.mynotepade;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shahryar960103.mynotepade.EditProfileFragmentDirections;
import com.shahryar960103.mynotepade.databinding.FragmentEditProfileBinding;


import java.util.List;
import java.util.Objects;


public class EditProfileFragment extends Fragment {

    FragmentEditProfileBinding binding;
    private static final int CAMERA_REQUEST = 1;
    private static final int SELECT_PHOTO = 100;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater(),container,false);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar2);

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavDirections navDirections = EditProfileFragmentDirections.actionEditProfileFragmentToHomeFragment();
                NavHostFragment.findNavController(EditProfileFragment.this).navigate(navDirections);

            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("username",binding.edtUsername.getText().toString());

              //  NavDirections navDirections = EditProfileFragmentDirections.actionEditProfileFragmentToHomeFragment();


                NavHostFragment.findNavController(EditProfileFragment.this)
                        .navigate(R.id.action_editProfileFragment_to_homeFragment,bundle);


            }
        });

        //Click on Profile Image for Select an Image for Profile
        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Setting an AlertDialog for choice between Camera of Gallery for select a Profile Image
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage(R.string.select_profile_image).setTitle(R.string.where_select_profile_image)
                        .setCancelable(false)
                        .setPositiveButton(R.string.camera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Access Camera Permission with Dexter
                        Dexter.withContext(getActivity()).withPermission(Manifest.permission.CAMERA)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                        /*Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(cameraIntent,CAMERA_REQUEST);*/

                                       ImagePicker.cameraOnly().start(EditProfileFragment.this);




                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                        //Snackbar.make(view,R.string.access_denied_message_camera,Snackbar.LENGTH_LONG).show();
                                    }
                                }).check();



                    }
                }).setNegativeButton(R.string.gallery, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Dexter.withContext(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                               /* Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent,SELECT_PHOTO);*/

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                        });

                    }
                }).show();




            }
        });






        return binding.getRoot();


    }
}