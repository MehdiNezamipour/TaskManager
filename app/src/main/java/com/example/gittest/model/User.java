package com.example.gittest.model;


import com.example.gittest.enums.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {

    private String mUserName;
    private String mPassword;
    private UUID mId;
    private Date mDate;
    private Role mRole;

    public User(String userName, String password) {
        //mTasks = new ArrayList<>();
        mUserName = userName;
        mPassword = password;
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public User(String userName, String password, UUID id, Date date) {
        mUserName = userName;
        mPassword = password;
        mId = id;
        mDate = date;
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

    public Date getDate() {
        return mDate;
    }

    public Role getRole() {
        return mRole;
    }

    public void setRole(Role role) {
        mRole = role;
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
