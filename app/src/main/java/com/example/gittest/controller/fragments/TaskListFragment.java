package com.example.gittest.controller.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.adapters.TaskListAdapter;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskDBRepository;
import com.example.gittest.repositories.UserDBRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment {


    public static final String ARG_STATE = "state";
    public static final String BUNDLE_STATE = "state";
    public static final String ARG_USER_NAME = "userName";
    private RecyclerView mTaskRecyclerView;
    private TaskListAdapter mAdapter;
    private List<Task> mTasks = new ArrayList<>();
    private ImageView mImageViewEmptyList;
    private TextView mTextViewEmptyList;
    private State mState;
    private TaskDBRepository mTaskDBRepository;
    private String mUserName;
    private User mUser;


    public TaskListAdapter getAdapter() {
        return mAdapter;
    }


    public TaskListFragment() {
        // Required empty public constructor
    }


    public static TaskListFragment newInstance(State state, String userName) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STATE, state);
        args.putString(ARG_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
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
        Log.d("TLF", "onCreate");
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());

        if (getArguments() != null) {
            mState = (State) getArguments().getSerializable(ARG_STATE);
            mUserName = getArguments().getString(ARG_USER_NAME);
        }
        mUser = UserDBRepository.getInstance(getActivity()).get(mUserName);

        if (savedInstanceState != null) {
            mState = (State) savedInstanceState.getSerializable(BUNDLE_STATE);
        }

        assert mState != null;
        switch (mState) {
            case TODO:
                mTasks = mTaskDBRepository.getSpecialTaskList(State.TODO, mUser.getId());
                break;
            case DOING:
                mTasks = mTaskDBRepository.getSpecialTaskList(State.DOING, mUser.getId());
                break;
            case DONE:
                mTasks = mTaskDBRepository.getSpecialTaskList(State.DONE, mUser.getId());
                break;
        }


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_STATE, mState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mAdapter == null) {
            mAdapter = new TaskListAdapter(getActivity(), this, mUserName);
        }

        return inflater.inflate(R.layout.fragment_task_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mTaskRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        updateAdapter();
    }


    private void findViews(View view) {
        mTaskRecyclerView = view.findViewById(R.id.recyclerView_task_list);
        mImageViewEmptyList = view.findViewById(R.id.imageView_empty_list);
        mTextViewEmptyList = view.findViewById(R.id.textView_empty_list);
    }


    private void updateAdapter() {

        mAdapter.setTasks(mTasks);
        mTaskRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        changeVisibility();
    }

    public void changeVisibility() {
        if (mAdapter.getItemCount() == 0) {
            mImageViewEmptyList.setVisibility(View.VISIBLE);
            mTextViewEmptyList.setVisibility(View.VISIBLE);
        } else {
            mImageViewEmptyList.setVisibility(View.GONE);
            mTextViewEmptyList.setVisibility(View.GONE);
        }
    }


}