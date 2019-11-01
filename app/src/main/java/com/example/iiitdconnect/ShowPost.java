package com.example.iiitdconnect;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowPost extends Fragment {

    Button b;
    int buttoncounter=0;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Post post;

    private TextView postTitle;
    private TextView postDescription;
    private TextView postLocation;
    private TextView postDate;
    private TextView postTime;

    public ShowPost(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        showPostActivity act = (showPostActivity) getActivity();
        this.post = act.getPost();
        View view = inflater.inflate(R.layout.fragment_show_post, container, false);
        b = (Button) view.findViewById(R.id.interested);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Post p = this.post;
        Log.d("AHAHHAHA", p.getTitle());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mAuth.getCurrentUser().getEmail().toString();
                String id = email.substring(0, email.indexOf("@"));
                if(buttoncounter % 2 == 0){
//                    b.setBackgroundDrawable(getResources().getDrawable(R.drawable.bluearrowpaint));
                   b.setBackgroundResource(R.drawable.bluearrowpaint);
                    buttoncounter++;
                    Map<String, String> mm = p.getInterestedpeople().getInterested_ids();
                    mm.put(id, "");
                    p.setInterestedpeople(new interested(mm));
                    mDatabase.child("Post").child(p.getTimestamp().replace("-", ":").replace(".", ":")).setValue(p);
                    Toast.makeText(getActivity(), "You are interested in this post!", Toast.LENGTH_SHORT).show();
                }
                else{
//                    b.setBackgroundDrawable(getResources().getDrawable(R.drawable.uparrowpaint));
                    b.setBackgroundResource(R.drawable.uparrowpaint);
                    buttoncounter++;
                    Map<String, String> mm = p.getInterestedpeople().getInterested_ids();
                    mm.remove(id);
                    p.setInterestedpeople(new interested(mm));
                    mDatabase.child("Post").child(p.getTimestamp().replace("-", ":").replace(".", ":")).setValue(p);
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



        return view;
    }

}
