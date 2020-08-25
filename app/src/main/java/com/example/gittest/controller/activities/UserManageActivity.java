package com.example.gittest.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.gittest.controller.fragments.UserManageFragment;

public class UserManageActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, UserManageActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return UserManageFragment.newInstance();
    }
}