package com.example.pspamigosatr.util;

import android.content.Context;
import android.telephony.PhoneNumberUtils;

import com.example.pspamigosatr.model.Repository;
import com.example.pspamigosatr.model.entity.Call;
import com.example.pspamigosatr.model.entity.Friend;

import java.util.List;

public class CallSaver {

    private String phone;
    private long dateCall;
    private Repository repository;

    public CallSaver(Context context, String phone, long dateCall){
        this.repository=new Repository(context);
        this.phone=phone;
        this.dateCall=dateCall;
    }


    public void save(){
        ThreadPool.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                List<Friend> list=repository.getListFriends();

                for (int i = 0; i < list.size() ; i++) {
                    if(PhoneNumberUtils.compare(list.get(i).getPhone(), phone)){
                        repository.insert(new Call(0, list.get(i).getId(), dateCall));
                    }
                }


                /*This way, "+34 666 66 66 66" would not be the same as "666666666" or "0034 666666666".
                That is why I always use PhoneNumberUtils to compare phone numbers

                Friend friend=repository.getFriendByPhone(phone);
                if(friend!=null){
                    repository.insert(new Call(0,friend.getId(), dateCall));
                }
                 */
            }
        });
    }


}
