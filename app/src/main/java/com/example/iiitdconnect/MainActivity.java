package com.example.iiitdconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("638022948431-c9op3nr45inlcojskujluvdk04l9arlp.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            Log.d("MESSAGE", "1");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
//            Intent i = new Intent(this, Feed.class);
//            startActivity(i);
            Log.d("MESSAGE", "FEEED");
            String email = mAuth.getCurrentUser().getEmail().toString();
            String id = email.substring(0, email.indexOf("@"));

            if(mDatabase.child("Student").child(id).getClass() != null || mDatabase.child("Alumni").child(id).getClass() != null || mDatabase.child("Faculty").child(id).getClass() != null) {
                Log.d("MESSAGE", "2");
                Intent i = new Intent(this, Feed.class);
                startActivity(i);
            }else{
                Log.d("MESSAGE", "3");
                Intent oneIntent = new Intent(this, RegistrationActivity.class);
                oneIntent.putExtra("Email", email);
                oneIntent.putExtra("Name", mAuth.getCurrentUser().getDisplayName().toString());
                startActivity (oneIntent);
            }
        }

    }


}