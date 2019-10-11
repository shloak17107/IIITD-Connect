package com.example.iiitdconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private boolean flag;
    private DatabaseReference mDatabase;
    public static FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("638022948431-c9op3nr45inlcojskujluvdk04l9arlp.apps.googleusercontent.com")
                .requestEmail()
                .build();
        flag = false;
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    public void check(String dsubchild, String x, String y){
        final String id = x;
        final String email = y;
        mDatabase.child("Student").orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    flag = true;
                    Intent i = new Intent(MainActivity.this, Feed.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                } else {
                    mDatabase.child("Alumni").orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                flag = true;
                                Intent i = new Intent(MainActivity.this, Feed.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            } else {
                                mDatabase.child("Faculty").orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()) {
                                            flag = true;
                                            Intent i = new Intent(MainActivity.this, Feed.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Intent oneIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                                            oneIntent.putExtra("Email", email);
                                            oneIntent.putExtra("Name", currentUser.getDisplayName().toString());
                                            startActivity(oneIntent);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.d("MSG", "cancelled");
                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("MSG", "cancelled");
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MSG", "cancelled");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
//            Log.d("MESSAGE", "1");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            Log.d("MESSAGE", "FEEED");
            String email = mAuth.getCurrentUser().getEmail().toString();
            String id = email.substring(0, email.indexOf("@"));


            check("Student", id, email);
//            check("Alumni", id);
//            check("Faculty", id);


//
//            if(mDatabase.child("Student").child(id).getClass() != null || mDatabase.child("Alumni").child(id).getClass() != null || mDatabase.child("Faculty").child(id).getClass() != null) {
//                Log.d("MESSAGE", "2");
//                Intent i = new Intent(this, Feed.class);
//                startActivity(i);
//            }else{
//                Log.d("MESSAGE", "3");
//                Intent oneIntent = new Intent(this, RegistrationActivity.class);
//                oneIntent.putExtra("Email", email);
//                oneIntent.putExtra("Name", mAuth.getCurrentUser().getDisplayName().toString());
//                startActivity (oneIntent);
//            }
        }

    }


}