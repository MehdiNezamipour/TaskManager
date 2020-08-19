package com.example.gittest.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gittest.database.CursorWrapper.TaskCursorWrapper;
import com.example.gittest.database.TaskDBSchema;
import com.example.gittest.database.TaskManagerBaseHelper;
import com.example.gittest.database.UserDBSchema;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements IRepository<Task> {


    private SQLiteDatabase mDatabase;
    private static Context sContext;
    private List<Task> mTodoTasks;
    private List<Task> mDoingTasks;
    private List<Task> mDoneTasks;
    private static TaskDBRepository sTaskRepository;


    private TaskDBRepository() {
        TaskManagerBaseHelper taskManagerBaseHelper = new TaskManagerBaseHelper(sContext);
        mDatabase = taskManagerBaseHelper.getWritableDatabase();
        mTodoTasks = new ArrayList<>();
        mDoingTasks = new ArrayList<>();
        mDoneTasks = new ArrayList<>();
    }

    public static TaskDBRepository getInstance(Context context) {
        sContext = context.getApplicationContext();
        if (sTaskRepository == null)
            sTaskRepository = new TaskDBRepository();
        return sTaskRepository;
    }

    public List<Task> getSpecialTaskList(State state, User user) {
        List<Task> answer = new ArrayList<>();
        for (Task task : getList(user)) {
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
        return null;
    }

    public List<Task> getList(User user) {
        List<Task> tasks = new ArrayList<>();
        String selection = TaskDBSchema.TaskTable.COLS.USER_ID + " =?";
        String[] selectionArgs = new String[]{user.getId().toString()};
        TaskCursorWrapper cursor = queryCrimes(selection, selectionArgs);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    private TaskCursorWrapper queryCrimes(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(TaskDBSchema.TaskTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return new TaskCursorWrapper(cursor);
    }

    @Override
    public Task get(UUID id) {
        return null;
    }


    @Override
    public void add(Task task) {
        ContentValues values = getTaskContentValue(task);
        mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);
    }

    @Override
    public void remove(Task task) {

        String where = TaskDBSchema.TaskTable.COLS.UUID + "=?";
        String[] whereArgs = new String[]{task.getTaskId().toString()};
        mDatabase.delete(UserDBSchema.UserTable.NAME, where, whereArgs);

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

    private ContentValues getTaskContentValue(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDBSchema.TaskTable.COLS.UUID, task.getTaskId().toString());
        values.put(TaskDBSchema.TaskTable.COLS.USER_ID, task.getUserId().toString());
        values.put(TaskDBSchema.TaskTable.COLS.TITLE, task.getTaskTitle());
        values.put(TaskDBSchema.TaskTable.COLS.SUBJECT, task.getTaskSubject());
        values.put(TaskDBSchema.TaskTable.COLS.STATE, task.getTaskState().toString());
        values.put(TaskDBSchema.TaskTable.COLS.DATE, task.getDate());
        values.put(TaskDBSchema.TaskTable.COLS.TIME, task.getTime());
        values.put(TaskDBSchema.TaskTable.COLS.EDITABLE, task.getEditable());
        return values;

    }


}
