package com.example.iiitdconnect;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Profilechangefaculty extends Fragment {

    private Button Cancel;
    private Button Save;

    public boolean changed;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    ProgressDialog progressDialog ;
    StorageReference storageReference2nd;
    Uri FilePathUri;
    StorageReference storageReference;
    String Storage_Path = "images/";
    CircularImageView image;


    EditText Name;
    Spinner Department;
    EditText LinkedIn;
    EditText webPage;
    EditText expertise;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_change_faculty, container, false);
        changed = false;
        Save = (Button) v.findViewById(R.id.buttonchange2);

        Name = (EditText) v.findViewById(R.id.editnamechange2);
        Department = (Spinner) v.findViewById(R.id.editdepartmentchange2);
        LinkedIn = (EditText) v.findViewById(R.id.editlinkdinchange2);
        webPage = (EditText) v.findViewById(R.id.editwebsitechange2);
        expertise = (EditText) v.findViewById(R.id.editexpertisechange2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.departmentarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Department.setAdapter(adapter);

        Name.setText(FeedFragment.currentFaculty.getName());
        Department.setSelection(((ArrayAdapter)Department.getAdapter()).getPosition(FeedFragment.currentFaculty.getDepartment()));
        LinkedIn.setText(FeedFragment.currentFaculty.getLinkedIn());
        webPage.setText(FeedFragment.currentFaculty.getWebpage());
        expertise.setText(FeedFragment.currentFaculty.getExpertise());

        

//        Cancel = (Button) v.findViewById(R.id.buttoncancel2);
//        Cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://iiitd-connect-73dc0.appspot.com");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String email = mAuth.getCurrentUser().getEmail().toString();
        image = v.findViewById(R.id.photochange2);
        String id = email.substring(0, email.indexOf("@"));
        storageReference.child(Storage_Path + email).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                Glide.with(getActivity()).load(imageURL).into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadUserData();
                if(changed) {
                    UploadImageFileToFirebaseStorage();
                }

                Intent i = new Intent(getActivity(), Feed.class);
                i.addFlags(i.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().finish();
                startActivity(i);
            }
        });


        ImageButton camerabutton = (ImageButton)v.findViewById(R.id.camerachange2);
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryorcamera(getActivity());
            }
        });

        return v;
    }


    public void UploadUserData() {
        String email = mAuth.getCurrentUser().getEmail();

//        FeedFragment.currentStudent.setBranch(Branch.getSelectedItem().toString());
        FeedFragment.currentFaculty.setName(Name.getText().toString());
        FeedFragment.currentFaculty.setDepartment(Department.getSelectedItem().toString());
        FeedFragment.currentFaculty.setLinkedIn(LinkedIn.getText().toString());
        FeedFragment.currentFaculty.setWebpage(webPage.getText().toString());
        FeedFragment.currentFaculty.setExpertise(expertise.getText().toString());

        String id = email.substring(0, email.indexOf("@"));
        mDatabase.child("Faculty").child(id).setValue(FeedFragment.currentFaculty);
        Toast.makeText(getActivity(), "Student Details Saved!", Toast.LENGTH_SHORT).show();
    }


    public void UploadImageFileToFirebaseStorage() {
        if (FilePathUri != null) {
            storageReference2nd = storageReference.child(Storage_Path + mAuth.getCurrentUser().getEmail().toString());
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        }
        else {

            Toast.makeText(getActivity(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        FilePathUri = data.getData();
                        Log.d("IMAGEPATH", FilePathUri.toString());
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(selectedImage);
                        changed = true;
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        FilePathUri = data.getData();
                        Log.d("IMAGEPATH", FilePathUri.toString());
                        Bitmap im = null;//MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        if (data != null) {
                            try {
                                im = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        image.setImageBitmap(im);
                        changed = true;
                    }
                    break;
            }

        }
    }

    private void galleryorcamera(Context context) {
        final CharSequence[] options = {"Use Camera", "Choose From Gallery", "Cancel"};

        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setTitle("Choose Profile Picture");

        alertbox.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());
                if (options[item].equals("Use Camera")) {
                    //if(result) {
                    Intent capture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(capture, 0);
                    //}
                } else if (options[item].equals("Choose From Gallery")) {
                    //if(result) {
                    Intent pickfromgallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickfromgallery, 1);
                    //}
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        alertbox.show();
    }

}