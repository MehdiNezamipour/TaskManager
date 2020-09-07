package com.example.gittest.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.gittest.enums.Role;
import com.example.gittest.enums.State;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "userTable")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "userName")
    private String mUserName;
    @ColumnInfo(name = "passWord")
    private String mPassword;
    @ColumnInfo(name = "UUID")
    private UUID mUUID;
    @ColumnInfo(name = "date")
    private Date mDate;
    @ColumnInfo(name = "role")
    private Role mRole;

    public User(String userName, String password, Role role) {
        //mTasks = new ArrayList<>();
        mUserName = userName;
        mPassword = password;
        mRole = role;
        mUUID = UUID.randomUUID();
        mDate = new Date();

    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UUID getUUID() {
        return mUUID;
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

    public static class DateConverter {

        @TypeConverter
        public Long fromDate(Date value) {
            return value.getTime();
        }

        @TypeConverter
        public Date fromLong(Long value) {
            return new Date(value);
        }
    }

    public static class RoleConverter {

        @TypeConverter
        public String convertRoleToString(Role value) {
            return value.toString();
        }

        @TypeConverter
        public Role formString(String value) {
            switch (value) {
                case "ADMIN":
                    return Role.ADMIN;
                case "NORMAL":
                    return Role.NORMAL;
            }
            return null;
        }
    }

}
