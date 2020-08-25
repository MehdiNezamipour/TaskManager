package com.example.gittest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskRepository;
import com.example.gittest.repositories.UserRepository;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskManageFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private String mUserName;
    private User mUser;

    public static final String ARG_USER_NAME = "com.example.gittest.userName";

    public TaskManageFragment() {
        // Required empty public constructor
    }

    public static TaskManageFragment newInstance(String userName) {
        TaskManageFragment fragment = new TaskManageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_USER_NAME);
        }
        mUser = UserRepository.getInstance().get(mUserName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(TaskRepository.getInstance().getList(mUser));
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void findViews(@NonNull View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_admin);
    }

    public class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTaskTitle;
        private TextView mTextViewTaskSubject;
        private TextView mTextViewTaskDate;
        private TextView mTextViewTaskIcon;
        private MaterialCardView mMaterialCardView;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mMaterialCardView = itemView.findViewById(R.id.card_container);
            mTextViewTaskTitle = itemView.findViewById(R.id.textView_task_title);
            mTextViewTaskSubject = itemView.findViewById(R.id.textView_task_subject);
            mTextViewTaskDate = itemView.findViewById(R.id.textView_task_date);
            mTextViewTaskIcon = itemView.findViewById(R.id.textView_task_icon);
        }

        public void bindTask(Task task) {
            mTextViewTaskTitle.setText(task.getTaskTitle());
            mTextViewTaskSubject.setText(task.getTaskSubject());
            mTextViewTaskDate.setText(task.getDate() + "        " + task.getTime());
            /*if (getAdapterPosition() % 2 == 0) {
                mMaterialCardView.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            }*/
            mMaterialCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    task.setEditable(true);
                }
            });
        }
    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        public void setTasks(List<Task> tasks) {
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