package com.example.pspamigosatr.view.fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pspamigosatr.R;
import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.view.adapter.ContactAdapter;
import com.example.pspamigosatr.viewmodel.ViewModelActivity;

import java.util.List;


public class ImportContactFragment extends Fragment implements ContactAdapter.OnContactClickListener {

    private ViewModelActivity viewModelActivity;

    public ImportContactFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_import_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelActivity=new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
        init();
    }


    private void init() {

        ContentResolver cr = getActivity().getContentResolver();


        String[] projection=new String[]{
                ContactsContract.Data._ID,
                ContactsContract.Data.DISPLAY_NAME
        };

        //I only look for contacts with phone number(s) and with not empty display names
        String selection= ContactsContract.Contacts.HAS_PHONE_NUMBER+" > 0 AND "+ContactsContract.Contacts.DISPLAY_NAME+" != ''";


        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                projection, selection, null, null);


        if ((cur != null ? cur.getCount() : 0) > 0) {

            getView().findViewById(R.id.tvNoContacts).setVisibility(View.GONE);

            RecyclerView rvContact=getView().findViewById(R.id.rvContactList);


            ContactAdapter adapter=new ContactAdapter(cur, getActivity(), this);

            rvContact.setAdapter(adapter);
            rvContact.setHasFixedSize(true);
            rvContact.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
    }


    @Override
    public void onContactClick(Friend f, List<String> phones) {
        //If the selected contact has only 1 phone number, I insert it directly
        //Otherwise I let the user choose the friend's phone number
        if(phones.size()==1){
            f.setPhone(phones.get(0));
            viewModelActivity.insert(f);
            NavHostFragment.findNavController(ImportContactFragment.this).popBackStack();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            String[] phones2=new String[phones.size()];

            for (int i = 0; i < phones.size(); i++) { phones2[i]=phones.get(i); }

            builder.setTitle(getContext().getString(R.string.string_selectphone))
                    .setSingleChoiceItems(phones2, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            f.setPhone(phones2[which]);
                            viewModelActivity.insert(f);
                            dialog.dismiss();
                            NavHostFragment.findNavController(ImportContactFragment.this).popBackStack();
                        }
                    });
            builder.show();
        }




    }
}