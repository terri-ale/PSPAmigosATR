package com.example.pspamigosatr.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pspamigosatr.model.dao.CallDao;
import com.example.pspamigosatr.model.dao.FriendDao;
import com.example.pspamigosatr.model.entity.Call;
import com.example.pspamigosatr.model.entity.Friend;

@Database(entities = {Friend.class, Call.class}, version = 1, exportSchema = false)
public abstract class FriendDatabase extends RoomDatabase {

    public abstract CallDao getCallDao();
    public abstract FriendDao getFriendDao();

    private volatile static FriendDatabase INSTANCE;

    public static synchronized FriendDatabase getDb(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    FriendDatabase.class, "dbfriend.sqlite")
                    .build();
        }
        return INSTANCE;
    }


}
