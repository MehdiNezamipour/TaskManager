package com.example.gittest.repositories;

import com.example.gittest.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements IRepository<Task> {

    private static TaskRepository sTaskRepository;
    private List<Task> mTasks;
    private int mTasksSize;


    public static TaskRepository getInstance() {
        if (sTaskRepository == null) {
            sTaskRepository = new TaskRepository();
        }
        return sTaskRepository;
    }

    private TaskRepository() {
        mTasks = new ArrayList<>();
        for (int i = 0; i < mTasksSize; i++) {
            Task task = new Task();
            mTasks.add(task);
        }
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    public int getTasksSize() {
        return mTasksSize;
    }

    public void setTasksSize(int tasksSize) {
        mTasksSize = tasksSize;
    }

    @Override
    public List<Task> getList() {
        return mTasks;
    }

    @Override
    public Task get(UUID id) {
        for (Task task : mTasks) {
            if (task.getTaskId().equals(id))
                return task;
        }
        return null;
    }

    @Override
    public void add(Task task) {
        mTasks.add(task);
    }

}
