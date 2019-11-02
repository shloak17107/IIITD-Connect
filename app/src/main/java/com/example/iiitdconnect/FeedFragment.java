package com.example.iiitdconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class FeedFragment extends Fragment {

    private FeedViewModel feedViewModel;
    private FirebaseAuth mAuth;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private static categories interests;
    public static int type;
    public static Student currentStudent;
    public static Alumni currentAlumni;
    public static Faculty currentFaculty;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        feedViewModel =
                ViewModelProviders.of(this).get(FeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        String email = mAuth.getCurrentUser().getEmail().toString();
        String id = email.substring(0, email.indexOf("@"));
        check(id, email);

        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Post");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Post> posts = new ArrayList<>();
                ArrayList<Post> temp = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Post post = data.getValue(Post.class);
                    Map<String, String> postCategories = post.getCategory().getCategories();
//                    Log.d("tags_size", Integer.toString(interests.getCategories().size()));
                    boolean flag = false;
                    for(Map.Entry<String, String> e: interests.getCategories().entrySet()) {
                        if (postCategories.containsKey(e.getKey())) {
                            posts.add(post);
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        temp.add(post);
                    }
                }
                Collections.reverse(posts);
                Collections.reverse(temp);
                Log.d("message", Integer.toString(posts.size()));
                for(Post e: temp){
                    posts.add(e);
                }
                adapter = new CustomAdapter(posts);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        return root;
    }

    public void Refresh (){
        Intent intent = getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void check(String x, String y){
        final String id = x;
        final String email = y;
        mDatabase.child("Student").orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    type = 1;
                    Student user = dataSnapshot.child(id).getValue(Student.class);
                    currentStudent = user;
                    interests = user.getCategory();

                } else {
                    mDatabase.child("Alumni").orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                type = 2;
                                Alumni user = dataSnapshot.child(id).getValue(Alumni.class);
                                currentAlumni = user;
                                interests = user.getCategory();
                            } else {
                                mDatabase.child("Faculty").orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()) {
                                            type = 3;
                                            Faculty user = dataSnapshot.child(id).getValue(Faculty.class);
                                            currentFaculty = user;
                                            interests = user.getCategory();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.d("MSG", "cancelled");
                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("MSG", "cancelled");
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MSG", "cancelled");
            }
        });
    }

}