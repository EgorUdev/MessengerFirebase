package com.example.messengerfirebase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SignInActivityViewModel  extends AndroidViewModel {

    private MutableLiveData<String> email;
    private MutableLiveData<String> password;



    public SignInActivityViewModel(@NonNull Application application) {
        super(application);
    }


}
