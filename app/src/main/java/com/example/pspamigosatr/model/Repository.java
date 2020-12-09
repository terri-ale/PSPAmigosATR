package com.example.pspamigosatr.model;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.pspamigosatr.model.dao.CallDao;
import com.example.pspamigosatr.model.dao.FriendDao;
import com.example.pspamigosatr.model.entity.Call;
import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.model.entity.FriendNumberCall;
import com.example.pspamigosatr.model.room.FriendDatabase;
import com.example.pspamigosatr.util.ThreadPool;

import java.util.List;

public class Repository {

    private CallDao callDao;
    private FriendDao friendDao;
    private Friend current;


    public Repository(Context context){
        FriendDatabase db=FriendDatabase.getDb(context);
        callDao=db.getCallDao();
        friendDao=db.getFriendDao();
    }


    public Friend getCurrent() { return current; }

    public void setCurrent(Friend current) { this.current = current; }

    public void insert(Friend friend){
        ThreadPool.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                friendDao.insert(friend);
            }
        });
    }


    public void insert(Call call){
        ThreadPool.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                callDao.insert(call);
            }
        });
    }



    public void update(Friend friend){
        ThreadPool.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                friendDao.update(friend);
            }
        });
    }


    public void update(Call call){
        ThreadPool.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                callDao.update(call);
            }
        });
    }


    public void delete(Friend friend){
        ThreadPool.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                friendDao.delete(friend);
            }
        });
    }


    public void delete(Call call){
        ThreadPool.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                callDao.delete(call);
            }
        });
    }



    public LiveData<List<Friend>> getLiveFriendList(){ return friendDao.getAllLive(); }

    public List<Friend> getListFriends() { return friendDao.getAllList(); }

    public Friend getFriendByPhone(String phone) { return friendDao.getByPhone(phone); }


    public LiveData<List<FriendNumberCall>> getLiveFriendNumberList(){ return friendDao.getAllWithCount(); }

    public LiveData<List<Call>> getFriendCalls(long idfriend){ return callDao.getCalls(idfriend); }

}
