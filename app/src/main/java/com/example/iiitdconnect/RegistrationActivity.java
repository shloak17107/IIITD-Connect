package com.example.iiitdconnect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RegistrationActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

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
