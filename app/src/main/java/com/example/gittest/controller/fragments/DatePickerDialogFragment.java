package com.example.gittest.controller.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.gittest.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerDialogFragment extends DialogFragment {

    public static final String EXTRA_USER_SELECTED_DATE = "userSelectedDate";
    private DatePicker mDatePicker;

    public DatePickerDialogFragment() {
        // Required empty public constructor
    }

    public static DatePickerDialogFragment newInstance() {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_date_picker_dialog, null);
        findViews(view);

        return new MaterialAlertDialogBuilder(getActivity())
                .setTitle(R.string.selectTime)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(getSelectedDate());
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private String getSelectedDate() {
        return mDatePicker.getYear() + " / " + mDatePicker.getMonth() + " / " + mDatePicker.getDayOfMonth();
    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.datePicker_task_date);
    }

    private void setResult(String date) {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_DATE, date);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}