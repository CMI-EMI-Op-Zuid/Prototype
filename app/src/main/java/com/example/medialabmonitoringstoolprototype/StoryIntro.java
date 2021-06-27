package com.example.medialabmonitoringstoolprototype;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StoryIntro extends Fragment {

    public StoryIntro() {
        // Required empty public constructor
    }

    public static StoryIntro newInstance(String param1, String param2) {
        StoryIntro fragment = new StoryIntro();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story_intro, container, false);
    }
}