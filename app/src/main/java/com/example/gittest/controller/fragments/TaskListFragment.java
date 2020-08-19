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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.gittest.R;
import com.example.gittest.model.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment {


    private RecyclerView mTaskRecyclerView;
    private TaskAdapter mAdapter;
    private List<Task> mTasks = new ArrayList<>();
    private ImageView mImageViewEmptyList;
    private TextView mTextViewEmptyList;
    private OnTaskClickListener mListener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }


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
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(mTasks);
        }
        mAdapter.setTasks(mTasks);
        mTaskRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (TaskListFragment.OnTaskClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnTaskClickListener");
        }
    }

    private void initUI(View view) {
        mTaskRecyclerView = view.findViewById(R.id.recyclerView_task_list);
        mImageViewEmptyList = view.findViewById(R.id.imageView_empty_list);
        mTextViewEmptyList = view.findViewById(R.id.textView_empty_list);
    }

    private void updateUI() {

        /*
        if (mTasks.size() == 0) {
            mImageViewEmptyList.setVisibility(View.VISIBLE);
            mTextViewEmptyList.setVisibility(View.VISIBLE);
        } else {
            mImageViewEmptyList.setVisibility(View.GONE);
            mTextViewEmptyList.setVisibility(View.GONE);
        }*/

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
                    mListener.onTaskClick(task);

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