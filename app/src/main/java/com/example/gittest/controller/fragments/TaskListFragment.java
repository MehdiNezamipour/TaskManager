package com.example.gittest.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gittest.R;
import com.example.gittest.controller.activities.TaskListActivity;
import com.example.gittest.model.Task;
import com.example.gittest.repositories.IRepository;
import com.example.gittest.repositories.TaskRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment {
    public static final String ARG_TASK_NAME = "taskName";
    public static final String ARG_NUMBER_OF_TASKS = "numberOfTasks";

    private IRepository<Task> mRepository;
    private RecyclerView mTaskRecyclerView;
    private Button mButtonAdd;
    private String mTaskName;
    private int mTaskNumbers;
    private TaskAdapter mAdapter;


    public TaskListFragment() {
        // Required empty public constructor
    }

    /**
     * @param taskName
     * @param taskNumbers
     * @return
     */
    public static TaskListFragment newInstance(String taskName, int taskNumbers) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TASK_NAME, taskName);
        args.putInt(ARG_NUMBER_OF_TASKS, taskNumbers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskName = getArguments().getString(ARG_TASK_NAME);
        mTaskNumbers = getArguments().getInt(ARG_NUMBER_OF_TASKS);
        TaskRepository.setTasksSize(mTaskNumbers);
        mRepository = TaskRepository.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        initUI(view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        setListeners();
        return view;
    }


    private void initUI(View view) {
        mTaskRecyclerView = view.findViewById(R.id.recyclerView_task_list);
        mButtonAdd = view.findViewById(R.id.button_add_task);
    }

    private void updateUI() {
        List<Task> tasks = mRepository.getList();
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mTaskRecyclerView.setAdapter(mAdapter);
        } else
            mAdapter.notifyDataSetChanged();
    }

    private void setListeners() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            //TODO
            @Override
            public void onClick(View view) {

            }
        });

    }


    public class TaskHolder extends RecyclerView.ViewHolder {

        private Task mTask;
        private TextView mTextViewTaskName;
        private TextView mTextViewTaskState;
        private TextView mTextViewTaskId;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTaskName = itemView.findViewById(R.id.textView_task_name);
            mTextViewTaskState = itemView.findViewById(R.id.textView_task_state);
            mTextViewTaskId = itemView.findViewById(R.id.textView_task_id);
        }

        public void bindTask(Task task) {
            mTask = task;
            mTextViewTaskName.setText(mTaskName);
            mTextViewTaskState.setText(task.getTaskState().toString());
            mTextViewTaskId.setText(task.getTaskId().toString());
        }

    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.task_row_layout, viewGroup, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
            taskHolder.bindTask(mTasks.get(i));
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

}