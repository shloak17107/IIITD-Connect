package com.example.iiitdconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_enter extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference student = database.getReference("Student");

    String email;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_enter);
        Intent intent = getIntent();
         email = intent.getStringExtra("Email");
         name = intent.getStringExtra("Name");
        TextView text1 = (TextView)findViewById(R.id.textView);
        TextView text2 = (TextView)findViewById(R.id.textView1);
        text1.setText(name);
        text2.setText(email);
    }



    public void next(View view){

        EditText branch = (EditText) findViewById(R.id.editText3);
        EditText yop = (EditText) findViewById(R.id.editText2);
        String br = branch.getText().toString();
        String y = yop.getText().toString();

        email = "aishwarya";

        student.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student.child(email).child("name").setValue(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent oneIntent = new Intent(this, profile_see.class);
        startActivity (oneIntent);

    }
}
