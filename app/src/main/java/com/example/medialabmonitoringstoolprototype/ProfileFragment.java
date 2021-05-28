package com.example.medialabmonitoringstoolprototype;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment  implements FirebaseAuth.AuthStateListener, View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private TextView logoutButton, textName, textAge, textRadius;
    private ImageView imageView;
    private Button editButton, submitForm;
    private EditText editName, editAge, editRadius;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        textName = v.findViewById(R.id.textName);
        textAge = v.findViewById(R.id.textAge);
        textRadius = v.findViewById(R.id.textRadius);
        editName = v.findViewById(R.id.editName);
        editAge = v.findViewById(R.id.editAge);
        editRadius = v.findViewById(R.id.editRadius);
        submitForm = v.findViewById(R.id.submitForm);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String name = userProfile.name;
                    String age = userProfile.age;
                    String radius = userProfile.radius.toString();

                    textName.setText(name);
                    textAge.setText(age);
                    textRadius.setText(radius);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), "Something Wrong happened", Toast.LENGTH_LONG).show();
            }
        });
        imageView = v.findViewById(R.id.imageView);

        editButton = v.findViewById(R.id.editButton);
        editButton.setOnClickListener(this);

        logoutButton = v.findViewById(R.id.logOutButton);
        logoutButton.setOnClickListener(this);

        return v;
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logOutButton:
                FirebaseAuth.getInstance().signOut();
                break;
            case R.id.editButton:
                editForm(v);
                break;
            case R.id.submitForm:
                submitForm(v);
                break;
        }
    }

    private void submitForm(View v) {
        String name = editName.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String radius = editRadius.getText().toString().trim();

        // check if textfields are not empty
        if (name.isEmpty()){
            editName.setError("Name is required!");
            editName.requestFocus();
        }

        if (age.isEmpty()){
            editAge.setError("Age is required!");
            editAge.requestFocus();
        }

        if (radius.isEmpty()){
            editRadius.setError("Radius is required!");
            editRadius.requestFocus();
        }

        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Users").child(userID).child("age").setValue(age).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to update age, Try Again", Toast.LENGTH_SHORT).show();
            }
        });
        reference.child("Users").child(userID).child("name").setValue(name).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to update name, Try Again", Toast.LENGTH_SHORT).show();
            }
        });
        reference.child("Users").child(userID).child("radius").setValue(radius).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to update radius, Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editForm(View v) {

        textName.setVisibility(View.INVISIBLE);

        textAge.setVisibility(View.INVISIBLE);

        textRadius.setVisibility(View.INVISIBLE);

        logoutButton.setVisibility(View.INVISIBLE);

        editName.setVisibility(View.VISIBLE);

        editAge.setVisibility(View.VISIBLE);

        editRadius.setVisibility(View.VISIBLE);

        submitForm.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            requireActivity().finish();
        }else{
//            Toast.makeText(ProfileActivity.this, "Couldn't sign out, please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}