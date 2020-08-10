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

    private User mUser;
    private UUID mTaskId;
    private String mTaskTitle;
    private String mTaskSubject;
    private State mTaskState;
    private Random mRandom = new Random();
    private String mDate;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy / MM / dd      HH : mm   a");


    public Task() {
        mTaskId = UUID.randomUUID();
        mTaskState = setRandomState();
        Date date = new Date();
        mDate = mSimpleDateFormat.format(date);
    }

    private State setRandomState() {
        int random = mRandom.nextInt(3);
        if (random == 0)
            return State.TODO;
        else if (random == 1)
            return State.DOING;
        else
            return State.DONE;
    }

    public UUID getTaskId() {
        return mTaskId;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public State getTaskState() {
        return mTaskState;
    }

    public String getDate() {
        return mDate;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getTaskSubject() {
        return mTaskSubject;
    }

    public void setTaskSubject(String taskSubject) {
        mTaskSubject = taskSubject;
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
}
