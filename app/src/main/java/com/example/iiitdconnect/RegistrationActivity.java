package com.example.iiitdconnect;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RegistrationActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static String email, name;
    public static StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        name = intent.getStringExtra("Name");

        storageReference = FirebaseStorage.getInstance().getReference();

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_fill_button);

        if (fragment == null) {
            fragment = new FragmentFillDetails();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_fill_button, fragment)
                    .commit();
        }

    }
}
