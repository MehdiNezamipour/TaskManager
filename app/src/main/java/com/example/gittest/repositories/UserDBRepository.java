package com.example.gittest.repositories;

import android.content.Context;

import androidx.room.Room;

import com.example.gittest.database.TaskManagerDataBase;
import com.example.gittest.model.User;

import java.util.List;

public class UserDBRepository {

    public static UserDBRepository sUserRepository;
    //private List<User> mUsers;
    private TaskManagerDataBase mDatabase;
    private static Context sContext;

    private UserDBRepository() {
        /*TaskManagerBaseHelper taskManagerBaseHelper = new TaskManagerBaseHelper(sContext);
        mDatabase = taskManagerBaseHelper.getWritableDatabase();*/
        mDatabase = Room.databaseBuilder(sContext, TaskManagerDataBase.class, "UserDB.db")
                .allowMainThreadQueries()
                .build();

    }

    public static UserDBRepository getInstance(Context context) {
        sContext = context.getApplicationContext();
        if (sUserRepository == null)
            sUserRepository = new UserDBRepository();
        return sUserRepository;
    }


    public List<User> getList() {

        return mDatabase.userDateBaseDAO().getUsers();

        /*List<User> users = new ArrayList<>();
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
        return users;*/
    }

   /* private UserCursorWrapper queryCrimes(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(UserDBSchema.UserTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return new UserCursorWrapper(cursor);
    }*/


    public User get(Long id) {

        return mDatabase.userDateBaseDAO().getUser(id);

        /*   String selection = UserDBSchema.UserTable.COLS.UUID + "=?";
        String[] selectionArgs = new String[]{id.toString()};

        UserCursorWrapper cursor = queryCrimes(selection, selectionArgs);
        try {
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }*/
    }

    public User get(String userName) {
        return mDatabase.userDateBaseDAO().getUser(userName);

        /*String selection = UserDBSchema.UserTable.COLS.USERNAME + "=?";
        String[] selectionArgs = new String[]{userName};

        UserCursorWrapper cursor = queryCrimes(selection, selectionArgs);
        try {
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }*/
    }


    public void add(User user) {
        mDatabase.userDateBaseDAO().insertUser(user);

        /*ContentValues values = getCrimeContentValue(user);
        mDatabase.insert(UserDBSchema.UserTable.NAME, null, values);*/
    }


    public void remove(User user) {

        mDatabase.userDateBaseDAO().deleteUser(user);

        /*String where = UserDBSchema.UserTable.COLS.UUID + "=?";
        String[] whereArgs = new String[]{user.getUUID().toString()};
        mDatabase.delete(UserDBSchema.UserTable.NAME, where, whereArgs);*/
    }


    public void removeAll() {
        mDatabase.userDateBaseDAO().deleteUsers();
        //mDatabase.delete(UserDBSchema.UserTable.NAME, null, null);
    }


    public void update(User user) {
        mDatabase.userDateBaseDAO().updateUser(user);

        /*User oldUser = get(user.getUUID());
        oldUser.setUserName(user.getUserName());
        oldUser.setPassword(user.getPassword());*/
    }

  /*  private ContentValues getCrimeContentValue(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDBSchema.UserTable.COLS.UUID, user.getUUID().toString());
        values.put(UserDBSchema.UserTable.COLS.USERNAME, user.getUserName());
        values.put(UserDBSchema.UserTable.COLS.PASSWORD, user.getPassword());
        values.put(UserDBSchema.UserTable.COLS.DATE, user.getDate().getTime());

        return values;
    }*/
}
