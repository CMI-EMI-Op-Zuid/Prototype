package com.example.medialabmonitoringstoolprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setItemBackgroundResource(R.drawable.menubackground);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DashboardFragment())
                .commit();
    }

    public void EnterStoryOverview(View view){
        Navigation.findNavController(view).navigate(R.id.storyOverview);
    }

    public void StartStory(View view){
        Navigation.findNavController(view).navigate(R.id.storyIntro);
    }

    public void StartChallenge(View view){
        Navigation.findNavController(view).navigate(R.id.storyChallenge);
    }

    public void StartEnding(View view){
        Navigation.findNavController(view).navigate(R.id.storyEnding);
    }

    public void EndStory(View view){
        Navigation.findNavController(view).navigate(R.id.dashboardFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.storyOverview:
                            selectedFragment = new StoryOverview();
                            break;
                        case R.id.dashboardFragment:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.profileFragment:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

}