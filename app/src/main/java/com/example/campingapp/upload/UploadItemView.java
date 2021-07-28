package com.example.campingapp.upload;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.campingapp.R;

public class UploadItemView extends LinearLayout {
    TextView textName;
    Button manage;
    Button delete;
    public UploadItemView(Context context) {
        super(context);
        init();
    }
    public UploadItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.upload_item,this,true);
        textName = (TextView)findViewById(R.id.my_camp_name);
        manage = (Button)findViewById(R.id.reservation_manage);
        delete = (Button)findViewById(R.id.camp_delete);
    }
    public void setTextName(String name){ this.textName.setText(name);}

}