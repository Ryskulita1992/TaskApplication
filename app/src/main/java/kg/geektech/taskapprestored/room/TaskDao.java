package kg.geektech.taskapprestored.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kg.geektech.taskapprestored.models.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllLive();

    @Insert
    void insert(Task task);

    @Query("UPDATE task Set title = :newTitle, `desc` = :newDesc WHERE id IN (:idList)")
    void update(int idList ,String newTitle, String newDesc);


    @Query("DELETE from task WHERE id IN (:idList)")
    void deleteByIdList(int idList);
//
//    @Query("UPDATE task Set title = :newTitle, `desc` = :newDesc WHERE id IN (:idList)")
//    void updateSalaryByIdList(int idList ,String newTitle, String newDesc);

}