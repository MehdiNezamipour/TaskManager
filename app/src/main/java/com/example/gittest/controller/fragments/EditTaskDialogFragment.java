package com.example.gittest.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gittest.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTaskDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTaskDialogFragment extends Fragment {


    public EditTaskDialogFragment() {
        // Required empty public constructor
    }
    public static EditTaskDialogFragment newInstance(String param1, String param2) {
        EditTaskDialogFragment fragment = new EditTaskDialogFragment();
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
        return inflater.inflate(R.layout.fragment_edit_task_dialog, container, false);
    }
}