package com.example.campingapp;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;




public class SearchItemView extends LinearLayout {
    TextView textName;
    TextView textReview;
    TextView textRating;
    TextView textPrice;
    ImageView campImage;

    public SearchItemView(Context context) {
        super(context);
        init();
    }

    public SearchItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_item,this,true);
        textName = (TextView)findViewById(R.id.text_campname);
        textRating = (TextView)findViewById(R.id.text_rating);
        textReview = (TextView)findViewById(R.id.text_review);
        textPrice = (TextView)findViewById(R.id.text_price);
        campImage = (ImageView)findViewById(R.id.camp_image);
    }

    public void setTextName(String name){ textName.setText(name);}
    public void setTextReview(Integer review){textReview.setText(Integer.toString(review));}
    public void setTextRating(Double rating){textRating.setText(Double.toString(rating));}
    public void setTextPrice(Integer price){textPrice.setText(Integer.toString(price));}

}
