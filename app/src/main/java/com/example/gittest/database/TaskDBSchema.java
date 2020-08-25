package com.example.gittest.database;

public final class TaskDBSchema {

    public static final String NAME = "TaskDB.db";
    public static final int VERSION = 1;


    public static final class TaskTable {
        public static final String NAME = "TaskTable";

        public static final class COLS {

            public static final String UUID = "UUID";
            public static final String USER_ID = "UserID";
            public static final String TITLE = "Title";
            public static final String SUBJECT = "Subject";
            public static final String STATE = "State";
            public static final String DATE = "Date";
            public static final String TIME = "Time";

        }
    }
}
