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
import com.example.pspamigosatr.model.entity.Call;
import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.model.entity.FriendNumberCall;

import java.text.SimpleDateFormat;
import java.util.List;

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.CallListViewHolder> {

    private List<Call> list;


    public CallListAdapter(List<Call> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public CallListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_or_call, parent,false);
        return new CallListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallListViewHolder holder, int position) {
        holder.bind(list.get(position));
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnFriendActionListener{
        public void onDeleteListener(Friend friend);
        public void onEditListener(Friend friend);
    }



    public class CallListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate;
        private TextView tvHour;

        public CallListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvNameOrDate);
            tvHour=itemView.findViewById(R.id.tvPhoneOrHour);

        }

        public void bind(Call call){
            tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(call.getDatecall()));

            tvHour.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hour, 0, 0, 0);
            tvHour.setText(new SimpleDateFormat("hh:mm").format(call.getDatecall()));
        }

    }
}

