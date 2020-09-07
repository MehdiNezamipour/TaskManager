package com.example.gittest.controller.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.gittest.R;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskDBRepository;
import com.example.gittest.repositories.UserDBRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends DialogFragment {

    public static final String ARG_USER = "user";
    private TextView mTextViewUserName;
    private TextView mTextViewTaskNumber;
    private TextView mTextViewSignUpDate;
    private User mUser;
    private TaskDBRepository mTaskDBRepository;


    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(User user) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mUser = (User) getArguments().getSerializable(ARG_USER);
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_user_detail, null);
        findViews(view);
        mTextViewUserName.setText(String.format("UserName : %s", mUser.getUserName()));
        mTextViewTaskNumber.setText("Number of Tasks : " + mTaskDBRepository.getList(mUser.getId()).size());
        mTextViewSignUpDate.setText(String.format("SignUp Date : %s", mUser.getDate().toString()));
        return new MaterialAlertDialogBuilder(getActivity())
                .setTitle(R.string.userInfo)
                .setView(view)
                .setPositiveButton(R.string.close, null)
                .create();
    }

    private void findViews(View view) {
        mTextViewUserName = view.findViewById(R.id.textView_userName_manage);
        mTextViewTaskNumber = view.findViewById(R.id.textView_user_task_number);
        mTextViewSignUpDate = view.findViewById(R.id.textView_user_signUp_date);

    }
}