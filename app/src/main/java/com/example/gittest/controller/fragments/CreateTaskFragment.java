package com.example.gittest.controller.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gittest.R;
import com.example.gittest.controller.activities.TaskListActivity;
import com.example.gittest.controller.activities.TaskPagerActivity;
import com.example.gittest.model.Task;
import com.example.gittest.repositories.TaskRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTaskFragment extends Fragment {



    private EditText mEditTextUserName;
    private EditText mEditTextNumberOfTasks;
    private Button mButtonBuildTasks;


    public CreateTaskFragment() {
        // Required empty public constructor
    }

    public static CreateTaskFragment newInstance() {
        CreateTaskFragment fragment = new CreateTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);
        initUI(view);
        mButtonBuildTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInputs()) {
                    Intent intent = TaskPagerActivity.newIntent(getActivity(), mEditTextUserName.getText().toString(),
                            Integer.parseInt(mEditTextNumberOfTasks.getText().toString()));
                    startActivity(intent);
                } else
                    Toast.makeText(getContext(), "Wrong Inputs !!!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private boolean checkInputs() {
        if (mEditTextUserName.getText().toString().length() == 0 || mEditTextNumberOfTasks.getText().toString().length() == 0)
            return false;
        else
            return true;
    }

    private void initUI(View view) {
        mEditTextUserName = view.findViewById(R.id.editText_username);
        mEditTextNumberOfTasks = view.findViewById(R.id.editText_number_of_tasks);
        mButtonBuildTasks = view.findViewById(R.id.button_build_tasks);

    }
}