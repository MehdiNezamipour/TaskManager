package com.example.gittest.controller.activities;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.gittest.controller.fragments.TaskListFragment;

public class TaskListActivity extends SingleFragmentActivity {

    public static final String EXTRA_TASK_NAME = "taskName";
    public static final String EXTRA_NUMBER_OF_TASKS = "numberOfTasks";

    public static Intent newIntent(Context context, String taskName, int numberOfTasks) {
        Intent intent = new Intent(context, TaskListActivity.class);
        intent.putExtra(EXTRA_TASK_NAME, taskName);
        intent.putExtra(EXTRA_NUMBER_OF_TASKS, numberOfTasks);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return TaskListFragment.newInstance(getIntent().getStringExtra(EXTRA_TASK_NAME), getIntent().getIntExtra(EXTRA_TASK_NAME, -1));
        
    }
}