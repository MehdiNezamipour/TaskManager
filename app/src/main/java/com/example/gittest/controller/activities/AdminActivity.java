package com.example.gittest.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.gittest.controller.fragments.AdminFragment;

public class AdminActivity extends SingleFragmentActivity {

    public static final String EXTRA_USER_NAME = "com.example.gittest.userName";

    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return AdminFragment.newInstance(getIntent().getStringExtra(EXTRA_USER_NAME));
    }

}