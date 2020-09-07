package com.example.gittest.repositories;

import android.content.Context;

import androidx.room.Room;

import com.example.gittest.database.TaskManagerDataBase;
import com.example.gittest.model.User;

import java.util.List;

public class UserDBRepository {

    public static UserDBRepository sUserRepository;
    private TaskManagerDataBase mDatabase;
    private static Context sContext;

    private UserDBRepository() {
        mDatabase = Room.databaseBuilder(sContext, TaskManagerDataBase.class, "UserDB.db")
                .allowMainThreadQueries()
                .build();

    }

    public static UserDBRepository getInstance(Context context) {
        sContext = context.getApplicationContext();
        if (sUserRepository == null)
            sUserRepository = new UserDBRepository();
        return sUserRepository;
    }


    public List<User> getList() {
        return mDatabase.userDateBaseDAO().getUsers();
    }


    public User get(Long id) {

        return mDatabase.userDateBaseDAO().getUser(id);
    }

    public User get(String userName) {
        return mDatabase.userDateBaseDAO().getUser(userName);
    }


    public void add(User user) {
        mDatabase.userDateBaseDAO().insertUser(user);

    }


    public void remove(User user) {
        mDatabase.userDateBaseDAO().deleteUser(user);

    }

    public void removeAll() {
        mDatabase.userDateBaseDAO().deleteUsers();

    }


    public void update(User user) {
        mDatabase.userDateBaseDAO().updateUser(user);
    }

}
