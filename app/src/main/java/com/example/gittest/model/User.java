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
    //private List<Task> mTasks;

    public User(String userName, String password) {
        //mTasks = new ArrayList<>();
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


    /*public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    public void addTask(Task task) {
        mTasks.add(task);
        TaskRepository.getInstance().add(task);
    }

    public void removeTask(Task task) {
        mTasks.remove(task);
        TaskRepository.getInstance().remove(task);
    }*/


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
