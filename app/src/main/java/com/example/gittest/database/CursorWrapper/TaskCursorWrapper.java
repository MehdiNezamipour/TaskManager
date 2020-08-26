package com.example.gittest.database.CursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.gittest.database.UserDBSchema;
import com.example.gittest.enums.State;
import com.example.gittest.model.Task;
import com.example.gittest.model.User;
import com.example.gittest.database.TaskDBSchema.*;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {

        String stringUUID = getString(getColumnIndex(TaskTable.COLS.UUID));
        String stringUserID = getString(getColumnIndex(TaskTable.COLS.USER_ID));
        String title = getString(getColumnIndex(TaskTable.COLS.TITLE));
        String subject = getString(getColumnIndex(TaskTable.COLS.SUBJECT));
        String date = getString(getColumnIndex(TaskTable.COLS.DATE));
        String time = getString(getColumnIndex(TaskTable.COLS.TIME));
        State state = State.valueOf(getString(getColumnIndex(TaskTable.COLS.STATE)));


        return new Task(UUID.fromString(stringUUID), UUID.fromString(stringUserID), title, subject, state, date, time);
    }
}
