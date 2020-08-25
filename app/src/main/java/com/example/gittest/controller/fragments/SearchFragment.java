package com.example.gittest.controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gittest.R;
import com.example.gittest.adapters.TaskSearchAdapter;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskDBRepository;
import com.example.gittest.repositories.UserDBRepository;

import java.util.ArrayList;
import java.util.List;

import static com.example.gittest.controller.fragments.AddTaskDialogFragment.ARG_USER_NAME;
import static com.example.gittest.controller.fragments.AddTaskDialogFragment.DATE_PICKER_DIALOG_FRAGMENT_TAG;
import static com.example.gittest.controller.fragments.AddTaskDialogFragment.REQUEST_CODE_DATE_PICKER;
import static com.example.gittest.controller.fragments.AddTaskDialogFragment.REQUEST_CODE_TIME_PICKER;
import static com.example.gittest.controller.fragments.AddTaskDialogFragment.TIME_PICKER_DIALOG_FRAGMENT_TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskSearchAdapter mAdapter;
    private EditText mEditTextSearchTitle;
    private EditText mEditTextSearchSubject;
    private Button mButtonSearchDate;
    private Button mButtonSearchTime;
    private Button mButtonSearch;

    private String mUserName;
    private TaskDBRepository mTaskDBRepository;
    private User mUser;


    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance(String userName) {
        SearchFragment fragment = new SearchFragment();
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

        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        mUser = UserDBRepository.getInstance(getActivity()).get(mUserName);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initUi();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mAdapter == null) {
            mAdapter = new TaskSearchAdapter(getActivity());
        }

        mRecyclerView.setAdapter(mAdapter);
        setListeners();


    }

    private void initUi() {
        mEditTextSearchTitle.setText(null);
        mEditTextSearchSubject.setText(null);
        mButtonSearchDate.setText(R.string.selectDate);
        mButtonSearchTime.setText(R.string.selectTime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            mButtonSearchDate.setText(data.getStringExtra(DatePickerDialogFragment.EXTRA_USER_SELECTED_DATE));
        } else if (requestCode == REQUEST_CODE_TIME_PICKER) {
            mButtonSearchTime.setText(data.getStringExtra(TimePickerDialogFragment.EXTRA_USER_SELECTED_TIME));
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView_search_result);
        mEditTextSearchTitle = view.findViewById(R.id.editText_search_title);
        mEditTextSearchSubject = view.findViewById(R.id.editText_search_subject);
        mButtonSearchDate = view.findViewById(R.id.button_search_date);
        mButtonSearchTime = view.findViewById(R.id.button_search_time);
        mButtonSearch = view.findViewById(R.id.button_search);
    }


    private void setListeners() {
        mButtonSearchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance();
                datePickerDialogFragment.setTargetFragment(SearchFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerDialogFragment.show(getFragmentManager(), DATE_PICKER_DIALOG_FRAGMENT_TAG);
            }
        });

        mButtonSearchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance();
                timePickerDialogFragment.setTargetFragment(SearchFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerDialogFragment.show(getFragmentManager(), TIME_PICKER_DIALOG_FRAGMENT_TAG);
            }
        });
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mEditTextSearchTitle.getText().toString();
                String subject = mEditTextSearchSubject.getText().toString();
                String date = mButtonSearchDate.getText().toString();
                String time = mButtonSearchTime.getText().toString();
                List<Task> tasks = new ArrayList<>(mTaskDBRepository.searchQuery(mUser, title, subject, date, time));
                mAdapter.setTasks(tasks);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

}