package com.example.gittest.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.model.Task;

import java.util.List;

public class TaskSearchAdapter extends RecyclerView.Adapter<TaskSearchAdapter.TaskHolder> {

    List<Task> mTasks;
    Context mContext;

    public TaskSearchAdapter(Context context) {
        mContext = context;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.task_row_layout, viewGroup, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
        taskHolder.bindTask(mTasks.get(i));
    }

    @Override
    public int getItemCount() {
        if (mTasks == null)
            return 0;
        return mTasks.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTaskTitle;
        private TextView mTextViewTaskSubject;
        private TextView mTextViewTaskDate;
        private TextView mTextViewTaskIcon;


        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTaskTitle = itemView.findViewById(R.id.textView_task_title);
            mTextViewTaskSubject = itemView.findViewById(R.id.textView_task_subject);
            mTextViewTaskDate = itemView.findViewById(R.id.textView_task_date);
            mTextViewTaskIcon = itemView.findViewById(R.id.textView_task_icon);
        }

        public void bindTask(Task task) {
            mTextViewTaskTitle.setText(task.getTaskTitle());
            mTextViewTaskSubject.setText(task.getTaskSubject());
            mTextViewTaskDate.setText(task.getDate() + "        " + task.getTime());
            mTextViewTaskIcon.setText(task.getTaskTitle());
        }
    }

}

