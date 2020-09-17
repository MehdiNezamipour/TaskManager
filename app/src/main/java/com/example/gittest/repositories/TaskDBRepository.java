package com.example.gittest.repositories;

import android.content.Context;

import androidx.room.Room;

import com.example.gittest.database.TaskManagerDataBase;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;

import java.io.File;
import java.util.List;

public class TaskDBRepository {


    private TaskManagerDataBase mDatabase;
    private static Context sContext;
    private static TaskDBRepository sTaskRepository;


    private TaskDBRepository() {
        mDatabase = Room.databaseBuilder(sContext, TaskManagerDataBase.class, "TaskDB.db")
                .allowMainThreadQueries()
                .build();

    }

    public static TaskDBRepository getInstance(Context context) {
        sContext = context.getApplicationContext();
        if (sTaskRepository == null)
            sTaskRepository = new TaskDBRepository();
        return sTaskRepository;
    }

    public List<Task> getSpecialTaskList(State state, Long userId) {
        return mDatabase.taskDataBaseDAO().getSpecialTaskList(state, userId);
    }

    public List<Task> getList(Long userId) {
        return mDatabase.taskDataBaseDAO().getUserTasks(userId);
    }

    public List<Task> getList(State state) {
        return mDatabase.taskDataBaseDAO().getList(state);
    }


    public List<Task> search(Long userId, String searchString) {
        return mDatabase.taskDataBaseDAO().searchQuery(userId, searchString);
    }


    public List<Task> getList() {
        return null;
    }


    public Task get(Long id) {
        return mDatabase.taskDataBaseDAO().getTask(id);
    }


    public void insert(Task task) {
        mDatabase.taskDataBaseDAO().insertTask(task);
    }


    public void delete(Task task) {
        mDatabase.taskDataBaseDAO().deleteTask(task);
    }


    public void deleteAll() {
        mDatabase.taskDataBaseDAO().deleteTasks();
    }

    public void removeAllUserTasks(long userId) {
        for (Task task : mDatabase.taskDataBaseDAO().getUserTasks(userId)) {
            delete(task);
        }
    }


    public void update(Task task) {
        mDatabase.taskDataBaseDAO().updateTask(task);
    }




}
