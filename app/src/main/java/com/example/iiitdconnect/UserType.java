package com.example.iiitdconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class UserType extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        String s = getIntent().getExtras().getString("abc");
        if(s.equals("student")){
            fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_registration2);
            if (fragment == null) {
                fragment = new FragmentFillDetailsFormstudent();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_registration2, fragment)
                        .commit();
            }        }
        else if(s.equals("alumni")){
            fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_registration2);
            if (fragment == null) {
                fragment = new FragmentFillDetailsFormalumni();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_registration2, fragment)
                        .commit();
            }        }
        else if(s.equals("faculty")){
            fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_registration2);
            if (fragment == null) {
                fragment = new FragmentFillDetailsFormfaculty();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_registration2, fragment)
                        .commit();
            }        }

    }
}
