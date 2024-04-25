package com.example.messengerfirebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Check if user is authorized
        mAuth = FirebaseAuth.getInstance();

//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user == null) {
//            Log.d(LOG_TAG, "Not authorized");
//        } else {
//            Log.d(LOG_TAG, "Authorized");
//        }
//
//        //Log out user
//        mAuth.signOut();
//
//
//        //Check Auth state
//        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = mAuth.getCurrentUser();
//                if (user == null) {
//                    Log.d(LOG_TAG, "Not authorized");
//                } else {
//                    Log.d(LOG_TAG, "Authorized" + user.getUid());
//                }
//            }
//        });
//
//        //Log in user
//        mAuth.signInWithEmailAndPassword("email@email.com", "3111111")
//                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                            @Override
//                            public void onSuccess(AuthResult authResult) {
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                if (user == null) {
//                                    Log.d(LOG_TAG, "Not authorized");
//                                } else {
//                                    Log.d(LOG_TAG, "Authorized");
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
//                        Toast.makeText(
//                                        MainActivity.this,
//                                        e.getMessage(),
//                                        Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                });

        //Reset password link send
        String email = "kesorev+2@gmail.com";
        mAuth.signInWithEmailAndPassword(email, "000000")
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
                        Toast.makeText(
                                        MainActivity.this,
                                        e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
//        mAuth.sendPasswordResetEmail(email)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(LOG_TAG, "Mail was sent ou to " + email);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(LOG_TAG, "Mail was NOT sent ou to " + email + " " + e.getMessage());
//                    }
//                });

        //Signup new user
        mAuth.createUserWithEmailAndPassword("email@email.com", "111111")
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user == null) {
                            Log.d(LOG_TAG, "Not authorized");
                        } else {
                            Log.d(LOG_TAG, "Authorized");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
                        Toast.makeText(
                                        MainActivity.this,
                                        e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}