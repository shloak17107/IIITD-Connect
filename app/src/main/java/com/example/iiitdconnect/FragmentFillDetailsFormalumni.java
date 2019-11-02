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
import java.util.Calendar;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FragmentFillDetailsFormalumni extends Fragment {
    private ImageButton camerabutton ;
    ImageView img;
    private Button Save;
    Spinner degreespinner;
    Spinner statusspinner;
    ImageButton click;
    EditText date;
    int year,month,day;
    private TextView tags;

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
    Spinner currentStatus;
    EditText instituteCompany;
    private ArrayList<String> postTags;

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
        statusspinner=view.findViewById(R.id.editstatus1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.statusarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        statusspinner.setAdapter(adapter);
        degreespinner=view.findViewById(R.id.editbranch1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.degreesarray, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        degreespinner.setAdapter(adapter1);

        camerabutton = (ImageButton) view.findViewById(R.id.camera1);
        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryorcamera(getActivity());
            }
        });

        click=(ImageButton)view.findViewById(R.id.datebutton1);
        date=(EditText)view.findViewById(R.id.editdob1);
        date.setEnabled(false);
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


        tags = (TextView) view.findViewById(R.id.addtag1);
//        tags.setEnabled(false);

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

        Save = (Button)view.findViewById(R.id.button1);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( Branch.getSelectedItem().toString().equals("Select Branch*") || ContactNumber.getText().toString().equals("") || yearOfPassing.getText().toString().equals("")){
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

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://iiitd-connect-73dc0.appspot.com");
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Branch = (Spinner) view.findViewById(R.id.editbranch1);
        ContactNumber = (EditText) view.findViewById(R.id.editcontact1);
        dateOfBirth = (EditText) view.findViewById(R.id.editdob1);
        LinkedIn = (EditText) view.findViewById(R.id.editlinkdin1);
        webPage = (EditText) view.findViewById(R.id.editwebsite1);
        yearOfPassing = (EditText) view.findViewById(R.id.edityearofpass1);
        currentStatus = (Spinner) view.findViewById(R.id.editstatus1);
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
        String branch = Branch.getSelectedItem().toString();
        String contact = ContactNumber.getText().toString();
        String dob = dateOfBirth.getText().toString();
        String linkedIn = LinkedIn.getText().toString();
        String webpage = webPage.getText().toString();
        String yop = yearOfPassing.getText().toString();
        String status = currentStatus.getSelectedItem().toString();
        String company = instituteCompany.getText().toString();


        String id = email.substring(0, email.indexOf("@"));

        Alumni newAlumni = new Alumni(name, branch, contact, dob, linkedIn, webpage, yop, status, company, postTags);
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
                boolean result = Utility.checkPermission(getActivity());
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
