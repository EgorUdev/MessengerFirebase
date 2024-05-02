package com.example.messengerfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonSignUp;
    private RegistrationActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        viewModel = new ViewModelProvider(this).get(RegistrationActivityViewModel.class);
        observeViewModel();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getTrimmedValue(editTextEmail);
                String password = getTrimmedValue(editTextPassword);
                String name = getTrimmedValue(editTextName);
                String lastName = getTrimmedValue(editTextLastName);
                String age = getTrimmedValue(editTextAge);
                if (!email.isEmpty()
                        && !password.isEmpty()
                        && !name.isEmpty()
                        && !lastName.isEmpty()
                        && !age.isEmpty()
                ) {
                    viewModel.register(email, password, name, lastName, age);
                } else {
                    Toast.makeText(RegistrationActivity.this,
                                    "Please, fill all fields",
                                    Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.etEmailRegistration);
        editTextPassword = findViewById(R.id.etPasswordRegistration);
        editTextName = findViewById(R.id.etNameRegistration);
        editTextLastName = findViewById(R.id.etLastName);
        editTextAge = findViewById(R.id.etAge);
        buttonSignUp = findViewById(R.id.btnSignUp);
    }

    private void observeViewModel() {
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(RegistrationActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getErrorToast().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(RegistrationActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }
}