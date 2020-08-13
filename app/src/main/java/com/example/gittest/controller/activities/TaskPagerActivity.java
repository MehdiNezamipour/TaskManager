package com.example.gittest.controller.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gittest.R;
import com.example.gittest.controller.fragments.AddTaskDialogFragment;
import com.example.gittest.controller.fragments.TaskListFragment;
import com.example.gittest.enums.State;
import com.example.gittest.model.User;
import com.example.gittest.repositories.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.UUID;

public class TaskPagerActivity extends AppCompatActivity implements AddTaskDialogFragment.OnAddDialogDismissListener, TaskListFragment.OnTaskClickListener {

    public static final String EXTRA_USERNAME = "userName";
    public static final String ADD_TASK_DIALOG_FRAGMENT_TAG = "AddTaskDialogFragment";
    public static final String EDIT_TASK_DIALOG_FRAGMENT_TAG = "editTaskDialogFragment";

    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActionButton;
    private TaskListFragment mTodoFragment;
    private TaskListFragment mDoingFragment;
    private TaskListFragment mDoneFragment;
    private ViewPager2 mViewPager2;
    private UserRepository mUserRepository;
    private String mUserName;
    private User mUser;


    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_USERNAME, userName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserName = getIntent().getStringExtra(EXTRA_USERNAME);
        mUserRepository = UserRepository.getInstance();
        mUser = mUserRepository.get(mUserName);

        mTodoFragment = TaskListFragment.newInstance();
        mDoingFragment = TaskListFragment.newInstance();
        mDoneFragment = TaskListFragment.newInstance();
        mTodoFragment.setTasks(mUser.getTaskRepository().getTodoTasks());
        mDoingFragment.setTasks(mUser.getTaskRepository().getDoingTasks());
        mDoneFragment.setTasks(mUser.getTaskRepository().getDoneTasks());


        setContentView(R.layout.activity_task_pager);
        findViews();
        FragmentStateAdapter adapter = new TaskViewPagerAdapter(this);
        mViewPager2.setAdapter(adapter);
        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0)
                    tab.setText(R.string.TODO);
                if (position == 1)
                    tab.setText(R.string.DOING);
                if (position == 2)
                    tab.setText(R.string.DONE);
            }
        }).attach();
        setListeners();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pager_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu_item:
                finish();
                return true;
            case R.id.remove_all_task_menu_item:
                new MaterialAlertDialogBuilder(this)
                        .setMessage(R.string.sureAlertMessage)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mUser.getTaskRepository().removeAllTasks();
                                notifyAllAdapter();
                            }
                        })
                        .setNeutralButton(android.R.string.cancel, null)
                        .create()
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskDialogFragment addTaskDialogFragment = AddTaskDialogFragment.newInstance(mUserName, "float");
                addTaskDialogFragment.show(getSupportFragmentManager(), ADD_TASK_DIALOG_FRAGMENT_TAG);

            }
        });
    }


    private void findViews() {
        mFloatingActionButton = findViewById(R.id.fab);
        mViewPager2 = findViewById(R.id.viewPager2);
        mTabLayout = findViewById(R.id.tabLayout);
    }

    @Override
    public void onDismiss(State state) {
        switch (state) {
            case TODO:
                if (mTodoFragment.getAdapter() != null) {
                    mTodoFragment.getAdapter().setTasks(mUser.getTaskRepository().getTodoTasks());
                    mTodoFragment.getAdapter().notifyDataSetChanged();
                }
                break;
            case DOING:
                if (mDoingFragment.getAdapter() != null) {
                    mDoingFragment.getAdapter().setTasks(mUser.getTaskRepository().getDoingTasks());
                    mDoingFragment.getAdapter().notifyDataSetChanged();
                }
                break;
            case DONE:
                if (mDoneFragment.getAdapter() != null) {
                    mDoneFragment.getAdapter().setTasks(mUser.getTaskRepository().getDoneTasks());
                    mDoneFragment.getAdapter().notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    private void notifyAllAdapter() {
        if (mTodoFragment.getAdapter() != null) {
            mTodoFragment.getAdapter().setTasks(mUser.getTaskRepository().getTodoTasks());
            mTodoFragment.getAdapter().notifyDataSetChanged();
        }

        if (mDoingFragment.getAdapter() != null) {
            mDoingFragment.getAdapter().setTasks(mUser.getTaskRepository().getDoingTasks());
            mDoingFragment.getAdapter().notifyDataSetChanged();
        }
        if (mDoneFragment.getAdapter() != null) {
            mDoneFragment.getAdapter().setTasks(mUser.getTaskRepository().getDoneTasks());
            mDoneFragment.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onTaskClick() {
        AddTaskDialogFragment addTaskDialogFragment = AddTaskDialogFragment.newInstance(mUserName, "task");
        addTaskDialogFragment.show(getSupportFragmentManager(), EDIT_TASK_DIALOG_FRAGMENT_TAG);
    }


    public class TaskViewPagerAdapter extends FragmentStateAdapter {

        public TaskViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return mTodoFragment;
            } else if (position == 1)
                return mDoingFragment;
            else if (position == 2)
                return mDoneFragment;
            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }


}

