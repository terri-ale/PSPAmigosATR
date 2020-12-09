package com.example.pspamigosatr.view.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pspamigosatr.R;
import com.example.pspamigosatr.model.entity.Friend;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Cursor cursor;
    private Context context;
    private ContentResolver cr;
    private OnContactClickListener listener;


    public ContactAdapter(Cursor cursor, Context context, OnContactClickListener listener){
        this.cursor=cursor;
        this.context=context;
        cr=context.getContentResolver();
        this.listener=listener;
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_or_call, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onContactClick(holder.toFriend(), holder.phones);
            }
        });
        //cursor.close();
    }



    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public interface OnContactClickListener{
        public void onContactClick(Friend f, List<String> phones);
    }




    public class ContactViewHolder extends RecyclerView.ViewHolder{

        private String id;
        public View view;
        private TextView tvName;
        private TextView tvPhone;


        public List<String> phones=new ArrayList<>();

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            tvName=itemView.findViewById(R.id.tvNameOrDate);
            tvPhone=itemView.findViewById(R.id.tvPhoneOrHour);

        }

        public void bind(int position){
            cursor.moveToPosition(position);
            tvName.setText(cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME)));
            id=cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));



            Cursor phoneCursor = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{id}, null);


            String currentPhone;
            phoneCursor.moveToNext();
            currentPhone=phoneCursor.getString(phoneCursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));

            phones.add(currentPhone);
            tvPhone.setText(currentPhone);


            while(phoneCursor != null && phoneCursor.moveToNext()){
                currentPhone=phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                phones.add(currentPhone);
                tvPhone.setText(context.getText(R.string.string_severalphones));
            }

            phoneCursor.close();

        }


        public Friend toFriend(){
            return new Friend(Long.parseLong(id), tvName.getText().toString(), null, 0l);

        }

    }
}
