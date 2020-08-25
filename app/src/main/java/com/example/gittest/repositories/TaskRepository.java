package com.example.gittest.repositories;

import com.example.gittest.enums.State;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements IRepository<Task> {


    private List<Task> mTasks;
    private List<Task> mTodoTasks;
    private List<Task> mDoingTasks;
    private List<Task> mDoneTasks;
    private static TaskRepository sTaskRepository;


    private TaskRepository() {
        mTasks = new ArrayList<>();
        mTodoTasks = new ArrayList<>();
        mDoingTasks = new ArrayList<>();
        mDoneTasks = new ArrayList<>();
    }

    public static TaskRepository getInstance() {
        if (sTaskRepository == null)
            sTaskRepository = new TaskRepository();
        return sTaskRepository;
    }

    public List<Task> getSpecialTaskList(State state, User user) {
        List<Task> answer = new ArrayList<>();
        for (Task task : mTasks) {
            if (task.getUserId().equals(user.getId()) && task.getTaskState().equals(state))
                answer.add(task);
        }
        switch (state) {
            case TODO:
                mTodoTasks = answer;
                return mTodoTasks;
            case DOING:
                mDoingTasks = answer;
                return mDoingTasks;
            case DONE:
                mDoneTasks = answer;
                return mDoneTasks;
        }
        return null;
    }


    @Override
    public List<Task> getList() {
        return mTasks;
    }

    public List<Task> getList(User user) {
        List<Task> answer = new ArrayList<>();
        for (Task task : mTasks) {
            if (task.getUserId().equals(user.getId()))
                answer.add(task);
        }
        return answer;
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

    @Override
    public void remove(Task task) {
        mTasks.remove(task);
        if (task.getTaskState() == State.TODO)
            mTodoTasks.remove(task);
        else if (task.getTaskState() == State.DOING)
            mDoingTasks.remove(task);
        else
            mDoneTasks.remove(task);
    }

    @Override
    public void update(Task task) {
        Task oldTask = get(task.getTaskId());
        oldTask.setTaskTitle(task.getTaskTitle());
        oldTask.setDate(task.getDate());
        oldTask.setTime(task.getTime());
        oldTask.setTaskState(task.getTaskState());
        oldTask.setTaskSubject(task.getTaskSubject());
    }


    public void removeAllUserTasks(User user) {
        for (Task task : getList(user)) {
            remove(task);
        }
    }
}
