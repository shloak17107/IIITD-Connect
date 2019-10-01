package com.example.iiitdconnect.ui.aboutus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutusViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is about us fragment constructor, in AboutusViewModel.java");
    }

    public LiveData<String> getText() {
        return mText;
    }
}