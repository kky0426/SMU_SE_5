package com.example.campingapp;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import android.widget.TextView;

public class Review_sing extends LinearLayout{
    TextView Usname,Recont;
    ImageView ReImg;
    public Review_sing(Context context){
        super(context);
        init(context);
    }
    public Review_sing(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }
    private void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.review_item,this,true);
        Usname=findViewById(R.id.review_name);
        Recont=findViewById(R.id.review_cont);

    }

    public void setUsname(String rename) {
        Usname.setText(rename);
    }

    public void setRecont(String recont) {
        Recont.setText(recont);
    }

}
