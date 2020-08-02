package com.example.gittest.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.gittest.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    /**
     * @return Fragment that must create and attach on own activity
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_single_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container_fragment);

        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container_fragment, createFragment())
                    .commit();
        }


    }

}