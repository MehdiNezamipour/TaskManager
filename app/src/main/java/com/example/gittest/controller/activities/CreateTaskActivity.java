package com.example.gittest.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.gittest.R;
import com.example.gittest.controller.fragments.CreateTaskFragment;

public class CreateTaskActivity extends SingleFragmentActivity {

    public Intent newIntent(Context context) {
        return new Intent(context, CreateTaskActivity.class);
    }


    @Override
    protected Fragment createFragment() {
        return CreateTaskFragment.newInstance();
    }


}