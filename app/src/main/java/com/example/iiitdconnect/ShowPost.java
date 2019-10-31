package com.example.iiitdconnect;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public ShowPost(Post post){
        this.post = post;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_post, container, false);
        b=(Button)view.findViewById(R.id.interested);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Post p = this.post;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mAuth.getCurrentUser().getEmail().toString();
                String id = email.substring(0, email.indexOf("@"));
                if(buttoncounter % 2 == 0){
//                    b.setBackgroundDrawable(getResources().getDrawable(R.drawable.bluearrowpaint));
                    buttoncounter++;
                    Map<String, String> mm = p.getInterestedpeople().getInterested_ids();
                    mm.put(id, "");
                    p.setInterestedpeople(new interested(mm));
                    mDatabase.child("Post").child(p.getTimestamp().replace("-", ":").replace(".", ":")).setValue(p);
                }
                else{
//                    b.setBackgroundDrawable(getResources().getDrawable(R.drawable.uparrowpaint));
                    buttoncounter++;
                    Map<String, String> mm = p.getInterestedpeople().getInterested_ids();
                    mm.remove(id);
                    p.setInterestedpeople(new interested(mm));
                    mDatabase.child("Post").child(p.getTimestamp().replace("-", ":").replace(".", ":")).setValue(p);
                }
            }
        });
        return view;
    }

}
