package com.example.pspamigosatr.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pspamigosatr.R;
import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.model.entity.FriendNumberCall;

import java.text.SimpleDateFormat;
import java.util.List;

public class FriendRoomAdapter extends RecyclerView.Adapter<FriendRoomAdapter.FriendRoomViewHolder> {

    private List<FriendNumberCall> list;
    private OnFriendActionListener listener;

    public FriendRoomAdapter(List<FriendNumberCall> list, OnFriendActionListener listener) {
        this.list=list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public FriendRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent,false);
        return new FriendRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRoomViewHolder holder, int position) {
        holder.bind(list.get(position));

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditListener(list.get(position).getFriend());
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteListener(list.get(position).getFriend());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFriendClickListener(list.get(position).getFriend());
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnFriendActionListener{
        public void onDeleteListener(Friend friend);
        public void onEditListener(Friend friend);
        public void onFriendClickListener(Friend friend);
    }



    public class FriendRoomViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPhone;
        private TextView tvBirthDate;
        private TextView tvTimes;
        public ImageButton btEdit;
        public ImageButton btDelete;

        public FriendRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvPhone=itemView.findViewById(R.id.tvPhone);
            tvBirthDate=itemView.findViewById(R.id.tvBirthDate);
            tvTimes=itemView.findViewById(R.id.tvTimes);
            btDelete=itemView.findViewById(R.id.btDelete);
            btEdit=itemView.findViewById(R.id.btEdit);
        }

        public void bind(FriendNumberCall f){
            tvName.setText(f.getFriend().getName());
            tvPhone.setText(f.getFriend().getPhone());
            if(f.getFriend().getBirthdate()==0){
                tvBirthDate.setVisibility(View.INVISIBLE);
            }else{
                tvBirthDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(f.getFriend().getBirthdate()));
            }

            //tvTimes.setText(String.valueOf(f.getCount()));
            tvTimes.setText(f.getCount()+"");
        }

    }
}
