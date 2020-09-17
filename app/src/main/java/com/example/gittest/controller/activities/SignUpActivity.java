package com.example.gittest.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.gittest.controller.fragments.LoginFragment;

public class SignUpActivity extends SingleFragmentActivity {

    public Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }


    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }


}