package com.example.messengerfirebase;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.Objects;

public class SignInActivityViewModel extends AndroidViewModel {

    private static final String TAG = "SignInActivityViewModel";

    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private FirebaseAuth mAuth;
    private final MutableLiveData<Boolean> shouldNavigateToMainScreen = new MutableLiveData<>();
    private final MutableLiveData<Boolean> shouldNavigateToRegister = new MutableLiveData<>();
    private final MutableLiveData<Boolean> shouldNavigateToForgotPassword = new MutableLiveData<>();
    private MutableLiveData<String> showToastMessage = new MutableLiveData<>();

    public SignInActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getShouldNavigateToMainScreen() {
        return shouldNavigateToMainScreen;
    }

    public LiveData<Boolean> getShouldNavigateToRegister() {
        return shouldNavigateToRegister;
    }

    public LiveData<Boolean> getShouldNavigateToForgotPassword() {
        return shouldNavigateToForgotPassword;
    }

    public LiveData<String> getShowToastMessage() {
        return showToastMessage;
    }

    public void onEmailEntered(String emailText) {
        email.setValue(emailText);
    }

    public void onPasswordEntered(String passwordText) {
        password.setValue(passwordText);
    }

    public void onLoginClicked() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(Objects.requireNonNull(email.getValue()), Objects.requireNonNull(password.getValue()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            shouldNavigateToMainScreen.postValue(true);
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthException) {
                                String errorText = exception.getMessage();
                                showToastMessage.setValue(errorText);
                            } else if (email == null || password == null) {
                                showToastMessage.setValue(String.valueOf(R.string.null_password_or_email_error));
                            } else {
                                showToastMessage.postValue(String.valueOf(R.string.authentication_failed));
                            }
                            Log.e(TAG,"signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void onRegisterClicked() {
        shouldNavigateToRegister.postValue(true);
    }

    public void onForgotClicked() {
        shouldNavigateToForgotPassword.postValue(true);
    }
}
