package com.example.iiitdconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;


public class CreatePostFrag extends Fragment {

    private Button postButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText title, body, tags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        postButton = (Button) view.findViewById(R.id.save);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        title = (EditText) view.findViewById(R.id.title);
        body = (EditText) view.findViewById(R.id.body);
        tags = (EditText) view.findViewById(R.id.tags);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPost();
            }
        });

        return view;
    }


    public void uploadPost(){
        String post_title = title.getText().toString();
        String post_body = body.getText().toString();
        String post_tags = tags.getText().toString();

        String email = mAuth.getCurrentUser().getEmail().toString();

        String id = email.substring(0, email.indexOf("@"));

        ArrayList<String> temptags = new ArrayList<>();
        temptags.add("job");
        temptags.add("post grad");
        temptags.add("alumni");

        Post post = new Post(email, post_title, post_body, temptags);
        mDatabase.child("Post").child(post.getTimestamp().replace("-", ":").replace(".", ":")).setValue(post);
        Toast.makeText(getActivity(), "New Post Created!", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }
}
