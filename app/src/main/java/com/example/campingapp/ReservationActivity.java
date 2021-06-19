package com.example.campingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.campingapp.databinding.ActivityReservationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.System.*;

public class ReservationActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    Spinner set_people;
    ArrayAdapter<Integer> adapter;
    Integer[] N_people={1,2,3,4,5,6,7,8};
    Integer people;
    CalendarDay startDay;
    CalendarDay endDay;
    Button reserveBtn;
    Button cancelBtn;
    CampingEntity camp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReservationBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_reservation);
        Intent intent = getIntent();
        ReservationSystem reservationSystem = ViewModelProviders.of(this).get(ReservationSystem.class);
        camp = (CampingEntity) intent.getSerializableExtra("CampingEntity");

        calendarView = binding.calendar;
        calendarView.state().edit().setMinimumDate(
                CalendarDay.from(Calendar.getInstance().get(Calendar.YEAR),
                                Calendar.getInstance().get(Calendar.MONTH)+1,
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH
                                )
                )

        ).setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        reservationSystem.blockDate(camp,calendarView);


        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                startDay = dates.get(0);
                endDay = dates.get(dates.size()-1);

            }
        });

        set_people =(Spinner) binding.setPeople;
        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,N_people);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        set_people.setAdapter(adapter);
        set_people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                people = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reserveBtn = (Button)binding.reserveButton;
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택한 인원수와 캠핑장 최소 최대 인원 비교
                if(people < camp.getMinPeople() || people > camp.getMaxPeople()){
                    Toast.makeText(ReservationActivity.this,"인원수를 확인해 주세요",Toast.LENGTH_SHORT).show();
                }else{
                    //예약
                    reservationSystem.reserveCamp(camp,startDay,endDay,people);
                    onBackPressed();
                }
            }
        });
        /*
        cancelBtn = (Button) binding.cancelButton;
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ReservationEntity> res;
                onBackPressed();
            }
        });

         */

    }



}