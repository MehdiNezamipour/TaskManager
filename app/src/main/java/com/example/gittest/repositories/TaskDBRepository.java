package com.example.gittest.repositories;

import android.content.Context;

import androidx.room.Room;

import com.example.gittest.database.TaskManagerDataBase;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;

import java.util.List;

public class TaskDBRepository  {


    private TaskManagerDataBase mDatabase;
    private static Context sContext;
    private static TaskDBRepository sTaskRepository;


    private TaskDBRepository() {
        /*TaskManagerBaseHelper taskManagerBaseHelper = new TaskManagerBaseHelper(sContext);
        mDatabase = taskManagerBaseHelper.getWritableDatabase();*/
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

       /* List<Task> tasks = new ArrayList<>();
        String selection = TaskDBSchema.TaskTable.COLS.USER_ID + " =?" + " AND " + TaskDBSchema.TaskTable.COLS.STATE + " =?";
        String[] selectionArgs = new String[]{user.getUUID().toString(), state.toString()};
        TaskCursorWrapper cursor = queryTasks(selection, selectionArgs);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;*/
    }

    public List<Task> getList(Long userId) {
        return mDatabase.taskDataBaseDAO().getUserTasks(userId);
        /* List<Task> tasks = new ArrayList<>();
        String selection = TaskDBSchema.TaskTable.COLS.USER_ID + " =?";
        String[] selectionArgs = new String[]{user.getUUID().toString()};
        TaskCursorWrapper cursor = queryTasks(selection, selectionArgs);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;*/
    }

    /*private TaskCursorWrapper queryTasks(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(true, TaskDBSchema.TaskTable.NAME, null,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);
        return new TaskCursorWrapper(cursor);
    }*/

    public List<Task> search(Long userId, String searchString) {
        return mDatabase.taskDataBaseDAO().searchQuery(userId, searchString);
    }

   /* public List<Task> searchQuery(User user, String title, String subject, String date, String time) {
        List<Task> tasks = new ArrayList<>();
        StringBuilder selection = new StringBuilder();
        List<String> selectionArg = new ArrayList<>();

        if (user != null) {
            selection.append(TaskDBSchema.TaskTable.COLS.USER_ID + " = ? " + " AND ");
            selectionArg.add(user.getUUID().toString());
        }
        if (!title.equals("")) {
            selection.append(TaskDBSchema.TaskTable.COLS.TITLE + " LIKE ? " + " AND ");
            selectionArg.add("%" + title + "%");
        }
        if (!subject.equals("")) {
            selection.append(TaskDBSchema.TaskTable.COLS.SUBJECT + " LIKE ? " + " AND ");
            selectionArg.add("%" + subject + "%");
        }
        if (!date.equalsIgnoreCase("select date")) {
            selection.append(TaskDBSchema.TaskTable.COLS.DATE + " = ? " + " AND ");
            selectionArg.add(date);
        }
        if (!time.equalsIgnoreCase("select time")) {
            selection.append(TaskDBSchema.TaskTable.COLS.TIME + " = ? " + "     ");
            selectionArg.add(time);
        }
        selection.delete(selection.length() - 4, selection.length());
        String[] array = new String[selectionArg.size()];
        selectionArg.toArray(array);

        TaskCursorWrapper taskCursorWrapper = new TaskCursorWrapper(queryTasks(selection.toString(), array));
        try {
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()) {
                tasks.add(taskCursorWrapper.getTask());
                taskCursorWrapper.moveToNext();
            }
        } finally {
            taskCursorWrapper.close();
        }
        return tasks;
    }
*/


    public List<Task> getList() {
        return null;
    }


    public Task get(Long id) {
        return mDatabase.taskDataBaseDAO().getTask(id);
        /* Task task = null;
        String selection = TaskDBSchema.TaskTable.COLS.UUID + " =?";
        String[] selectionArgs = new String[]{id.toString()};
        TaskCursorWrapper cursor = queryTasks(selection, selectionArgs);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                task = cursor.getTask();
                cursor.moveToNext();
                break;
            }
        } finally {
            cursor.close();
        }
        return task;*/
    }


    public void add(Task task) {
       mDatabase.taskDataBaseDAO().insertTask(task);
        /* ContentValues values = getTaskContentValue(task);
        mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);*/
    }


    public void remove(Task task) {

        mDatabase.taskDataBaseDAO().deleteTask(task);
        /* String where = TaskDBSchema.TaskTable.COLS.UUID + " =?";
        String[] whereArgs = new String[]{task.getTaskId().toString()};
        mDatabase.delete(TaskDBSchema.TaskTable.NAME, where, whereArgs);*/
    }


    public void removeAll() {
        mDatabase.taskDataBaseDAO().deleteTasks();
        //mDatabase.delete(TaskDBSchema.TaskTable.NAME, null, null);
    }

    public void removeAllUserTasks(long userId) {
        for (Task task : mDatabase.taskDataBaseDAO().getUserTasks(userId)) {
            remove(task);
        }
    }


    public void update(Task task) {
       mDatabase.taskDataBaseDAO().updateTask(task);
        /* ContentValues contentValues = getTaskContentValue(task);
        String where = TaskDBSchema.TaskTable.COLS.UUID + " =? ";
        String[] whereAgs = new String[]{task.getTaskId().toString()};
        mDatabase.update(TaskDBSchema.TaskTable.NAME, contentValues, where, whereAgs);*/
    }


 /*   private ContentValues getTaskContentValue(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDBSchema.TaskTable.COLS.UUID, task.getTaskId().toString());
        values.put(TaskDBSchema.TaskTable.COLS.USER_ID, task.getUserId().toString());
        values.put(TaskDBSchema.TaskTable.COLS.TITLE, task.getTaskTitle());
        values.put(TaskDBSchema.TaskTable.COLS.SUBJECT, task.getTaskSubject());
        values.put(TaskDBSchema.TaskTable.COLS.STATE, task.getTaskState().toString());
        values.put(TaskDBSchema.TaskTable.COLS.DATE, task.getDate());
        values.put(TaskDBSchema.TaskTable.COLS.TIME, task.getTime());
        return values;

    }*/


}
