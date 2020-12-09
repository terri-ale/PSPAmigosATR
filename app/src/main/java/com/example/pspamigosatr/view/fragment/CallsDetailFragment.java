package com.example.pspamigosatr.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pspamigosatr.R;
import com.example.pspamigosatr.model.entity.Call;
import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.view.adapter.CallListAdapter;
import com.example.pspamigosatr.viewmodel.ViewModelActivity;

import java.util.ArrayList;
import java.util.List;


public class CallsDetailFragment extends Fragment {

    private Friend current;
    ViewModelActivity viewModelActivity;
    List<Call> listCalls=new ArrayList<>();

    public CallsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelActivity=new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
        current=viewModelActivity.getCurrent();
        init();
    }


    private void init(){
        TextView tvTitle=getView().findViewById(R.id.tvTitle);
        tvTitle.setText(current.getName());

        RecyclerView rvCalls=getView().findViewById(R.id.rvCalls);
        CallListAdapter adapter=new CallListAdapter(listCalls);
        rvCalls.setAdapter(adapter);
        rvCalls.setHasFixedSize(true);
        rvCalls.setLayoutManager(new LinearLayoutManager(getActivity()));

        TextView tvNoCalls=getView().findViewById(R.id.tvNoCalls);

        viewModelActivity.getFriendCalls(current.getId()).observe(getActivity(), new Observer<List<Call>>() {
            @Override
            public void onChanged(List<Call> calls) {

                if(calls.size()==0){ tvNoCalls.setVisibility(View.VISIBLE); } else { tvNoCalls.setVisibility(View.GONE); };

                listCalls.clear();
                listCalls.addAll(calls);
                adapter.notifyDataSetChanged();
            }
        });



    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calls_detail, container, false);
    }
}