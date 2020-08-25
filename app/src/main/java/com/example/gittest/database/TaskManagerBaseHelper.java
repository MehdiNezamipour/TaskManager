package com.example.gittest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskManagerBaseHelper extends SQLiteOpenHelper {


    public TaskManagerBaseHelper(@Nullable Context context) {
        super(context, TaskDBSchema.NAME, null, TaskDBSchema.VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + UserDBSchema.UserTable.NAME + " (" +

                UserDBSchema.UserTable.COLS.UUID + " text Primary key ," +
                UserDBSchema.UserTable.COLS.USERNAME + " text," +
                UserDBSchema.UserTable.COLS.PASSWORD + " text," +
                UserDBSchema.UserTable.COLS.DATE + " long" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE " + TaskDBSchema.TaskTable.NAME + " (" +

                TaskDBSchema.TaskTable.COLS.UUID + " text Primary key," +
                TaskDBSchema.TaskTable.COLS.USER_ID + " text," +
                TaskDBSchema.TaskTable.COLS.TITLE + " text," +
                TaskDBSchema.TaskTable.COLS.SUBJECT + " text," +
                TaskDBSchema.TaskTable.COLS.STATE + " text," +
                TaskDBSchema.TaskTable.COLS.DATE + " text," +
                TaskDBSchema.TaskTable.COLS.TIME + " text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
