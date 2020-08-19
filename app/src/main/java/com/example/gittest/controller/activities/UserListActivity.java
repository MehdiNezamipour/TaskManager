package com.example.gittest.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.gittest.controller.fragments.UserListFragment;

public class UserListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, UserListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return UserListFragment.newInstance();
    }
}