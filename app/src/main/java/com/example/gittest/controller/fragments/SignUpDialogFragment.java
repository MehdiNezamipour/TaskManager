package com.example.gittest.controller.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.gittest.R;
import com.example.gittest.model.User;
import com.example.gittest.repositories.IRepository;
import com.example.gittest.repositories.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpDialogFragment extends DialogFragment {

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private EditText mEditTextRepeatPassword;
    private UserRepository mUserRepository;


    public SignUpDialogFragment() {
        // Required empty public constructor
    }

    public static SignUpDialogFragment newInstance() {
        SignUpDialogFragment fragment = new SignUpDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_sign_up, null);
        findViews(view);

        return new MaterialAlertDialogBuilder(getActivity())
                .setTitle(R.string.sign_up)
                .setView(view)
                .setPositiveButton(R.string.sign_up, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (checkInputs()) {
                            User user = new User(mEditTextUserName.getText().toString(), mEditTextPassword.getText().toString());
                            mUserRepository.add(user);
                            Toast.makeText(getActivity(), user.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

    }

    private boolean checkInputs() {
        if (mEditTextUserName.getText().toString().trim().length() == 0 || mEditTextPassword.getText().toString().trim().length() < 8
                || !mEditTextPassword.getText().toString().trim().equals(mEditTextRepeatPassword.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Wrong Inputs", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.editText_signUp_userName);
        mEditTextPassword = view.findViewById(R.id.editText_signUp_password);
        mEditTextRepeatPassword = view.findViewById(R.id.editText_signUp_repeat_password);
    }

}