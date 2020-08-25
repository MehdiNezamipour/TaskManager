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
import com.example.gittest.repositories.UserRepository;
import com.example.gittest.utils.adapters.UserListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserManageFragment extends Fragment {

    private UserRepository mUserRepository;
    private RecyclerView mRecyclerView;
    private UserListAdapter mAdapter;


    public UserManageFragment() {
        // Required empty public constructor
    }

    public static UserManageFragment newInstance() {
        UserManageFragment fragment = new UserManageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();

    }



    private void updateUi() {
        if (mAdapter == null)
            mAdapter = new UserListAdapter(getActivity());
        mAdapter.setUsers(mUserRepository.getList());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void findViews(@NonNull View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_task_list);
    }
}