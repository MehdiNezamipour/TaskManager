package com.example.gittest.database;

public final class UserDBSchema {

    public static final String NAME = "UserDB.db";
    public static final int VERSION = 1;


    public static final class UserTable {
        public static final String NAME = "UserTable";

        public static final class COLS {

            public static final String USERNAME = "userName";
            public static final String PASSWORD = "passWord";
            public static final String UUID = "UUID";
            public static final String DATE = "Date";

        }
    }
}
