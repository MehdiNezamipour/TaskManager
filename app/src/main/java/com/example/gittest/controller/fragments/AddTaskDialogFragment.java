package com.example.gittest.controller.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.gittest.R;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskDBRepository;
import com.example.gittest.repositories.UserDBRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskDialogFragment extends DialogFragment {

    public static final String ARG_USER_NAME = "userName";
    public static final String DATE_PICKER_DIALOG_FRAGMENT_TAG = "datePickerDialogFragment";
    public static final String TIME_PICKER_DIALOG_FRAGMENT_TAG = "timePickerDialogFragment";
    public static final int REQUEST_CODE_DATE_PICKER = 2;
    public static final int REQUEST_CODE_TIME_PICKER = 3;
    public static final String ARG_TASK = "task";
    private EditText mEditTextTaskTitle;
    private EditText mEditTextTaskSubject;
    private Button mButtonDatePicker;
    private Button mButtonTimePicker;
    private RadioGroup mRadioGroupTaskState;
    private UserDBRepository mUserDBRepository;
    private TaskDBRepository mTaskDBRepository;
    private User mUser;
    private OnAddDialogDismissListener mListener;
    private Task mTask;

    public interface OnAddDialogDismissListener {
        void onDismiss();
    }

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    public static AddTaskDialogFragment newInstance(String userName, Task task) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        args.putSerializable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    public static AddTaskDialogFragment newInstance(String userName) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDBRepository = UserDBRepository.getInstance(getActivity());
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        if (getArguments() != null) {
            mUser = mUserDBRepository.get(getArguments().getString(ARG_USER_NAME));
            mTask = (Task) getArguments().getSerializable(ARG_TASK);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnAddDialogDismissListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnAddDialogDismissListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            mButtonDatePicker.setText(data.getStringExtra(DatePickerDialogFragment.EXTRA_USER_SELECTED_DATE));
        } else if (requestCode == REQUEST_CODE_TIME_PICKER) {
            mButtonTimePicker.setText(data.getStringExtra(TimePickerDialogFragment.EXTRA_USER_SELECTED_TIME));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_add_task_dialog, null);
        findViews(view);
        setListeners();

        if (mTask == null) {
            initUi();
            return new MaterialAlertDialogBuilder(getActivity())
                    .setTitle(R.string.addingTask)
                    .setView(view)
                    .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mTask = new Task(mUser);
                            setTaskFields(mTask);
                            mTaskDBRepository.add(mTask);
                            //mUser.addTask(mTask);
                            mListener.onDismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .setCancelable(true)
                    .create();
        } else {
            setEnable(false);
            initUi();
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(getActivity())
                    .setTitle(R.string.editTask)
                    .setView(view)
                    .setPositiveButton(R.string.save, (dialogInterface, i) -> {
                        setTaskFields(mTask);
                        mTaskDBRepository.update(mTask);
                        mListener.onDismiss();

                    })
                    .setNegativeButton(R.string.edit, (dialogInterface, i) -> {

                    })
                    .setNeutralButton(R.string.remove, (dialogInterface, i) -> {
                        mTaskDBRepository.remove(mTask);
                        //mUser.removeTask(mTask);
                        mListener.onDismiss();
                        dismiss();
                    }).create();

            alertDialog.setOnShowListener(dialogInterface -> {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        setEnable(true);
                    }
                });
            });
            return alertDialog;
        }
    }

    private void setTaskFields(Task task) {
        task.setTaskTitle(mEditTextTaskTitle.getText().toString());
        task.setTaskSubject(mEditTextTaskSubject.getText().toString());
        task.setDate(mButtonDatePicker.getText().toString());
        task.setTime(mButtonTimePicker.getText().toString());
        switch (mRadioGroupTaskState.getCheckedRadioButtonId()) {
            case R.id.radioButton_task_todo:
                task.setTaskState(State.TODO);
                break;
            case R.id.radioButton_task_doing:
                task.setTaskState(State.DOING);
                break;
            case R.id.radioButton_task_done:
                task.setTaskState(State.DONE);
                break;
            default:
                break;
        }
    }

    private void setEnable(boolean b) {
        mEditTextTaskTitle.setEnabled(b);
        mEditTextTaskSubject.setEnabled(b);
        mButtonTimePicker.setEnabled(b);
        mButtonDatePicker.setEnabled(b);
        for (int i = 0; i < mRadioGroupTaskState.getChildCount(); i++) {
            mRadioGroupTaskState.getChildAt(i).setEnabled(b);
        }
    }

    private void setListeners() {
        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance();
                datePickerDialogFragment.setTargetFragment(AddTaskDialogFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerDialogFragment.show(getFragmentManager(), DATE_PICKER_DIALOG_FRAGMENT_TAG);
            }
        });

        mButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance();
                timePickerDialogFragment.setTargetFragment(AddTaskDialogFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerDialogFragment.show(getFragmentManager(), TIME_PICKER_DIALOG_FRAGMENT_TAG);
            }
        });
    }

    private void initUi() {
        if (mTask != null) {
            mEditTextTaskTitle.setText(mTask.getTaskTitle());
            mEditTextTaskSubject.setText(mTask.getTaskSubject());
            mButtonDatePicker.setText(mTask.getDate());
            mButtonTimePicker.setText(mTask.getTime());
            switch (mTask.getTaskState()) {
                case TODO:
                    mRadioGroupTaskState.check(R.id.radioButton_task_todo);
                    break;
                case DOING:
                    mRadioGroupTaskState.check(R.id.radioButton_task_doing);
                    break;
                case DONE:
                    mRadioGroupTaskState.check(R.id.radioButton_task_done);
                    break;
                default:
                    break;
            }
        } else {
            mEditTextTaskTitle.setText(null);
            mEditTextTaskSubject.setText(null);
            mButtonDatePicker.setText(R.string.selectDate);
            mButtonTimePicker.setText(R.string.selectTime);
            mRadioGroupTaskState.check(R.id.radioButton_task_todo);
        }
    }

    private void findViews(View view) {
        mEditTextTaskTitle = view.findViewById(R.id.editText_task_title);
        mEditTextTaskSubject = view.findViewById(R.id.editText_task_subject);
        mButtonDatePicker = view.findViewById(R.id.button_datePicker);
        mButtonTimePicker = view.findViewById(R.id.button_timePicker);
        mRadioGroupTaskState = view.findViewById(R.id.radioGroup_task_state);
    }
}