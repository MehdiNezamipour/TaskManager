package com.example.gittest.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.example.gittest.enums.State;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Task implements Serializable {

    private UUID mTaskId;
    private UUID mUserId;
    private String mTaskTitle;
    private String mTaskSubject;
    private State mTaskState;
    private String mDate;
    private String mTime;
    private boolean mEditable;


    public Task(User user) {
        mUserId = user.getId();
        mTaskId = UUID.randomUUID();
    }

    public Task(UUID taskId, UUID userId, String taskTitle, String taskSubject, State taskState, String date, String time, boolean editable) {
        mTaskId = taskId;
        mUserId = userId;
        mTaskTitle = taskTitle;
        mTaskSubject = taskSubject;
        mTaskState = taskState;
        mDate = date;
        mTime = time;
        mEditable = editable;
    }

    public UUID getTaskId() {
        return mTaskId;
    }


    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskSubject() {
        return mTaskSubject;
    }

    public void setTaskSubject(String taskSubject) {
        mTaskSubject = taskSubject;
    }

    public State getTaskState() {
        return mTaskState;
    }

    public void setTaskState(State taskState) {
        mTaskState = taskState;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public boolean getEditable() {
        return mEditable;
    }

    public void setEditable(boolean editable) {
        mEditable = editable;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return mTaskId.equals(task.mTaskId);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mTaskId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "mTaskTitle='" + mTaskTitle + '\'' +
                ", mTaskSubject='" + mTaskSubject + '\'' +
                ", mTaskState=" + mTaskState +
                ", mDate='" + mDate + '\'' +
                ", mTime='" + mTime + '\'' +
                '}';
    }
}
