package kg.geektech.taskapprestored.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kg.geektech.taskapprestored.models.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task ")
    LiveData<List<Task>> getAllLive();

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

//    @Query("SELECT * FROM task ORDER BY title ASC ")
//    LiveData<Task> getAllSortedLive();
//
//    @Query("SELECT * FROM task  ORDER BY title DESC ")
//    LiveData<Task> sortDesc();


}
