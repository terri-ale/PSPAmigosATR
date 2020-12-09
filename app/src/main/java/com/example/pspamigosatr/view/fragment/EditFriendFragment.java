package com.example.pspamigosatr.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.pspamigosatr.R;
import com.example.pspamigosatr.model.entity.Friend;
import com.example.pspamigosatr.viewmodel.ViewModelActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class EditFriendFragment extends Fragment implements CalendarView.OnDateChangeListener{

    private ViewModelActivity viewModelActivity;
    TextInputLayout tiName, tiPhone;
    private CalendarView cvBirthdate;
    private long selectedDate=0l;
    private Friend current;


    public EditFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelActivity=new ViewModelProvider(getActivity()).get(ViewModelActivity.class);
        current=viewModelActivity.getCurrent();
        init();
    }

    private void init() {

        tiName =getView().findViewById(R.id.tiName);
        tiPhone =getView().findViewById(R.id.tiPhone);
        cvBirthdate=getView().findViewById(R.id.cvBirthdate);

        if(current.getBirthdate() != 0) {
            cvBirthdate.setDate(current.getBirthdate());
            selectedDate=current.getBirthdate();
        };

        tiName.getEditText().setText(current.getName());

        tiPhone.getEditText().setText(current.getPhone());

        cvBirthdate.setOnDateChangeListener(this);


        getView().findViewById(R.id.btSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSave();
            }
        });

    }


    private void attemptSave(){
        boolean allRight=true;

        tiName.setErrorEnabled(false);
        tiPhone.setErrorEnabled(false);

        if(TextUtils.isEmpty(tiName.getEditText().getText())){
            allRight=false;
            tiName.setError(getContext().getString(R.string.error_required));
        }


        if(TextUtils.isEmpty(tiPhone.getEditText().getText())){
            allRight=false;
            tiPhone.setError(getContext().getString(R.string.error_required));
        }

        if(allRight){
            current.setPhone(tiPhone.getEditText().getText().toString());
            current.setName(tiName.getEditText().getText().toString());
            current.setBirthdate(selectedDate);

            viewModelActivity.update(current);
            NavHostFragment.findNavController(EditFriendFragment.this).popBackStack();
        }

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_friend, container, false);
    }


    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Calendar tmp=new GregorianCalendar();
        tmp.set(year,month,dayOfMonth);
        selectedDate=tmp.getTime().getTime();
    }
}