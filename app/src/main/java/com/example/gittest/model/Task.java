package com.example.gittest.model;

import com.example.gittest.enums.State;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

public class Task implements Serializable {
    private UUID mTaskId;
    private static String mTaskName;
    private State mTaskState;
    private Random mRandom = new Random();


    public Task() {
        mTaskId = UUID.randomUUID();
        mTaskState = setRandomState();
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

    public static String getTaskName() {
        return mTaskName;
    }

    public State getTaskState() {
        return mTaskState;
    }

    public static void setTaskName(String taskName) {
        mTaskName = taskName;
    }

    public void setTaskState(State taskState) {
        mTaskState = taskState;
    }


}
