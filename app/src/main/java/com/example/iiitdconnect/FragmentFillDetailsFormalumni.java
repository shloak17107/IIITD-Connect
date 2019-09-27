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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FragmentFillDetailsFormalumni extends Fragment {
    private Button camerabutton ;
    ImageView img;
    private Button Save;

    StorageReference storageReference2nd;
    Uri FilePathUri;
    StorageReference storageReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText Branch;
    EditText ContactNumber;
    EditText dateOfBirth;
    EditText LinkedIn;
    EditText webPage;
    EditText yearOfPassing;
    EditText currentStatus;
    EditText instituteCompany;

    String Storage_Path = "images/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_fill_details_formalumni, container, false);
        img=view.findViewById(R.id.photo1);
        camerabutton = (Button)view.findViewById(R.id.camera1);
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryorcamera(getActivity());
            }
        });
        Save = (Button)view.findViewById(R.id.button1);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImageFileToFirebaseStorage();
                UploadUserData();
            }
        });

        storageReference = RegistrationActivity.storageReference;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Branch = (EditText) view.findViewById(R.id.editbranch1);
        ContactNumber = (EditText) view.findViewById(R.id.editcontact1);
        dateOfBirth = (EditText) view.findViewById(R.id.editdob1);
        LinkedIn = (EditText) view.findViewById(R.id.editlinkdin1);
        webPage = (EditText) view.findViewById(R.id.editwebsite1);
        yearOfPassing = (EditText) view.findViewById(R.id.edityearofpass1);
        currentStatus = (EditText) view.findViewById(R.id.editstatus1);
        instituteCompany = (EditText) view.findViewById(R.id.editcompany1);

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
                        Bitmap im = null;//MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        if (data != null) {
                            try {
                                FilePathUri = data.getData();
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
        String branch = Branch.getText().toString();
        String contact = ContactNumber.getText().toString();
        String dob = dateOfBirth.getText().toString();
        String linkedIn = LinkedIn.getText().toString();
        String webpage = webPage.getText().toString();
        String yop = yearOfPassing.getText().toString();
        String status = currentStatus.getText().toString();
        String company = currentStatus.getText().toString();


        String id = email.substring(0, email.indexOf("@"));
        Alumni newAlumni = new Alumni(name, branch, contact, dob, linkedIn, webpage, yop, status, company);
        mDatabase.child("Alumni").child(id).setValue(newAlumni);
        Toast.makeText(getActivity(), "Alumni Details Saved!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

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
        final CharSequence[] options = {"Use Photo", "Choose From Gallery", "Cancel"};

        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setTitle("Choose Profile Picture");

        alertbox.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());
                if (options[item].equals("Use Camera")) {
                    // if(result) {
                    Intent capture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(capture, 0);

                    //}
                } else if (options[item].equals("Choose From Gallery")) {
                    //   if(result) {
                    Intent pickfromgallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickfromgallery, 1);

                    // }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        alertbox.show();
    }
}
