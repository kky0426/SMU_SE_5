package com.example.campingapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class UsedItemView extends LinearLayout {
    TextView textName;
    TextView textStart;
    TextView textEnd;
    TextView textPeople;
    public UsedItemView(Context context) {
        super(context);
        init();
    }
    public UsedItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.used_item,this,true);
        textName = (TextView)findViewById(R.id.usedItem_name);
        textStart = (TextView)findViewById(R.id.usedItem_start);
        textEnd = (TextView)findViewById(R.id.usedItem_end);
        textPeople = (TextView)findViewById(R.id.usedItem_people);
    }
    public void setTextName(String name){ this.textName.setText(name);}
    public void setTextStart(String start){this.textStart.setText(start);}
    public void setTextEnd(String end){this.textEnd.setText(end);}
    public void setTextPeople(Integer people){this.textPeople.setText(Integer.toString(people));}
}
