package com.example.gittest.controller.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.gittest.R;
import com.example.gittest.enums.Role;
import com.example.gittest.model.User;
import com.example.gittest.repositories.UserDBRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpDialogFragment extends DialogFragment {

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private EditText mEditTextRepeatPassword;
    private CheckBox mCheckBoxAdmin;
    private UserDBRepository mUserDBRepository;


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
        mUserDBRepository = UserDBRepository.getInstance(getActivity());
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
                            Role role;
                            if (mCheckBoxAdmin.isChecked()) {
                                role = Role.ADMIN;
                            } else {
                                role = Role.NORMAL;
                            }
                            User user = new User(mEditTextUserName.getText().toString(), mEditTextPassword.getText().toString(), role);
                            mUserDBRepository.add(user);
                            Toast.makeText(getActivity(), R.string.successful_signup, Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

    }


    private boolean checkInputs() {
        if (mEditTextUserName.getText().toString().trim().length() == 0
                || mEditTextPassword.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Empty username or password !", Toast.LENGTH_SHORT).show();
            return false;
        } else if (checkUserNameExist(mEditTextUserName.getText().toString())) {
            Toast.makeText(getActivity(), "This username is Exist !", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mEditTextPassword.getText().toString().trim().equals(mEditTextRepeatPassword.getText().toString().trim())) {
            Toast.makeText(getActivity(), "passwords not same", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private boolean checkUserNameExist(String userName) {
        for (User user : mUserDBRepository.getList()) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.editText_singUp_username);
        mEditTextPassword = view.findViewById(R.id.editText_singUp_password);
        mEditTextRepeatPassword = view.findViewById(R.id.editText_singUp_repeat_password);
        mCheckBoxAdmin = view.findViewById(R.id.checkBox_signUp_as_admin);
    }

}