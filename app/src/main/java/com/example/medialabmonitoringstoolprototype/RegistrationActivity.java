package com.example.medialabmonitoringstoolprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    // create variables
    private TextView backToLogin;
    private EditText nameRegistration, ageRegistration, passwordRegistration, emailRegistration;
    private Button confirmRegistration;

    // make FirebaseAuth object
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // fill firebaseAuth object with a firebaseAuth singleton instance.
        mAuth = FirebaseAuth.getInstance();


        // get backtologin button and give it onClick listener.
        backToLogin = (TextView) findViewById(R.id.backToLogin);
        backToLogin.setOnClickListener(this);

        // get confirmregistration button and give it onClick listener
        confirmRegistration = (Button) findViewById(R.id.confirmRegistration);
        confirmRegistration.setOnClickListener(this);

        // get all textfields
        nameRegistration = (EditText) findViewById(R.id.nameRegistration);
        ageRegistration = (EditText) findViewById(R.id.editAge);
        passwordRegistration = (EditText) findViewById(R.id.passwordRegistration);
        emailRegistration = (EditText) findViewById(R.id.emailRegistration);
    }

    // onclick listener event function
    public void onClick(View v) {

        // switch method that runs trough the onlick event
        switch (v.getId()){

            // if onclick event comes from backToLogin
            case R.id.backToLogin:

                // start main activity
                startActivity(new Intent(this, LoginActivity.class));
                break;

            // if onclick event comes from banner
            case R.id.confirmRegistration:

                // run the register user function
                registerUser();
                break;
        }
    }

    // The register user function
    private void registerUser() {

        // get text from registration textfields, convert text to string, remove spaces in front and behind with trim.
        String name = nameRegistration.getText().toString().trim();
        String password = passwordRegistration.getText().toString().trim();
        String email = emailRegistration.getText().toString().trim();
        String age = ageRegistration.getText().toString().trim();

        // check if textfields are not empty
        if (name.isEmpty()){
            nameRegistration.setError("Name is required!");
            nameRegistration.requestFocus();
            return;
        }

        if (age.isEmpty()){
            ageRegistration.setError("age is required!");
            ageRegistration.requestFocus();
            return;
        }

        if (email.isEmpty()){
            emailRegistration.setError("email is required!");
            emailRegistration.requestFocus();
            return;
        }

        // check if email is a valid email address.
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailRegistration.setError("Please provide an valid email address!");
            emailRegistration.requestFocus();
            return;
        }

        // Firebase password minimum password characters is 6, so here i check if password length is not lower than 6.
        if (password.length() < 6){
            passwordRegistration.setError("min password length should be 6 characters!");
            passwordRegistration.requestFocus();
            return;
        }

        if (password.isEmpty()){
            passwordRegistration.setError("password is required!");
            passwordRegistration.requestFocus();
            return;
        }

        // Firebase function to create user with given email and password
        mAuth.createUserWithEmailAndPassword(email, password)

                // function to run when user has been created
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // check if task has been succesfull. Task is the authresult in the onComplete arguments inf public function above
                        if (task.isSuccessful()){

                            // creating a user with my "user" class. I'm using a "user" class to store user info in object first, to send user object to firebase.
                            User user = new User( name, email, age);

                            // getting the firebase database "Users" and current user id by calling FirebaseAuth getcurrentuser getUid. This way,firebase knows which user id needs be added to the user database.
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                // if user and user id has been added to the user database table.
                                public void onComplete(@NonNull Task<Void> task) {

                                    // notification if task has been successful or not
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "User has been registered succesfully", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(RegistrationActivity.this, "Failed to register user, Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegistrationActivity.this, "Failed to register user, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}