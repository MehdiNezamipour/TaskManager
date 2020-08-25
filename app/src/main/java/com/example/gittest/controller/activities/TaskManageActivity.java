package com.example.gittest.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.gittest.controller.fragments.AddTaskDialogFragment;
import com.example.gittest.controller.fragments.TaskManageFragment;

public class TaskManageActivity extends SingleFragmentActivity implements AddTaskDialogFragment.OnAddDialogDismissListener {

    public static final String EXTRA_USER_NAME = "com.example.gittest.userName";
    private String mUserName;
    private TaskManageFragment mFragment;

    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, TaskManageActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        mUserName = getIntent().getStringExtra(EXTRA_USER_NAME);
        mFragment = TaskManageFragment.newInstance(mUserName);
        return mFragment;
    }


    @Override
    public void onDismiss() {
        mFragment.getAdapter().notifyDataSetChanged();
    }
}