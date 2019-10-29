package com.example.iiitdconnect;

import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


public class CreatePostFrag extends Fragment {

    private Button postButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText title, body, tagsField;
    private Button tagsButton;
    private ArrayList<String> postTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        postButton = (Button) view.findViewById(R.id.save);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        title = (EditText) view.findViewById(R.id.title);
        body = (EditText) view.findViewById(R.id.body);
        tagsButton = (Button) view.findViewById(R.id.tagsButton);
        tagsField = (EditText) view.findViewById(R.id.tagsField);
        tagsField.setEnabled(false);

        tagsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTags = new ArrayList<String>();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        tagsField.setText(tagsshow.toString());
                    }
                });
                builder.setNegativeButton("Cancel", null);

// Create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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

        String email = mAuth.getCurrentUser().getEmail().toString();

        String id = email.substring(0, email.indexOf("@"));

//        ArrayList<String> temptags = new ArrayList<>();
//        temptags.add("job");
//        temptags.add("post grad");
//        temptags.add("alumni");

        Post post = new Post(email, post_title, post_body, postTags);
        mDatabase.child("Post").child(post.getTimestamp().replace("-", ":").replace(".", ":")).setValue(post);
        Toast.makeText(getActivity(), "New Post Created!", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }
}
