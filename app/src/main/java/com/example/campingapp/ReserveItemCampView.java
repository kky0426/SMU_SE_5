package com.example.campingapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ReserveItemCampView extends LinearLayout {
    TextView textStart;
    TextView textEnd;
    TextView textPeople;
    public ReserveItemCampView(Context context) {
        super(context);
        init();
    }
    public ReserveItemCampView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.reserve_item_for_camp,this,true);
        textStart = (TextView)findViewById(R.id.resItemCamp_start);
        textEnd = (TextView)findViewById(R.id.resItemCamp_end);
        textPeople = (TextView)findViewById(R.id.resItemCamp_people);
    }

    public void setTextStart(String start){this.textStart.setText(start);}
    public void setTextEnd(String end){this.textEnd.setText(end);}
    public void setTextPeople(Integer people){this.textPeople.setText(Integer.toString(people));}
}