package com.example.campingapp.reservation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.campingapp.R;

public class ReserveItemView extends LinearLayout {
    TextView textName;
    TextView textStart;
    TextView textEnd;
    TextView textPeople;
    public ReserveItemView(Context context) {
        super(context);
        init();
    }
    public ReserveItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.reserve_item,this,true);
        textName = (TextView)findViewById(R.id.resItem_name);
        textStart = (TextView)findViewById(R.id.resItem_start);
        textEnd = (TextView)findViewById(R.id.resItem_end);
        textPeople = (TextView)findViewById(R.id.resItem_people);
    }
    public void setTextName(String name){ this.textName.setText(name);}
    public void setTextStart(String start){this.textStart.setText(start);}
    public void setTextEnd(String end){this.textEnd.setText(end);}
    public void setTextPeople(Integer people){this.textPeople.setText(Integer.toString(people));}
}


