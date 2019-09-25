package com.example.iiitd_connect.ui.signout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignoutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SignoutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is signout fragment constructor in SignoutViewModel.java");
    }

    public LiveData<String> getText() {
        return mText;
    }
}