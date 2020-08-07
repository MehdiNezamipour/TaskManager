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
import com.example.gittest.controller.fragments.TaskListFragment;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;
import com.example.gittest.repositories.IRepository;
import com.example.gittest.repositories.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_NAME = "taskName";
    public static final String EXTRA_NUMBER_OF_TASKS = "numberOfTasks";

    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActionButton;

    private TaskRepository mTaskRepository = TaskRepository.getInstance();
    private TaskListFragment mTodoFragment;
    private TaskListFragment mDoingFragment;
    private TaskListFragment mDoneFragment;
    private ViewPager2 mViewPager2;
    private String mTasksTitle;
    private int mInitTaskNumbers;


    public static Intent newIntent(Context context, String taskName, int numberOfTasks) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_TASK_NAME, taskName);
        intent.putExtra(EXTRA_NUMBER_OF_TASKS, numberOfTasks);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTodoFragment = TaskListFragment.newInstance();
        mDoingFragment = TaskListFragment.newInstance();
        mDoneFragment = TaskListFragment.newInstance();
        firstTasks();
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

    private void firstTasks() {
        mTasksTitle = getIntent().getStringExtra(EXTRA_TASK_NAME);
        mInitTaskNumbers = getIntent().getIntExtra(EXTRA_NUMBER_OF_TASKS, -1);
        for (int i = 0; i < mInitTaskNumbers; i++) {
            Task task = new Task();
            task.setTaskTitle(mTasksTitle);
            mTaskRepository.add(task);
        }
        mTodoFragment.setTasks(mTaskRepository.getTodoTasks());
        mDoingFragment.setTasks(mTaskRepository.getDoingTasks());
        mDoneFragment.setTasks(mTaskRepository.getDoneTasks());

    }

    private void setListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();
                task.setTaskTitle(mTasksTitle);
                mTaskRepository.add(task);
                mTodoFragment.setTasks(mTaskRepository.getTodoTasks());
                mDoingFragment.setTasks(mTaskRepository.getDoingTasks());
                mDoneFragment.setTasks(mTaskRepository.getDoneTasks());
                mTodoFragment.getAdapter().notifyDataSetChanged();
                mDoingFragment.getAdapter().notifyDataSetChanged();
                mDoneFragment.getAdapter().notifyDataSetChanged();

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

