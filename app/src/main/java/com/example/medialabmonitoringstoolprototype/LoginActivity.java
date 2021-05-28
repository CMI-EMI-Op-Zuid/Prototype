package com.example.medialabmonitoringstoolprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, View.OnClickListener  {

    private TextView Registration;
    private EditText editTextEmail, editTextPassword;

    private Button logInButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Registration = ( TextView ) findViewById( R.id.Registration );
        Registration.setOnClickListener(this);

        logInButton = ( Button ) findViewById( R.id.loginButton);
        logInButton.setOnClickListener(this);

        editTextEmail = ( EditText ) findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = ( EditText ) findViewById(R.id.editTextTextPassword);

        progressBar = ( ProgressBar ) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Registration:
                startActivity( new Intent( LoginActivity.this, RegistrationActivity.class));
                break;
            case R.id.loginButton:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // check if textfields are not empty
        if (email.isEmpty()){
            editTextEmail.setError("Name is required!");
            editTextEmail.requestFocus();
        }

        // check if email is a valid email address.
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide an valid email address!");
            editTextEmail.requestFocus();
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password);

//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                    editor.putBoolean("loggedIn", true);
//                    editor.apply();
//
//                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
//                    finish();
//                } else{
//                    Toast.makeText(MainActivity.this, "Wrong credentials, please try again.", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        }else{
//            Toast.makeText(MainActivity.this, "Wrong credentials, please try again.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}