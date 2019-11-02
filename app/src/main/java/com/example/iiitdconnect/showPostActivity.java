package com.example.iiitdconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class showPostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Post post;
    private String createdBy, title, body;
    private String timestamp;
    private categories category;
    private String date;
    private String time;
    private String venue;
    private interested interestedpeople;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        this.createdBy = i.getStringExtra("createdBy");
        this.title = i.getStringExtra("title");
        this.body = i.getStringExtra("body");
        this.timestamp = i.getStringExtra("timestamp");
        this.date = i.getStringExtra("date");
        this.time = i.getStringExtra("time");
        this.venue = i.getStringExtra("venue");
        Map<String, String> cat = (Map) i.getSerializableExtra("cat");
        Map<String, String> inter = (Map) i.getSerializableExtra("interested");
        ArrayList<String> tt = new ArrayList<>();
        for(String s: cat.keySet()){
            tt.add(s);
        }
        this.post = new Post(createdBy, title, body, tt, date, time, venue);
        this.post.interestedpeople = new interested(inter);
        this.post.setTimestamp(this.timestamp);

        setContentView(R.layout.activity_show_post);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_post_show);
        if (fragment == null) {
            fragment = new ShowPost();
            fm.beginTransaction()
                    .add(R.id.fragment_post_show, fragment)
                    .commit();
        }
    }

    public Post getPost(){
        return this.post;
    }
}
