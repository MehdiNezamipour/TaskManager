package com.example.gittest.controller.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.gittest.R;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;
import com.example.gittest.repositories.TaskDBRepository;
import com.example.gittest.repositories.UserDBRepository;
import com.example.gittest.utils.PictureUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskDialogFragment extends DialogFragment {

    public static final String ARG_USER_NAME = "userName";
    public static final String DATE_PICKER_DIALOG_FRAGMENT_TAG = "datePickerDialogFragment";
    public static final String TIME_PICKER_DIALOG_FRAGMENT_TAG = "timePickerDialogFragment";
    public static final int REQUEST_CODE_TAKE_PICTURE = 0;
    public static final int REQUEST_CODE_PICK_PHOTO = 1;
    public static final int REQUEST_CODE_DATE_PICKER = 2;
    public static final int REQUEST_CODE_TIME_PICKER = 3;
    public static final String ARG_TASK = "task";
    private static final String FILEPROVIDER_AUTHORITY = "com.example.gittest.fileprovider";

    private EditText mEditTextTaskTitle;
    private EditText mEditTextTaskSubject;
    private Button mButtonDatePicker;
    private Button mButtonTimePicker;
    private ImageView mImageViewPhoto;
    private ImageButton mImageButtonTakePhoto;
    private RadioGroup mRadioGroupTaskState;
    private UserDBRepository mUserDBRepository;
    private TaskDBRepository mTaskDBRepository;
    private User mUser;
    private OnAddDialogDismissListener mListener;
    private Task mTask;

    private File mPhotoFile;


    public interface OnAddDialogDismissListener {
        void onListChanged();
    }

    public AddTaskDialogFragment() {
        // Required empty public constructor
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
        }

        mTask = new Task(mUser);
        mPhotoFile = mTask.getPhotoFile(getActivity(), mTask);

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


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_add_task_dialog, null);
        findViews(view);
        setListeners();
        initUi();

        return new MaterialAlertDialogBuilder(getActivity())
                .setTitle(R.string.addingTask)
                .setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setTaskFields(mTask);
                        mTaskDBRepository.insert(mTask);
                        mListener.onListChanged();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setCancelable(true)
                .create();
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
        } else if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            updatePhotoView();

            Uri photoUri = FileProvider.getUriForFile(
                    getActivity(),
                    FILEPROVIDER_AUTHORITY,
                    mPhotoFile);
            getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        } else if (requestCode == REQUEST_CODE_PICK_PHOTO) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                Cursor cursor = getActivity().getContentResolver().query(
                        selectedImage,
                        filePathColumn,
                        null,
                        null,
                        null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    if (picturePath != null) {
                        Bitmap bitmap = PictureUtil.getScaledBitmap(picturePath, getActivity());
                        mImageViewPhoto.setImageBitmap(bitmap);
                        mTask.setPhotoPath(picturePath);

                    } else
                        mImageViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_task));
                    cursor.close();
                }
            }
        }
    }

    private void initUi() {
        mEditTextTaskTitle.setText(null);
        mEditTextTaskSubject.setText(null);
        mButtonDatePicker.setText(R.string.selectDate);
        mButtonTimePicker.setText(R.string.selectTime);
        mRadioGroupTaskState.check(R.id.radioButton_task_todo);
        updatePhotoView();
    }

    private void findViews(View view) {
        mEditTextTaskTitle = view.findViewById(R.id.editText_task_title);
        mEditTextTaskSubject = view.findViewById(R.id.editText_task_subject);
        mButtonDatePicker = view.findViewById(R.id.button_datePicker);
        mButtonTimePicker = view.findViewById(R.id.button_timePicker);
        mRadioGroupTaskState = view.findViewById(R.id.radioGroup_task_state);
        mImageViewPhoto = view.findViewById(R.id.imageView_task_image);
        mImageButtonTakePhoto = view.findViewById(R.id.imageButton_take_image);
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
        mImageButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(getActivity());
            }
        });
    }


    private void updatePhotoView() {
        Bitmap bitmap = PictureUtil.getScaledBitmap(mPhotoFile.getPath(), getActivity());
        if (bitmap == null) {
            mImageViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_task));
        } else
            mImageViewPhoto.setImageBitmap(bitmap);
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

    //method for take photo of task
    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your task picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        if (mPhotoFile == null)
                            return;

                        Uri photoURI = FileProvider.getUriForFile(
                                getActivity(),
                                FILEPROVIDER_AUTHORITY,
                                mPhotoFile);

                        grantTemPermissionForTakePicture(takePictureIntent, photoURI);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PICTURE);
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, REQUEST_CODE_PICK_PHOTO);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void grantTemPermissionForTakePicture(Intent takePictureIntent, Uri photoURI) {
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(
                takePictureIntent,
                PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo activity : activities) {
            getActivity().grantUriPermission(activity.activityInfo.packageName,
                    photoURI,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
}