package com.example.iiitdconnect;

import com.google.firebase.auth.FirebaseAuth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignoutViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private MutableLiveData<String> mText;

    public SignoutViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}