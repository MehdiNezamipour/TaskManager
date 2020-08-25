package com.example.gittest.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.adapters.TaskListAdapter;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskRepository;
import com.example.gittest.repositories.UserRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskManageFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskListAdapter mAdapter;
    private String mUserName;
    private User mUser;

    public static final String ARG_USER_NAME = "com.example.gittest.userName";

    public TaskListAdapter getAdapter() {
        return mAdapter;
    }

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
            mAdapter = new TaskListAdapter(getActivity(), this, mUserName);
        }
        mAdapter.setTasks(TaskRepository.getInstance().getList(mUser));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private void findViews(@NonNull View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_admin);
    }


}