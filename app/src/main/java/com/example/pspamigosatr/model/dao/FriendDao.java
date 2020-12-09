package com.example.pspamigosatr.model.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.model.entity.FriendNumberCall;

import java.util.List;

@Dao
public interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Friend friend);

    @Update
    int update(Friend friend);

    @Delete
    int delete(Friend friend);

    @Query("DELETE FROM friend WHERE id = :id")
    int delete(long id);

    @Query("SELECT * FROM friend WHERE id = :id")
    Friend get(long id);

    @Query("SELECT * FROM friend WHERE phone = :phone")
    Friend getByPhone(String phone);

    @Query("SELECT * FROM friend ORDER BY name")
    LiveData<List<Friend>> getAllLive();

    @Query("SELECT * FROM friend ORDER BY name")
    List<Friend> getAllList();


    /* This way, count value is 1 when actually there are no calls. I prefer subquery.
    @Query("SELECT f.id AS f_id, f.name AS f_name, f.phone AS f_phone, f.birthdate AS f_birthdate, count(*) AS count " +
            "FROM friend f " +
            "LEFT JOIN call c " +
            "ON f.id=c.idfriend " +
            "GROUP BY f.id, f.name, f.phone, f.birthdate")*/

    @Query("SELECT f.id AS f_id, f.name AS f_name, f.phone AS f_phone, f.birthdate AS f_birthdate, " +
            "(SELECT COUNT(*) FROM call c WHERE c.idfriend=f.id) AS count " +
                "FROM friend f " +
                "ORDER BY f.name ASC")
    LiveData<List<FriendNumberCall>> getAllWithCount();

}