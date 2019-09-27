package com.example.iiitdconnect.ui.signout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.iiitdconnect.R;

public class SignoutFragment extends Fragment {

    private SignoutViewModel signoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signoutViewModel =
                ViewModelProviders.of(this).get(SignoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signout, container, false);
        final TextView textView = root.findViewById(R.id.text_signout);
        signoutViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}