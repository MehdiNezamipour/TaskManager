package com.example.gittest.repositories;

import com.example.gittest.enums.State;
import com.example.gittest.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements IRepository<Task> {

    private static TaskRepository sTaskRepository;

    private List<Task> mTasks;
    private ArrayList<Task> mTodoTasks;
    private ArrayList<Task> mDoingTasks;
    private ArrayList<Task> mDoneTasks;


    public static TaskRepository getInstance() {
        if (sTaskRepository == null) {
            sTaskRepository = new TaskRepository();
        }
        return sTaskRepository;
    }

    private TaskRepository() {
        mTasks = new ArrayList<>();
        mTodoTasks = new ArrayList<>();
        mDoingTasks = new ArrayList<>();
        mDoneTasks = new ArrayList<>();
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
        if (task.getTaskState() == State.TODO)
            mTodoTasks.add(task);
        else if (task.getTaskState() == State.DOING)
            mDoingTasks.add(task);
        else
            mDoneTasks.add(task);
    }

    public ArrayList<Task> getTodoTasks() {
        return mTodoTasks;
    }


    public ArrayList<Task> getDoingTasks() {
        return mDoingTasks;
    }


    public ArrayList<Task> getDoneTasks() {
        return mDoneTasks;
    }



}
