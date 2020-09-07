package com.example.gittest.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gittest.enums.State;
import com.example.gittest.model.Task;

import java.util.List;

@Dao
public interface TaskDataBaseDAO {

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM taskTable WHERE id =:id")
    Task getTask(Long id);

    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE userId=:userId")
    List<Task> getUserTasks(Long userId);

    @Query("SELECT * FROM taskTable WHERE state =:state AND userId=:userId")
    List<Task> getSpecialTaskList(State state, Long userId);

    @Query("SELECT * FROM taskTable WHERE userId=:userId AND title LIKE +'%'+:searchString+'%' OR subject LIKE +'%'+:searchString+'%'")
    List<Task> searchQuery(Long userId, String searchString);

    @Query("DELETE FROM taskTable")
    void deleteTasks();

    @Query("SELECT * FROM taskTable WHERE state=:state")
    List<Task> getList(State state);
}
