package com.example.gittest.model;

import com.example.gittest.enums.State;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Task implements Serializable {
    private UUID mTaskId;
    private String mTaskTitle;
    private State mTaskState;
    private Random mRandom = new Random();
    private String mDate;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat();


    public Task() {
        mTaskId = UUID.randomUUID();
        mTaskState = setRandomState();
        mSimpleDateFormat.applyPattern("yyyy / MM / dd - HH : mm  a");
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
}
