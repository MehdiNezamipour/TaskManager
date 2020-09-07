package com.example.gittest.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.gittest.model.Task;
import com.example.gittest.model.User;

@Database(entities = {Task.class, User.class}, version = 1, exportSchema = false)
@TypeConverters({Task.UUIDConverter.class, Task.StateConverter.class, User.DateConverter.class, User.RoleConverter.class})
public abstract class TaskManagerDataBase extends RoomDatabase {

    public abstract TaskDataBaseDAO taskDataBaseDAO();

    public abstract UserDateBaseDAO userDateBaseDAO();


}
