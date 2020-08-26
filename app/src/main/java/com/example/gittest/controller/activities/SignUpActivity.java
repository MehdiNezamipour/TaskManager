package com.example.gittest.controller.activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.amitshekhar.DebugDB;
import com.example.gittest.controller.fragments.LoginFragment;

public class SignUpActivity extends SingleFragmentActivity {

    public Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }


    @Override
    protected Fragment createFragment() {
        Log.d("SUA", DebugDB.getAddressLog());
        return LoginFragment.newInstance();
    }


}