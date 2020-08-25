package com.example.gittest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.controller.fragments.EditTaskDialogFragment;
import com.example.gittest.model.Task;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {

    public static final String EDIT_TASK_DIALOG_FRAGMENT_TAG = "editTaskDialogFragment";
    private List<Task> mTasks;
    private Context mContext;
    private Fragment mFragment;
    private String mUserName;

    public TaskListAdapter(Context context, Fragment fragment, String userName) {
        mContext = context;
        mFragment = fragment;
        mUserName = userName;
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
            mTextViewTaskIcon.setText(task.getTaskTitle());

            mMaterialCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditTaskDialogFragment editTaskDialogFragment = EditTaskDialogFragment.newInstance(mUserName, task);
                    assert mFragment.getFragmentManager() != null;
                    editTaskDialogFragment.show(mFragment.getFragmentManager(), EDIT_TASK_DIALOG_FRAGMENT_TAG);
                }
            });

        }

    }

}
