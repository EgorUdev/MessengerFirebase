package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ResetPasswordActivityViewModel  extends ViewModel {
    private FirebaseAuth mAuth;
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

//    public ResetPasswordActivityViewModel() {
//        mAuth = FirebaseAuth.getInstance();
//    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }

    public void resetPassword(String email) {
        mAuth = FirebaseAuth.getInstance();
        if (!email.isEmpty()) {
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                toastMessage.setValue(String.valueOf(R.string.reset_link_is_sent));
                            } else {
                                String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                                toastMessage.setValue(errorMessage);
                            }
                        }
                    });
        } else {
            toastMessage.setValue(String.valueOf(R.string.please_enter_the_email));
        }
    }
}
