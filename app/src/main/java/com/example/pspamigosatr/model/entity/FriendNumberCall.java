package com.example.pspamigosatr.model.entity;

import androidx.room.Embedded;

public class FriendNumberCall {

    @Embedded(prefix = "f_")
    private Friend friend;

    private int count;

    public FriendNumberCall(){}

    public Friend getFriend() { return friend; }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "FriendNumberCall{" +
                "friend=" + friend +
                ", count=" + count +
                '}';
    }
}



