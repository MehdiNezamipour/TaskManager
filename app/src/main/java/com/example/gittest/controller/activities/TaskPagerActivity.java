package com.example.gittest.controller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gittest.R;
import com.example.gittest.controller.fragments.AddTaskDialogFragment;
import com.example.gittest.controller.fragments.EditTaskDialogFragment;
import com.example.gittest.controller.fragments.TaskListFragment;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskRepository;
import com.example.gittest.repositories.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "userName";
    public static final String ADD_TASK_DIALOG_FRAGMENT_TAG = "AddTaskDialogFragment";

    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActionButton;
    private TaskListFragment mTodoFragment;
    private TaskListFragment mDoingFragment;
    private TaskListFragment mDoneFragment;
    private ViewPager2 mViewPager2;
    private UserRepository mUserRepository;
    private String mUserName;
    private User mUser;


    public static Intent newIntent(Context context, String taskName) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_USERNAME, taskName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance();
        mTodoFragment = TaskListFragment.newInstance();
        mDoingFragment = TaskListFragment.newInstance();
        mDoneFragment = TaskListFragment.newInstance();
        mUserName = getIntent().getStringExtra(EXTRA_USERNAME);
        mUser = mUserRepository.get(mUserName);

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


    private void setListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddTaskDialogFragment addTaskDialogFragment = AddTaskDialogFragment.newInstance(mUserName);
                addTaskDialogFragment.show(getSupportFragmentManager(), ADD_TASK_DIALOG_FRAGMENT_TAG);

               /* mTodoFragment.setTasks(mUser.getTaskRepository().getTodoTasks());
                mDoingFragment.setTasks(mUser.getTaskRepository().getDoingTasks());
                mDoneFragment.setTasks(mUser.getTaskRepository().getDoneTasks());

                mTodoFragment.getAdapter().notifyDataSetChanged();
                mDoingFragment.getAdapter().notifyDataSetChanged();
                mDoneFragment.getAdapter().notifyDataSetChanged();*/
            }
        });
    }


    private void findViews() {
        mFloatingActionButton = findViewById(R.id.fab);
        mViewPager2 = findViewById(R.id.viewPager2);
        mTabLayout = findViewById(R.id.tabLayout);
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

