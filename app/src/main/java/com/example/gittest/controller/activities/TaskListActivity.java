package com.example.gittest.controller.activities;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.gittest.controller.fragments.TaskListFragment;
import com.example.gittest.enums.State;

public class TaskListActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TaskListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return TaskListFragment.newInstance();
    }
}