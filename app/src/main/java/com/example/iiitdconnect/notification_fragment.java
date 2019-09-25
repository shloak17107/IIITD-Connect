package com.example.project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class notification_fragment extends Fragment {
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_notification, container, false);
       // RecyclerView notification_list= view.findViewById(R.id.notification_recycler);
        //notification_list.setLayoutManager(new(LinearLayoutManager(this)));
        String[] l={"java","c","python"};

        return view;
    }



}
