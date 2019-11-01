package com.example.iiitdconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.iiitdconnect.ui.profile.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        int type=FeedFragment.type;
        Button Edit;
        if(type==1){
            Student student=FeedFragment.currentStudent;
            root = inflater.inflate(R.layout.fragment_profile_details_student, container, false);
            TextView name=root.findViewById(R.id.editnamedetails);
            TextView branch=root.findViewById(R.id.editbranchdetails);
            TextView contact=root.findViewById(R.id.editcontactdetails);
            TextView dob=root.findViewById(R.id.editdobdetails);
            TextView linkedin=root.findViewById(R.id.editlinkdindetails);
            TextView website=root.findViewById(R.id.editwebsitedetails);
            TextView yop=root.findViewById(R.id.edityearofpassdetails);
            name.setText(student.getName());
            branch.setText(student.getBranch());
            contact.setText(student.getContactNumber());
            dob.setText(student.getDateOfBirth());
            linkedin.setText(student.getLinkedIn());
            website.setText(student.getWebpage());
            yop.setText(student.getYearOfPassing());
            Edit = (Button) root.findViewById(R.id.Editdetails);
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Profilechange.class);
                    intent.putExtra("type","1");
                    startActivity(intent);


                }
            });
        }
        else if(type==2){
            Alumni alumni=FeedFragment.currentAlumni;
            root = inflater.inflate(R.layout.fragment_profile_details_alumni, container, false);
            TextView name=root.findViewById(R.id.editnamedetails1);
            TextView branch=root.findViewById(R.id.editbranchdetails1);
            TextView contact=root.findViewById(R.id.editcontactdetails1);
            TextView status=root.findViewById(R.id.editstatusdetails1);
            TextView dob=root.findViewById(R.id.editdobdetails1);
            TextView linkedin=root.findViewById(R.id.editlinkdindetails1);
            TextView website=root.findViewById(R.id.editwebsitedetails1);
            TextView yop=root.findViewById(R.id.edityearofpassdetails1);
            TextView company=root.findViewById(R.id.editcompanydetails1);
            name.setText(alumni.getName());
            branch.setText(alumni.getBranch());
            contact.setText(alumni.getContactNumber());
            status.setText(alumni.getCurrentStatus());
            dob.setText(alumni.getDateOfBirth());
            linkedin.setText(alumni.getLinkedIn());
            website.setText(alumni.getWebpage());
            yop.setText(alumni.getYearOfPassing());
            company.setText(alumni.getInstituteCompany());
            Edit = (Button) root.findViewById(R.id.Editdetails1);
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Profilechange.class);
                    intent.putExtra("type","2");
                    startActivity(intent);

                }
            });
        }
        else{
            Faculty faculty =FeedFragment.currentFaculty;
            root = inflater.inflate(R.layout.fragment_profile_details_faculty, container, false);
            TextView name=root.findViewById(R.id.editnamedetails2);
            TextView expertise=root.findViewById(R.id.editexpertisedetails2);
            TextView department=root.findViewById(R.id.editdepartmentdetails2);
            TextView linkedin=root.findViewById(R.id.editlinkdindetails2);
            TextView website=root.findViewById(R.id.editwebsitedetails2);
            name.setText(faculty.getName());
            expertise.setText(faculty.getExpertise());
            linkedin.setText(faculty.getLinkedIn());
            website.setText(faculty.getWebpage());
            Edit = (Button) root.findViewById(R.id.Editdetails2);
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Profilechange.class);
                    intent.putExtra("type","3");
                    startActivity(intent);
                }
            });
        }
        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}