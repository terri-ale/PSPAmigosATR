package com.example.pspamigosatr.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pspamigosatr.model.entity.Call;
import com.example.pspamigosatr.model.entity.Friend;

import java.util.List;

@Dao
public interface CallDao {

    @Delete
    int delete(Call call);

    @Query("DELETE FROM call WHERE id = :id")
    int delete(long id);

    @Query("SELECT * FROM call WHERE id = :id")
    Call get(long id);

    @Query("SELECT * FROM call WHERE idfriend = :id")
    LiveData<List<Call>> getCalls(long id);

    @Insert
    long insert(Call call);

    @Update
    int update(Call call);
    
}