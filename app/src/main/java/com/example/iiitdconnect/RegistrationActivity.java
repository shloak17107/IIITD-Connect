package com.example.iiitdconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RegistrationActivity extends AppCompatActivity {

    public static String email, name;
    public static StorageReference storageReference;
    Button go;
    RadioGroup rg;
    RadioButton user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        name = intent.getStringExtra("Name");

        storageReference = FirebaseStorage.getInstance().getReference();
        addListenerOnButton();

    }


    public void addListenerOnButton() {
        rg = (RadioGroup) findViewById(R.id.usertypes);
        go = (Button) findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = rg.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                user_type = (RadioButton) findViewById(selectedId);

                if(user_type == null){
                    Toast.makeText(getApplicationContext(), "Please select an option to proceed!!!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(user_type.getText().equals("Student") ) {


                    Intent intent = new Intent(RegistrationActivity.this, UserType.class);
                    intent.putExtra("abc","student");
                    startActivity(intent);
                }
                else if(user_type.getText().equals("Alumni")){

                    Intent intent = new Intent(RegistrationActivity.this,UserType.class);
                    intent.putExtra("abc","alumni");
                    startActivity(intent);
                }
                else if(user_type.getText().equals("Faculty")){

                    Intent intent = new Intent(RegistrationActivity.this,UserType.class);
                    intent.putExtra("abc","faculty");
                    startActivity(intent);
                }

            }

        });
    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
