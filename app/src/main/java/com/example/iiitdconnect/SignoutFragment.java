package com.example.iiitdconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SignoutFragment extends Fragment {

    private SignoutViewModel signoutViewModel;
    private FeedViewModel feedViewModel;
    private FirebaseAuth mAuth;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private static categories interests;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signoutViewModel =
                ViewModelProviders.of(this).get(SignoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signout, container, false);



        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        feedViewModel =
                ViewModelProviders.of(this).get(FeedViewModel.class);
        String email = mAuth.getCurrentUser().getEmail().toString();
        String id = email.substring(0, email.indexOf("@"));

        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view_myposts);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<String> postIds = new ArrayList<>();

        if(FeedFragment.type == 1){
            Map<String, String> map = FeedFragment.currentStudent.getMyPosts();
            for(String key: map.keySet()){
                postIds.add(key);
            }
        }else if(FeedFragment.type == 2){
            Map<String, String> map = FeedFragment.currentAlumni.getMyPosts();
            for(String key: map.keySet()){
                postIds.add(key);
            }
        }else{
            Map<String, String> map = FeedFragment.currentFaculty.getMyPosts();
            for(String key: map.keySet()){
                postIds.add(key);
            }
        }

        postIds.remove("temp_post");
        final ArrayList<String> postIds1 = postIds;
        final ArrayList<Post> posts = new ArrayList<>();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("Post");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Post post = data.getValue(Post.class);
                        if(postIds1.contains(post.getTimestamp().replace("-", ":").replace(".", ":"))) {
                            posts.add(post);
                        }
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
}


