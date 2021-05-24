package com.example.campingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.*;

public class ReservationActivity extends AppCompatActivity {
    MaterialCalendarView calendarView;
    Spinner set_people;
    ArrayAdapter<Integer> adapter;
    Integer[] N_people={1,2,3,4,5,6,7,8};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        calendarView = findViewById(R.id.calendar);
        calendarView.state().edit().setMinimumDate(
                CalendarDay.from(Calendar.getInstance().get(Calendar.YEAR),
                                Calendar.getInstance().get(Calendar.MONTH)+1,
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH
                                )
                )

        ).setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        set_people =(Spinner) findViewById(R.id.set_people);
        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,N_people);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        set_people.setAdapter(adapter);
        set_people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}