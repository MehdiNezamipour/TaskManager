package com.example.gittest.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.gittest.controller.fragments.SearchFragment;

import static com.example.gittest.controller.activities.TaskListActivity.EXTRA_USER_NAME;

public class SearchActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return SearchFragment.newInstance(getIntent().getStringExtra(EXTRA_USER_NAME));
    }
}