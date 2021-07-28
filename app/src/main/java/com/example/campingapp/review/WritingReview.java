package com.example.campingapp.review;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.Button;
import android.widget.RatingBar;

public class WritingReview extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_review);
        ReviewSystem reviewSystem = new ReviewSystem();
        Button btn=(Button)findViewById(R.id.review_button);
        String campid=getIntent().getStringExtra("campid");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser user=auth.getCurrentUser();
                EditText editText1=(EditText)findViewById(R.id.review_writing);
                RatingBar ratingBar = (RatingBar)findViewById(R.id.review_ratingbar);
                WritingReviewEntity review = new WritingReviewEntity();
                String cid = campid;
                String uid = user.getUid();
                String cont=editText1.getText().toString();
                String name=user.getEmail();
                review.setCid(cid);
                review.setCont(cont);
                review.setUid(uid);
                review.setUserName(name);
                review.setRating(ratingBar.getRating());
                reviewSystem.uploadReview(review,WritingReview.this);
                onBackPressed();

            }
        });




    }



}
