package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.SignInButton;

public class Login_page_fragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.login_page_fragment_layout,container,false);
        return view;
//        (SignInButton) view.findViewById(R.id.sign_in_button).setOnClickListener();
    }
//    SignInButton signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
//    signInButton.ButtonSize(short);

}
