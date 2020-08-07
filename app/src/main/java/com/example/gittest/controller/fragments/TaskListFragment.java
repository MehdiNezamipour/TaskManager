package com.example.gittest.controller.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gittest.R;
import com.example.gittest.controller.activities.TaskListActivity;
import com.example.gittest.controller.activities.TaskPagerActivity;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;
import com.example.gittest.repositories.IRepository;
import com.example.gittest.repositories.TaskRepository;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment implements Serializable {


    private RecyclerView mTaskRecyclerView;
    private TaskAdapter mAdapter;
    private List<Task> mTasks;

    public TaskAdapter getAdapter() {
        return mAdapter;
    }

    public TaskListFragment() {
        // Required empty public constructor
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mTaskRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        updateUI();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initUI(View view) {
        mTaskRecyclerView = view.findViewById(R.id.recyclerView_task_list);
    }

    private void updateUI() {
        if (mAdapter == null)
            mAdapter = new TaskAdapter();
        mAdapter.setTasks(mTasks);
        mTaskRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }


    public class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTaskName;
        private TextView mTextViewTaskState;
        private TextView mTextViewTaskDate;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTaskName = itemView.findViewById(R.id.textView_task_name);
            mTextViewTaskState = itemView.findViewById(R.id.textView_task_state);
            mTextViewTaskDate = itemView.findViewById(R.id.textView_task_time);
        }

        public void bindTask(Task task) {
            mTextViewTaskName.setText(task.getTaskTitle());
            mTextViewTaskState.setText(task.getTaskState().toString());
            mTextViewTaskDate.setText(task.getDate());
        }

    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        List<Task> mTasks;

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.task_row_layout, viewGroup, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder taskHolder, int position) {
            taskHolder.bindTask(mTasks.get(position));
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

}