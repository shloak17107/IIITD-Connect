package com.example.iiitdconnect;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowPost extends Fragment {

    Button b;
    int buttoncounter=0;

    Button calender_save;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Post post;

    private TextView postTitle;
    private TextView postDescription;
    private TextView postLocation;
    private TextView postDate;
    StorageReference storageReference2nd;
    Uri FilePathUri;
    StorageReference storageReference;
    private TextView postTime;
    String Storage_Path = "images/";

    ImageView image;

    private static final String APPLICATION_NAME = "IIITD Connect";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private TextView owner;

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "./../../../../../../credentials.json";

    public ShowPost(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        showPostActivity act = (showPostActivity) getActivity();
        this.post = act.getPost();
        View view = inflater.inflate(R.layout.fragment_show_post, container, false);
        b = view.findViewById(R.id.interested);
        mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://iiitd-connect-73dc0.appspot.com");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Post p = this.post;
        String email = mAuth.getCurrentUser().getEmail().toString();

        owner = (TextView) view.findViewById(R.id.owner);
        owner.setText(p.getCreatedBy());

        image = (ImageView) view.findViewById(R.id.photodetails3);
        String Postid = p.getTimestamp().replace("-", ":").replace(".", ":");
        storageReference.child(Storage_Path + Postid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        Map<String, String> xx = this.post.getInterestedpeople().getInterested_ids();
        if(xx.containsKey(email.substring(0, email.indexOf("@")))){
            buttoncounter = 1;
            b.setText("NO INTEREST");
        }else{
            buttoncounter = 0;
            b.setText("SHOW INTEREST");
        }

        calender_save = view.findViewById(R.id.add_to_calendar);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mAuth.getCurrentUser().getEmail().toString();
                String id = email.substring(0, email.indexOf("@"));
                Log.d("TIMESTAMP", p.getTimestamp());
                if(buttoncounter % 2 == 0){
//                    b.setBackgroundDrawable(getResources().getDrawable(R.drawable.bluearrowpaint));
                    b.setText("NO INTEREST");
                    buttoncounter++;
                    Map<String, String> mm = p.getInterestedpeople().getInterested_ids();
                    mm.put(id, "");
                    p.setInterestedpeople(new interested(mm));
                    mDatabase.child("Post").child(p.getTimestamp().replace("-", ":").replace(".", ":")).setValue(p);

                    if(FeedFragment.type == 1){
                        Map<String, String> xx = FeedFragment.currentStudent.getInterestedPosts();
                        xx.put(post.getTimestamp().replace("-", ":").replace(".", ":"), "");
                        FeedFragment.currentStudent.setInterestedPosts(xx);
                        mDatabase.child("Student").child(id).setValue(FeedFragment.currentStudent);
                    }else if(FeedFragment.type == 2){
                        Map<String, String> xx = FeedFragment.currentAlumni.getInterestedPosts();
                        xx.put(post.getTimestamp().replace("-", ":").replace(".", ":"), "");
                        FeedFragment.currentAlumni.setInterestedPosts(xx);
                        mDatabase.child("Alumni").child(id).setValue(FeedFragment.currentAlumni);
                    }else if(FeedFragment.type == 3){
                        Map<String, String> xx = FeedFragment.currentFaculty.getInterestedPosts();
                        xx.put(post.getTimestamp().replace("-", ":").replace(".", ":"), "");
                        FeedFragment.currentFaculty.setInterestedPosts(xx);
                        mDatabase.child("Faculty").child(id).setValue(FeedFragment.currentFaculty);
                    }



                    Toast.makeText(getActivity(), "You are interested in this post!", Toast.LENGTH_SHORT).show();
                }
                else{
//                    b.setBackgroundDrawable(getResources().getDrawable(R.drawable.uparrowpaint));
                    buttoncounter++;
                    b.setText("SHOW INTEREST");
                    Map<String, String> mm = p.getInterestedpeople().getInterested_ids();
                    mm.remove(id);
                    p.setInterestedpeople(new interested(mm));
                    mDatabase.child("Post").child(p.getTimestamp().replace("-", ":").replace(".", ":")).setValue(p);

                    if(FeedFragment.type == 1){
                        Map<String, String> xx = FeedFragment.currentStudent.getInterestedPosts();
                        xx.remove(post.getTimestamp().replace("-", ":").replace(".", ":"));
                        FeedFragment.currentStudent.setInterestedPosts(xx);
                        mDatabase.child("Student").child(id).setValue(FeedFragment.currentStudent);
                    }else if(FeedFragment.type == 2){
                        Map<String, String> xx = FeedFragment.currentAlumni.getInterestedPosts();
                        xx.remove(post.getTimestamp().replace("-", ":").replace(".", ":"));
                        FeedFragment.currentAlumni.setInterestedPosts(xx);
                        mDatabase.child("Alumni").child(id).setValue(FeedFragment.currentAlumni);
                    }else if(FeedFragment.type == 3){
                        Map<String, String> xx = FeedFragment.currentFaculty.getInterestedPosts();
                        xx.remove(post.getTimestamp().replace("-", ":").replace(".", ":"));
                        FeedFragment.currentFaculty.setInterestedPosts(xx);
                        mDatabase.child("Faculty").child(id).setValue(FeedFragment.currentFaculty);
                    }


                    Toast.makeText(getActivity(), "You are not interested in this post!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String title = p.getTitle();
        String description = p.getBody();
        String location = p.getVenue();
        String date = p.getDate();
        String time = p.getTime();

        postTitle = (TextView) view.findViewById(R.id.post_title);
        postDescription = (TextView) view.findViewById(R.id.post_description);
        postLocation = (TextView) view.findViewById(R.id.post_location);
        postDate = (TextView) view.findViewById(R.id.post_date);
        postTime = (TextView) view.findViewById(R.id.post_time);

        if (!title.equals(""))
            postTitle.setText(title);
        if (!description.equals(""))
            postDescription.setText(description);
        if (!location.equals(""))
            postLocation.setText(location);
        if (!date.equals(""))
            postDate.setText(date);
        if (!time.equals(""))
            postTime.setText(time);

        final String TITLE = title;
        final String LOCATION = location;
        final String DESCRIPTION = description;
        final String DATE = date;

        calender_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HELLO", DATE);
                if (DATE.equals("")) {
                    Toast.makeText(getActivity(), "Post doesn't have date. Cannot add to calendar.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String[] ss = DATE.split("/");
                    String D = ss[0];
                    String M = ss[1];
                    String Y = ss[2];
                    if(D.length() == 1){
                        D = "0" + D;
                    }
                    if(M.length() == 1){
                        M = "0" + M;
                    }

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra(Events.TITLE, TITLE);
                    intent.putExtra(Events.EVENT_LOCATION, LOCATION);
                    intent.putExtra(Events.DESCRIPTION, DESCRIPTION);

                    // Setting dates

                    java.sql.Timestamp tsStart = java.sql.Timestamp.valueOf(Y+ "-" + M + "-" + D + " " + 05 + ":"+ 12 + ":00");
                    java.sql.Timestamp tsEnd = java.sql.Timestamp.valueOf(Y+ "-" + M + "-" + D + " " + 23+ ":"+ 59+ ":00");

                    long startTime = tsStart.getTime();
                    long endTime = tsEnd.getTime();

                    GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(Y), Integer.parseInt(M), Integer.parseInt(D));
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            startTime);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            endTime);

                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                    startActivity(intent);
                }
            }
        });




        return view;
    }


}
