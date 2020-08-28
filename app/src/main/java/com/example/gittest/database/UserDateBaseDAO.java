package com.example.gittest.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gittest.model.Task;
import com.example.gittest.model.User;

import java.util.List;

@Dao
public interface UserDateBaseDAO {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM userTable")
    List<User> getUsers();

    @Query("SELECT * FROM userTable WHERE userName=:userName")
    User getUser(String userName);


    @Query("SELECT * FROM userTable WHERE id=:id")
    User getUser(Long id);

    @Query("DELETE FROM userTable")
    void deleteUsers();


}
