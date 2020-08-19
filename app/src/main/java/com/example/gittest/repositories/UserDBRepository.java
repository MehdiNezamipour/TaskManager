package com.example.gittest.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gittest.database.CursorWrapper.UserCursorWrapper;
import com.example.gittest.database.TaskManagerBaseHelper;
import com.example.gittest.database.UserDBSchema;
import com.example.gittest.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository<User> {

    public static UserDBRepository sUserRepository;
    //private List<User> mUsers;
    private SQLiteDatabase mDatabase;
    private static Context sContext;

    private UserDBRepository() {
        TaskManagerBaseHelper taskManagerBaseHelper = new TaskManagerBaseHelper(sContext);
        mDatabase = taskManagerBaseHelper.getWritableDatabase();
    }

    public static UserDBRepository getInstance(Context context) {
        sContext = context.getApplicationContext();
        if (sUserRepository == null)
            sUserRepository = new UserDBRepository();
        return sUserRepository;
    }

    @Override
    public List<User> getList() {
        List<User> users = new ArrayList<>();
        UserCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return users;
    }

    private UserCursorWrapper queryCrimes(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(UserDBSchema.UserTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return new UserCursorWrapper(cursor);
    }

    @Override
    public User get(UUID id) {
        String selection = UserDBSchema.UserTable.COLS.UUID + "=?";
        String[] selectionArgs = new String[]{id.toString()};

        UserCursorWrapper cursor = queryCrimes(selection, selectionArgs);
        try {
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public User get(String userName) {
        String selection = UserDBSchema.UserTable.COLS.USERNAME + "=?";
        String[] selectionArgs = new String[]{userName};

        UserCursorWrapper cursor = queryCrimes(selection, selectionArgs);
        try {
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    @Override
    public void add(User user) {
        ContentValues values = getCrimeContentValue(user);
        mDatabase.insert(UserDBSchema.UserTable.NAME, null, values);
    }


    @Override
    public void remove(User user) {
        String where = UserDBSchema.UserTable.COLS.UUID + "=?";
        String[] whereArgs = new String[]{user.getId().toString()};
        mDatabase.delete(UserDBSchema.UserTable.NAME, where, whereArgs);
    }

    @Override
    public void update(User user) {
        User oldUser = get(user.getId());
        oldUser.setUserName(user.getUserName());
        oldUser.setPassword(user.getPassword());
    }

    private ContentValues getCrimeContentValue(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDBSchema.UserTable.COLS.UUID, user.getId().toString());
        values.put(UserDBSchema.UserTable.COLS.USERNAME, user.getUserName());
        values.put(UserDBSchema.UserTable.COLS.PASSWORD, user.getPassword());
        values.put(UserDBSchema.UserTable.COLS.DATE, user.getDate().getTime());

        return values;
    }
}
