package com.example.iiitdconnect;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FragmentFillDetailsFormfaculty extends Fragment {
    private ImageButton camerabutton ;
    ImageView img;
    private Button Save;
    Spinner departmentspinner;

    StorageReference storageReference2nd;
    Uri FilePathUri;
    StorageReference storageReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView tags;
    private ArrayList<String> postTags;

    Spinner Department;
    EditText Expertise;
    EditText webpage;
    EditText LinkedIn;

//    EditText Branch;
    String Storage_Path = "images/";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_fill_details_formfaculty, container, false);
        img=view.findViewById(R.id.photo2);
        departmentspinner=view.findViewById(R.id.editdepartment2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.departmentarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        departmentspinner.setAdapter(adapter);
        camerabutton = (ImageButton)view.findViewById(R.id.camera2);
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryorcamera(getActivity());
            }
        });
        Save = (Button)view.findViewById(R.id.button2);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("abc",departmentspinner.getSelectedItem().toString());
                if( departmentspinner.getSelectedItem().toString().equals("Select Department*")){
                    Toast toast = Toast.makeText(getContext(),
                            "Please fill the essential details",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
                else {
                    UploadUserData();
                    UploadImageFileToFirebaseStorage();
                    Intent i = new Intent(getActivity(), Feed.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        });

        tags = (TextView) view.findViewById(R.id.addtag2);

        postTags = new ArrayList<String>();
        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTags = new ArrayList<String>();
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Tags");

// Add a checkbox list
                final String[] choices = getResources().getStringArray(R.array.allTags);
                final boolean[] checkedItems =new boolean[choices.length];
                builder.setMultiChoiceItems(choices, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // The user checked or unchecked a box
                        if(isChecked){
                            if(!postTags.contains(choices[which])){
                                postTags.add(choices[which]);
                            }
                        }
                        else{
                            if(postTags.contains(choices[which])){
                                postTags.add(choices[which]);
                            }
                        }
                    }
                });

// Add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // The user clicked OK
                        StringBuilder tagsshow = new StringBuilder();
                        for (String t : postTags){
                            tagsshow.append("'").append(t.replace("'", "\\'")).append("',");
                        }
                        if (tagsshow.length()>0){
                            tagsshow.deleteCharAt(tagsshow.length() - 1);
                        }
                        tags.setText(tagsshow.toString());
                    }
                });
                builder.setNegativeButton("Cancel", null);

// Create and show the alert dialog
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://iiitd-connect-73dc0.appspot.com");
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Department = (Spinner) view.findViewById(R.id.editdepartment2);
        Expertise = (EditText) view.findViewById(R.id.editexpertise2);
        LinkedIn = (EditText) view.findViewById(R.id.editlinkdin2);
        webpage = (EditText) view.findViewById(R.id.editwebsite2);

//        Branch = (EditText) view.findViewById(R.id.editbranch1);
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        FilePathUri = data.getData();
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        img.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        FilePathUri = data.getData();
                        Bitmap im = null;//MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        if (data != null) {
                            try {
                                im = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        img.setImageBitmap(im);
                    }
                    break;
            }

        }
    }

    public void UploadUserData() {
        String email = RegistrationActivity.email;
        String name = RegistrationActivity.name;
        String dept = Department.getSelectedItem().toString();
        String expt = Expertise.getText().toString();
        String linkedin = LinkedIn.getText().toString();
        String web = webpage.getText().toString();
        String id = email.substring(0, email.indexOf("@"));




        Faculty newFaculty = new Faculty(name, dept, expt, web, linkedin, postTags);
        mDatabase.child("Faculty").child(id).setValue(newFaculty);
//        Toast.makeText(getActivity(), "Alumni Details Saved!", Toast.LENGTH_SHORT).show();
    }

    public void UploadImageFileToFirebaseStorage() {
        if (FilePathUri != null) {
//            progressDialog.setTitle("Updating Profile Picture...");
//            progressDialog.show();
            storageReference2nd = storageReference.child(Storage_Path + mAuth.getCurrentUser().getEmail().toString());
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getActivity(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

//                            @SuppressWarnings("VisibleForTests")
//                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempImageName, taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
//                            updateUI(mAuth.getCurrentUser());
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
//                            progressDialog.setTitle("Updating Profile Picture...");

                        }
                    });
        }
        else {

            Toast.makeText(getActivity(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    private void galleryorcamera(Context context) {
        final CharSequence[] options = {"Use Camera", "Choose From Gallery", "Cancel"};

        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setTitle("Choose Profile Picture");

        alertbox.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(getActivity());
                if (options[item].equals("Use Camera")) {
                    if(result) {
                        Intent capture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(capture, 0);
                    }
                } else if (options[item].equals("Choose From Gallery")) {
                    if(result) {
                        Intent pickfromgallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickfromgallery, 1);
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        alertbox.show();
    }
}
