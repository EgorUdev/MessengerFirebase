package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersActivityViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private MutableLiveData<FirebaseUser> user;
    private MutableLiveData<String> errorToastMessage;

    public UsersActivityViewModel() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }

    public void logOut() {
        mAuth.signOut();
    }

    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }

    public MutableLiveData<String> getErrorToastMessage() {
        return errorToastMessage;
    }
}
