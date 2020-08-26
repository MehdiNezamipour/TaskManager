package com.example.gittest.database.CursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.gittest.database.UserDBSchema;
import com.example.gittest.model.User;

import java.util.Date;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String stringUUID = getString(getColumnIndex(UserDBSchema.UserTable.COLS.UUID));
        String userName = getString(getColumnIndex(UserDBSchema.UserTable.COLS.USERNAME));
        String passWord = getString(getColumnIndex(UserDBSchema.UserTable.COLS.PASSWORD));
        Date date = new Date(getLong(getColumnIndex(UserDBSchema.UserTable.COLS.DATE)));
        User user = new User(userName, passWord, UUID.fromString(stringUUID), date);
        return user;
    }


}
