package com.example.iiitdconnect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Profilechange extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilechange);
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_profilechange);
        int type=FeedFragment.type;
        if (type==1) {
            if (fragment == null) {
                fragment = new Profilechangestudent();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_profilechange, fragment)
                        .commit();
            }
        }
        else if(type==2){
            if (fragment == null) {
                fragment = new Profilechangealumni();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_profilechange, fragment)
                        .commit();
            }
        }
        else{
            if (fragment == null) {
                fragment = new Profilechangefaculty();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_profilechange, fragment)
                        .commit();
            }
        }
    }
}