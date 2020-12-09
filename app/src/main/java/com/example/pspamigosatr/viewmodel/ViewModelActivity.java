package com.example.pspamigosatr.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pspamigosatr.model.Repository;
import com.example.pspamigosatr.model.entity.Call;
import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.model.entity.FriendNumberCall;

import java.util.List;

public class ViewModelActivity extends AndroidViewModel {

    private Repository repository;

    public ViewModelActivity(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }


    public void insert(Friend friend) { repository.insert(friend); }

    public void insert(Call call) { repository.insert(call); }

    public void update(Friend friend) { repository.update(friend); }

    public void update(Call call) { repository.update(call); }

    public LiveData<List<Friend>> getLiveFriendList() { return repository.getLiveFriendList(); }

    public LiveData<List<FriendNumberCall>> getLiveFriendNumberList() { return repository.getLiveFriendNumberList(); }

    public LiveData<List<Call>> getFriendCalls(long idfriend) { return repository.getFriendCalls(idfriend);}

    public List<Friend> getListFriends() { return repository.getListFriends(); }

    public void delete(Friend friend) {
        repository.delete(friend);
    }

    public void delete(Call call) {
        repository.delete(call);
    }

    public Friend getCurrent() { return repository.getCurrent(); }

    public void setCurrent(Friend current) {
        repository.setCurrent(current);
    }
}
