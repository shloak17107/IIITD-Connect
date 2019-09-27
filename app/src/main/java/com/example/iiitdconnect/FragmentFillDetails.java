package com.example.iiitdconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class FragmentFillDetails extends Fragment {

    private Button fill_student;
    private Button fill_alumni;
    private Button fill_faculty;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fragment_fill_details, container, false);
        fill_student = (Button) v.findViewById(R.id.fill_details_student);
        fill_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = RegistrationActivity.fragmentManager.findFragmentById(R.id.fragment_fill_details);
                if(fragment==null) {
                    RegistrationActivity.fragmentManager.beginTransaction().add(R.id.fragment_fill_details, new FragmentFillDetailsFormstudent(), null).commit();
                }
                else{
                    RegistrationActivity.fragmentManager.beginTransaction().remove(fragment).commit();
                    RegistrationActivity.fragmentManager.beginTransaction().add(R.id.fragment_fill_details, new FragmentFillDetailsFormstudent(), null).commit();
                }
            }
        });
        fill_alumni = (Button) v.findViewById(R.id.fill_details_alumni);
        fill_alumni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = RegistrationActivity.fragmentManager.findFragmentById(R.id.fragment_fill_details);
                if(fragment==null) {
                    RegistrationActivity.fragmentManager.beginTransaction().add(R.id.fragment_fill_details, new FragmentFillDetailsFormalumni(), null).commit();
                }
                else{
                    RegistrationActivity.fragmentManager.beginTransaction().remove(fragment).commit();
                    RegistrationActivity.fragmentManager.beginTransaction().add(R.id.fragment_fill_details, new FragmentFillDetailsFormalumni(), null).commit();
                }
            }
        });
        fill_faculty = (Button) v.findViewById(R.id.fill_details_faculty);
        fill_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = RegistrationActivity.fragmentManager.findFragmentById(R.id.fragment_fill_details);
                if(fragment==null) {
                    RegistrationActivity.fragmentManager.beginTransaction().add(R.id.fragment_fill_details, new FragmentFillDetailsFormfaculty(), null).commit();
                }
                else{
                    RegistrationActivity.fragmentManager.beginTransaction().remove(fragment).commit();
                    RegistrationActivity.fragmentManager.beginTransaction().add(R.id.fragment_fill_details, new FragmentFillDetailsFormfaculty(), null).commit();
                }
            }
        });

        return v;

    }

}
