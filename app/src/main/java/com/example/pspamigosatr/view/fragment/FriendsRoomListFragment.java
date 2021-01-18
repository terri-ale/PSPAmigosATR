package com.example.pspamigosatr.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pspamigosatr.R;
import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.model.entity.FriendNumberCall;
import com.example.pspamigosatr.view.adapter.FriendRoomAdapter;
import com.example.pspamigosatr.viewmodel.ViewModelActivity;

import java.util.ArrayList;
import java.util.List;


public class FriendsRoomListFragment extends Fragment implements FriendRoomAdapter.OnFriendActionListener {

    private String[] arrayPermissions={
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG
    };

    private static final int PERMISSIONS_CODE=1;

    private List<FriendNumberCall> friendList=new ArrayList<>();

    private ViewModelActivity viewModelActivity;


    public FriendsRoomListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts_room_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelActivity= new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
        init();
    }

    private void init() {
        getView().findViewById(R.id.btWarning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMissingPermissions();
            }
        });

        if (!allPermissionsGranted()){ setWarning(true); }


        RecyclerView rvContactsRoom = getView().findViewById(R.id.rvContactsRoom);

        FriendRoomAdapter adapter=new FriendRoomAdapter(friendList, this);
        rvContactsRoom.setAdapter(adapter);
        rvContactsRoom.setHasFixedSize(true);
        rvContactsRoom.setLayoutManager(new LinearLayoutManager(getActivity()));


        viewModelActivity.getLiveFriendNumberList().observe(getActivity(), new Observer<List<FriendNumberCall>>() {
            @Override
            public void onChanged(List<FriendNumberCall> friendNumberCalls) {
                friendList.clear();
                friendList.addAll(friendNumberCalls);
                adapter.notifyDataSetChanged();
            }
        });


        getView().findViewById(R.id.fabAddContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FriendsRoomListFragment.this)
                        .navigate(R.id.action_contactsRoomList_to_importContact);
            }
        });
    }


    @Override
    public void onDeleteListener(Friend friend) {
        viewModelActivity.delete(friend);
    }

    @Override
    public void onEditListener(Friend friend) {
        viewModelActivity.setCurrent(friend);
        NavHostFragment.findNavController(FriendsRoomListFragment.this)
                .navigate(R.id.action_contactsRoomList_to_editFriendFragment);
    }

    @Override
    public void onFriendClickListener(Friend friend) {
        viewModelActivity.setCurrent(friend);
        NavHostFragment.findNavController(FriendsRoomListFragment.this)
                .navigate(R.id.action_contactsRoomList_to_callsDetailFragment);
    }




    /* ----- PERMISSIONS ----- */

    public boolean allPermissionsGranted(){
        //this method checks check whether the permissions
        //are already granted before taking advantage of them. This avoids an error if
        // permissions have been revoked from android settings.
        //Resource efficiency: if version is previous than M, it will not make any further check.
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M) return true;
        for(String permission : arrayPermissions){
            //as soon as it finds any permission not granted, returns false
            if(getContext().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }


    private void requestMissingPermissions(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M) return;

        int result=PackageManager.PERMISSION_GRANTED;
        List<String> missingPermissions=new ArrayList<>();
        for(String permission : arrayPermissions){
            result=getContext().checkSelfPermission(permission);
            if(result!=PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }

        if (missingPermissions.size()>0) {
            String[] missingPermissions2=new String[missingPermissions.size()];
            for(int i=0;i<missingPermissions.size();i++)
                missingPermissions2[i]=missingPermissions.get(i);

            requestPermissions(missingPermissions2, PERMISSIONS_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int grantedCounter=0;
        switch (requestCode){
            case PERMISSIONS_CODE:
                for(int result : grantResults){
                    if(result==PackageManager.PERMISSION_GRANTED) grantedCounter++;
                }
                break;
        }
        if(grantedCounter==permissions.length){ setWarning(false); }
    }


    private void setWarning(boolean shouldShowWarning){
        if(shouldShowWarning){
            getView().findViewById(R.id.tvWarning).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.btWarning).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.fabAddContact).setVisibility(View.GONE);
        }else{
            getView().findViewById(R.id.tvWarning).setVisibility(View.GONE);
            getView().findViewById(R.id.btWarning).setVisibility(View.GONE);
            getView().findViewById(R.id.fabAddContact).setVisibility(View.VISIBLE);
        }
    }


}