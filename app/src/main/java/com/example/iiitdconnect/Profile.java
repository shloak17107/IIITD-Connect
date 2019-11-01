package com.example.iiitdconnect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Profile extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_profile);
        int type=FeedFragment.type;
        if(type==1) {
            if (fragment == null) {
                fragment = new Profiledetailsstudent();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_profile, fragment)
                        .commit();
            }
        }

    }
}
