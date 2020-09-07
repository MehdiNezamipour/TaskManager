package com.example.gittest.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.gittest.enums.State;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "taskTable")
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "uuid")
    private UUID mTaskId;
    @ColumnInfo(name = "userId")
    private Long mUserId;
    @ColumnInfo(name = "title")
    private String mTaskTitle;
    @ColumnInfo(name = "subject")
    private String mTaskSubject;
    @ColumnInfo(name = "state")
    private State mTaskState;
    @ColumnInfo(name = "date")
    private String mDate;
    @ColumnInfo(name = "time")
    private String mTime;


    public Task() {
    }

    public Task(User user) {
        mUserId = user.getId();
        mTaskId = UUID.randomUUID();
    }


    public Long getId() {
        return id;
    }


    public UUID getTaskId() {
        return mTaskId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskId(UUID taskId) {
        mTaskId = taskId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskSubject() {
        return mTaskSubject;
    }

    public void setTaskSubject(String taskSubject) {
        mTaskSubject = taskSubject;
    }

    public State getTaskState() {
        return mTaskState;
    }

    public void setTaskState(State taskState) {
        mTaskState = taskState;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }


    public Long getUserId() {
        return mUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return mTaskId.equals(task.mTaskId);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mTaskId);
    }

    @Override
    public String toString() {
        return
                "Title : " + mTaskTitle + "\n" +
                        "Subject : " + mTaskSubject + "\n" +
                        "Date : " + mDate + "\n" +
                        "Time : " + mTime + "\n" +
                        "TaskState : " + mTaskState;

    }

    public static class UUIDConverter {

        @TypeConverter
        public String fromUUID(UUID value) {
            return value.toString();
        }

        @TypeConverter
        public UUID fromString(String value) {
            return UUID.fromString(value);
        }
    }

    public static class StateConverter {

        @TypeConverter
        public String fromState(State value) {
            return value.toString();
        }

        @TypeConverter
        public State formString(String value) {
            switch (value) {
                case "TODO":
                    return State.TODO;
                case "DOING":
                    return State.DOING;
                case "DONE":
                    return State.DONE;
            }
            return null;
        }
    }
}
