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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FragmentFillDetailsFormstudent extends Fragment {

    private Button camerabutton ;

    private Button Save ;
    ImageView img;
    Spinner degreespinner;
    Button click;
    EditText date;
    int year,month,day;

    StorageReference storageReference2nd;
    Uri FilePathUri;
    StorageReference storageReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    Spinner Branch;
    EditText ContactNumber;
    EditText dateOfBirth;
    EditText LinkedIn;
    EditText webPage;
    EditText yearOfPassing;

    //    EditText Branch;
    String Storage_Path = "images/";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_fragment_fill_details_formstudent, container, false);
        img=view.findViewById(R.id.photo);
        degreespinner=view.findViewById(R.id.editbranch);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.degreesarray, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        degreespinner.setAdapter(adapter2);
        camerabutton = (Button)view.findViewById(R.id.camera);
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryorcamera(getActivity());
            }
        });

        click=(Button)view.findViewById(R.id.datebutton);
        date=(EditText)view.findViewById(R.id.editdob);
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
                        date.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },year,month,day);
                d.show();

            }
        });

        Save = (Button)view.findViewById(R.id.button);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadUserData();
                UploadImageFileToFirebaseStorage();
                Intent i = new Intent(getActivity(), Feed.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        storageReference = RegistrationActivity.storageReference;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Branch = (Spinner) view.findViewById(R.id.editbranch);
        ContactNumber = (EditText) view.findViewById(R.id.editcontact);
        dateOfBirth = (EditText) view.findViewById(R.id.editdob);
        LinkedIn = (EditText) view.findViewById(R.id.editlinkdin);
        webPage = (EditText) view.findViewById(R.id.editwebsite);
        yearOfPassing = (EditText) view.findViewById(R.id.edityearofpass);

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

        String branch = Branch.getSelectedItem().toString();
        String contact = ContactNumber.getText().toString();
        String dob = dateOfBirth.getText().toString();
        String linkedIn = LinkedIn.getText().toString();
        String webpage = webPage.getText().toString();
        String yop = yearOfPassing.getText().toString();

        String id = email.substring(0, email.indexOf("@"));
        Student newStudent = new Student(name, branch, contact, dob, linkedIn, webpage, yop);
        mDatabase.child("Student").child(id).setValue(newStudent);
        Toast.makeText(getActivity(), "Student Details Saved!", Toast.LENGTH_SHORT).show();
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
