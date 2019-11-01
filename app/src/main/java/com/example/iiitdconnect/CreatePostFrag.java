package com.example.iiitdconnect;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class CreatePostFrag extends Fragment {

    private Button postButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText title, body, tagsField;
    private Button tagsButton;
    private TextView date;
    private TextView time;
    private ArrayList<String> postTags;
    int year,month,day;
    int selectedHour, selectedMinute;
    private EditText venue;

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
        date = (TextView) view.findViewById(R.id.Date);
        time = (TextView) view.findViewById(R.id.Time);
        venue = (EditText) view.findViewById(R.id.location);


        date.setOnClickListener(new View.OnClickListener() {
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

        time.setText( "" + selectedHour + ":" + selectedMinute);

        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        postTags = new ArrayList<String>();
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
        String post_date = date.getText().toString();
        String post_time = time.getText().toString();
        String post_venue = venue.getText().toString();

        String email = mAuth.getCurrentUser().getEmail().toString();

        String id = email.substring(0, email.indexOf("@"));

//        ArrayList<String> temptags = new ArrayList<>();
//        temptags.add("job");
//        temptags.add("post grad");
//        temptags.add("alumni");

        Post post = new Post(email, post_title, post_body, postTags, post_date, post_time, post_venue);
        mDatabase.child("Post").child(post.getTimestamp().replace("-", ":").replace(".", ":")).setValue(post);
        if(FeedFragment.type == 1){
            Map<String, String> xx = FeedFragment.currentStudent.getMyPosts();
            xx.put(post.getTimestamp().replace("-", ":").replace(".", ":"), "");
            FeedFragment.currentStudent.setMyPosts(xx);
            mDatabase.child("Student").child(id).setValue(FeedFragment.currentStudent);
        }else if(FeedFragment.type == 2){
            Map<String, String> xx = FeedFragment.currentAlumni.getMyPosts();
            xx.put(post.getTimestamp().replace("-", ":").replace(".", ":"), "");
            FeedFragment.currentAlumni.setMyPosts(xx);
            mDatabase.child("Alumni").child(id).setValue(FeedFragment.currentAlumni);
        }else if(FeedFragment.type == 3){
            Map<String, String> xx = FeedFragment.currentFaculty.getMyPosts();
            xx.put(post.getTimestamp().replace("-", ":").replace(".", ":"), "");
            FeedFragment.currentFaculty.setMyPosts(xx);
            mDatabase.child("Faculty").child(id).setValue(FeedFragment.currentFaculty);
        }
        Toast.makeText(getActivity(), "New Post Created!", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }
}
