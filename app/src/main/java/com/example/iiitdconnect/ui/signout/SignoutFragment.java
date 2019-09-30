package com.example.iiitdconnect.ui.signout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iiitdconnect.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SignoutFragment extends Fragment {

    private SignoutViewModel signoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signoutViewModel =
                ViewModelProviders.of(this).get(SignoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signout, container, false);

        return root;
    }
}