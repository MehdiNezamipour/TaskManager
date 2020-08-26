package com.example.gittest.controller.activities;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.gittest.controller.fragments.TaskListFragment;

public class TaskListActivity extends SingleFragmentActivity {


    public static final String EXTRA_USER_NAME = "userName";

    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, TaskListActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return TaskListFragment.newInstance();
    }

}