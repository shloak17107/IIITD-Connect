package com.example.iiitdconnect;


import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.io.IOException;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Profilechangestudent extends Fragment {

    private Button Save;
    private Button Cancel;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public boolean changed;

    EditText Name;
    Spinner Branch;
    EditText ContactNumber;
    EditText dateOfBirth;
    EditText LinkedIn;
    EditText webPage;
    EditText yearOfPassing;

    int year,month,day;


    ProgressDialog progressDialog ;

    StorageReference storageReference2nd;
    Uri FilePathUri;
    StorageReference storageReference;
    String Storage_Path = "images/";
    CircularImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_change_student, container, false);
        changed = false;
        Save = (Button) v.findViewById(R.id.buttonchange);

        Name = v.findViewById(R.id.editnamechange);
        Branch = v.findViewById(R.id.editbranchchange);
        ContactNumber = v.findViewById(R.id.editcontactchange);
        dateOfBirth = v.findViewById(R.id.editdobchange);
        LinkedIn = v.findViewById(R.id.editlinkdinchange);
        webPage = v.findViewById(R.id.editwebsitechange);
        yearOfPassing = v.findViewById(R.id.edityearofpasschange);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.degreesarray, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Branch.setAdapter(adapter2);

        Name.setText(FeedFragment.currentStudent.getName());
        Branch.setSelection(((ArrayAdapter)Branch.getAdapter()).getPosition(FeedFragment.currentStudent.getBranch()));
        ContactNumber.setText(FeedFragment.currentStudent.getContactNumber());
        dateOfBirth.setText(FeedFragment.currentStudent.getDateOfBirth());
        LinkedIn.setText(FeedFragment.currentStudent.getLinkedIn());
        webPage.setText(FeedFragment.currentStudent.getWebpage());
        yearOfPassing.setText(FeedFragment.currentStudent.getYearOfPassing());


        mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://iiitd-connect-73dc0.appspot.com");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String email = mAuth.getCurrentUser().getEmail().toString();
        image = v.findViewById(R.id.photochange);
        storageReference.child(Storage_Path + email).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageURL = uri.toString();
                Glide.with(getActivity()).load(imageURL).into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        Save = v.findViewById(R.id.buttonchange);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadUserData();
                if(changed) {
                    UploadImageFileToFirebaseStorage();
                }

//                addSmallDelay();
            }
        });

        ImageButton click=(ImageButton)v.findViewById(R.id.datebuttonchange);
        dateOfBirth.setEnabled(false);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                year=cal.get(Calendar.YEAR);
                month=cal.get(Calendar.MONTH);
                day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateOfBirth.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },year,month,day);
                d.show();

            }
        });


        ImageButton camerabutton = (ImageButton)v.findViewById(R.id.camerachange);
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryorcamera(getActivity());
            }
        });


        return v;
    }


    public void addSmallDelay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getActivity(), Feed.class);
                i.addFlags(i.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().finish();
                startActivity(i);
            }
        }, 2000);
    }

    public void UploadUserData() {
        String email = mAuth.getCurrentUser().getEmail();

//        FeedFragment.currentStudent.setBranch(Branch.getSelectedItem().toString());
        FeedFragment.currentStudent.setName(Name.getText().toString());
        FeedFragment.currentStudent.setBranch(Branch.getSelectedItem().toString());
        FeedFragment.currentStudent.setContactNumber(ContactNumber.getText().toString());
        FeedFragment.currentStudent.setYearOfPassing(yearOfPassing.getText().toString());
        FeedFragment.currentStudent.setLinkedIn(LinkedIn.getText().toString());
        FeedFragment.currentStudent.setWebpage(webPage.getText().toString());
        FeedFragment.currentStudent.setDateOfBirth(dateOfBirth.getText().toString());

        String id = email.substring(0, email.indexOf("@"));
        mDatabase.child("Student").child(id).setValue(FeedFragment.currentStudent);
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