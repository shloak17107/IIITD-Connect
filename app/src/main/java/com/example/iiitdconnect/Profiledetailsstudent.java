package com.example.iiitdconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Profiledetailsstudent extends Fragment {

    StorageReference storageReference2nd;
    Uri FilePathUri;
    StorageReference storageReference;
    String Storage_Path = "images/";
    CircularImageView image;
    private FirebaseAuth mAuth;


    private Button Edit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_details_student, container, false);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://iiitd-connect-73dc0.appspot.com");
        image = v.findViewById(R.id.photodetails);
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail().toString();
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

        Edit = (Button) v.findViewById(R.id.Editdetails);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Profilechange.class);
                startActivity(intent);

            }
        });
        return v;
    }

}
