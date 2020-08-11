package com.example.gittest.model;

import com.example.gittest.repositories.TaskRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {

    private String mUserName;
    private String mPassword;
    private UUID mId;
    private TaskRepository mTaskRepository;

    public User(String userName, String password) {
        mTaskRepository = new TaskRepository();
        mUserName = userName;
        mPassword = password;
        mId = UUID.randomUUID();
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }

    public TaskRepository getTaskRepository() {
        return mTaskRepository;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return mUserName.equals(user.mUserName) &&
                mPassword.equals(user.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUserName, mPassword);
    }

}
